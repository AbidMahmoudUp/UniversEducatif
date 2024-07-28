<?php
namespace App\Security;
use App\Entity\GoogleOauth2;
use App\Entity\Profile;
use App\Entity\User; // your user entity
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\EntityManagerInterface;
use KnpU\OAuth2ClientBundle\Security\Authenticator\SocialAuthenticator;
use League\OAuth2\Client\Provider\GoogleUser;
use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use Symfony\Component\HttpFoundation\Cookie;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Exception\AuthenticationException;
use Symfony\Component\Security\Core\User\UserProviderInterface;

class GoogleAuthenticator extends SocialAuthenticator
{
    private $clientRegistry;
    private $em;
    private $router;
    private $session;

    public function __construct(ClientRegistry $clientRegistry, EntityManagerInterface $em, RouterInterface $router,SessionInterface $session)
    {
        $this->clientRegistry = $clientRegistry;
        $this->em = $em;
        $this->router = $router;
        $this->session=$session;
    }

    public function supports(Request $request)
    {
        // continue ONLY if the current ROUTE matches the check ROUTE
        return $request->attributes->get('_route') === 'connect_google_check';
    }

    public function getCredentials(Request $request)
    {
        // this method is only called if supports() returns true

        // For Symfony lower than 3.4 the supports method need to be called manually here:
        // if (!$this->supports($request)) {
        //     return null;
        // }

        return $this->fetchAccessToken($this->getGoogleClient());
    }

    public function getUser($credentials, UserProviderInterface $userProvider )
    {
        /** @var GoogleUser $googleUser */
        $googleUser = $this->getGoogleClient()->fetchUserFromToken($credentials);
    
        $email = $googleUser->getEmail();
    
        // Check if a user with the same Google ID exists
        $existingUser = $this->em->getRepository(GoogleOauth2::class)->findOneBy(['email' => $email]);
        
        if ($existingUser) {
            $user = $this->em->getRepository(User::class)->findOneBy(['email' => $email]);
            setcookie("ConnectedUser", $existingUser->getUser()->getId(), time() + (86400 * 30), "/");
            $this->router->generate('app_user_welcomePage');
                
            return $user;
        }
    
        // Check if a user with the same email exists
        $oauthUser = $this->em->getRepository(GoogleOauth2::class)->findOneBy(['email' => $email]);
    
        // If no user exists with the same email, create a new one
 
        if (!$oauthUser) {
            $oauthUser = new GoogleOauth2();
            $oauthUser->setEmail($email);    
            $user = new User();
            $user->setEmail($email);
            $user->setStatus("User");
            $user->setState("Active");
            $user->setUserName($googleUser->getName());
            $user->setPassword(password_hash($googleUser->getName(), PASSWORD_BCRYPT));
            
            try {
                $oauthUser->setUser($user);
                $this->em->persist($oauthUser);
                $this->em->persist($user);
                $this->em->flush();
                $this->em->refresh($user);
                $profile=new Profile();
                $profile->setAddress("aze");
                $profile->setLocation("eaz");
                $profile->setIdUser($user);
                $profile->setPhoneNumber(121231212);
                $profile->setFirstName("eaz");
                $profile->setLastName("eaz");
                $profile->setPicture("http://127.0.0.1/img/userDefaultIcon.png");
                $this->em->persist($profile);
                $this->em->flush();

                // $user= $this->em->getRepository(User::class)->findOneBy(["email" => $email]);
                
                $this->session->set('user', $user);
                $_SESSION["Userid"]=$user->getId();
                $cookie = new Cookie(
                    'ConnectedUser', // Cookie name
                    $user->getId(), // User ID
                    time() + (86400 * 30), // Expiration: 30 days (86400 seconds per day)
                    '/', // Path (optional)
                    null, // Domain (optional)
                    false, // Secure (optional)
                    true // HttpOnly (optional)
                );
                setcookie("ConnectedUser", $user->getId(), time() + (86400 * 30), "/");

              $this->router->generate('app_user_welcomePage');
            } catch (\Exception $e) {
                // Handle database errors
                // Log or return an error response
                // Example: return new Response('Error creating user', Response::HTTP_INTERNAL_SERVER_ERROR);
                throw new AuthenticationException('User creation failed: ' . $e->getMessage());
            }
    
            // Also, create a new User entity
           // Securely hash password
    
            // Persist the new User entity
     
    
            // Return the Googleoauth user
        }
    
        // Return the existing Googleoauth user
        return $oauthUser;
    }
    
    /**
     * @return  \KnpU\OAuth2ClientBundle\Client\OAuth2Client

     */
    private function getGoogleClient()
    {
        return $this->clientRegistry->getClient('google');
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, $providerKey)
    {
        // change "app_homepage" to some route in your app
        $targetUrl = $this->router->generate('app_user_welcomePage');
        
        return new RedirectResponse($targetUrl);
    
        // or, on success, let the request continue to be handled by the controller
        //return null;
    }

    public function onAuthenticationFailure(Request $request, AuthenticationException $exception)
    {
        $message = strtr($exception->getMessageKey(), $exception->getMessageData());

        return new Response($message, Response::HTTP_FORBIDDEN);
    }

    /**
     * Called when authentication is needed, but it's not sent.
     * This redirects to the 'login'.
     */
    public function start(Request $request, AuthenticationException $authException = null)
    {
        return new RedirectResponse(
            '/SignInSignUp/', // might be the site, where users choose their oauth provider
            Response::HTTP_TEMPORARY_REDIRECT
        );
    }

    // ...
}
