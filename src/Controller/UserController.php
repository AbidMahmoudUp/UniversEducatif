<?php

namespace App\Controller;

use App\Entity\Profile;
use App\Entity\User;
use App\Form\AdminPasswordUpdateType;
use App\Form\ForgetPasswordType;
use App\Form\LoginType;
use App\Form\SettingsCoachType;
use App\Form\SettingsType;
use App\Form\UpdateForgetPasswordType;
use App\Form\UserType;
use App\Form\VerficationCodeType;
use App\Repository\NotesRepository;
use App\Repository\ProfileAdminRepository;
use App\Repository\ProfileCoachRepository;
use App\Repository\ProfileRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;
use GuzzleHttp\Psr7\UploadedFile;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Cookie;
use Symfony\Component\HttpFoundation\File\UploadedFile as FileUploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Validator\ValidatorInterface;

class UserController extends AbstractController
{


    private $session;


    private $verificationCode;

    

    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }


    #[Route('/', name: 'app_user_index', methods: ['GET'])]
    public function index(UserRepository $userRepository): Response
    {
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll(),
        ]);
    }
 

    #[Route('/SignInSignUp', name: 'SignInSignUp')]
public function SignInSignUp(Request $request, EntityManagerInterface $entityManager, UserRepository $userRepository,ProfileRepository $profileRepository): Response
{
    
    if(isset($_COOKIE['ConnectedUser']) )
    {

       if(!isset($_SESSION["Userid"]))
       {
        $userRepository = $entityManager->getRepository(User::class);
        $user = $userRepository->findOneBy(['id' => $_COOKIE['ConnectedUser']]);
        $this->session->set('user', $user);
        $_SESSION["Userid"]=$user->getId();
        $_SESSION["status"]=$user->getStatus();
       }
        if($_SESSION["status"]=="User")
        {
            return $this->redirectToRoute('app_user_welcomePage');
        }
        if($_SESSION["status"]=="Coach")
        {
            return $this->redirectToRoute('app_Coach_dashbord');

        }
       
        return $this->redirectToRoute('app_Admin_dashbord');
    }
    $errors ="";
    $user = new User();
    $profile = new Profile();
    $formSignUp = $this->createForm(UserType::class, $user);
    $formSignIn = $this->createForm(LoginType::class);
    $formForgetPassword = $this->createForm(ForgetPasswordType::class);
    $formUpdatePassword = $this->createForm(UpdateForgetPasswordType::class);

   
    
    // Handle form submission for sign up
    $formSignUp->handleRequest($request);
    if ($formSignUp->isSubmitted() && $formSignUp->isValid()) {
        
        
        $user->setStatus("User");
        $user->setState("Active");
        $data =$formSignUp->getData();
        $password = $data->getPassword();
        $user->setPassword(password_hash($password,PASSWORD_BCRYPT));
        
        $entityManager->persist($user);
        $entityManager->flush();
        $profile->setIdUser($user);
        $picture="http://127.0.0.1/img/userDefaultIcon.png";
        $profile->setPicture($picture);
        $entityManager->persist($profile);
        $entityManager->flush();
        return $this->redirectToRoute('SignInSignUp');
    }
    
    $formSignIn->handleRequest($request);
    if ($formSignIn->isSubmitted() ) {
        $data = $formSignIn->getData();
        $email = $data->getEmail();
        $password = $data->getPassword();
        

        $userRepository = $entityManager->getRepository(User::class);
        $user = $userRepository->findOneBy(['email' => $email]);
        
        if ($user && password_verify($password, $user->getPassword()) && $user->getStatus() === "Admin"&& $user->getState() === "Active") {
            // Store user information in the session
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
                $this->session->set("status",$user->getStatus());
                $_SESSION["status"]=$user->getStatus();
                setcookie("ConnectedUser", $user->getId(), time() + (86400 * 30), "/");
                $profileConnectedUser=$profileRepository->findOneBy(['idUser'=>$user->getId()]);
                if($profileConnectedUser->getFirstName()==null || 
                   $profileConnectedUser->getLastName()==null ||
                   $profileConnectedUser->getPhoneNumber()==null || 
                   $profileConnectedUser->getAddress()==null ||
                   $profileConnectedUser->getLocation()==null 
             
                ){
                    return $this->redirectToRoute('app_admin_settings');
                }
            return $this->redirectToRoute('app_Admin_dashbord');
        }
        else{
            $errors = "invalid Credentials ";
        } 
        if ($user && password_verify($password, $user->getPassword()) && $user->getStatus() === "User"&& $user->getState() == "Active") {
            // Store user information in the session
            $this->session->set('user', $user);
                $_SESSION["Userid"]=$user->getId();
      
                $this->session->set("status",$user->getStatus());
                $_SESSION["status"]=$user->getStatus();
                setcookie("ConnectedUser", $user->getId(), time() + (86400 * 30), "/");
                $profileConnectedUser=$profileRepository->findOneBy(['idUser'=>$user->getId()]);
                if($profileConnectedUser->getFirstName()==null || 
                   $profileConnectedUser->getLastName()==null ||
                   $profileConnectedUser->getPhoneNumber()==null || 
                   $profileConnectedUser->getAddress()==null ||
                   $profileConnectedUser->getLocation()==null 
             
                ){
                    return $this->redirectToRoute('app_user_settings');
                }
        
            return $this->redirectToRoute('app_user_welcomePage');
        } 
            else{
                $errors = "invalid Credentials ";
            }
        if ($user && password_verify($password, $user->getPassword()) && $user->getStatus() == "Coach"&& $user->getState() == "Active") {
            // Store user information in the session
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
                $this->session->set("status",$user->getStatus());
                $_SESSION["status"]=$user->getStatus();
                setcookie("ConnectedUser", $user->getId(), time() + (86400 * 30), "/");
                $profileConnectedUser=$profileRepository->findOneBy(['idUser'=>$user->getId()]);
                if($profileConnectedUser->getFirstName()==null || 
                   $profileConnectedUser->getLastName()==null ||
                   $profileConnectedUser->getPhoneNumber()==null || 
                   $profileConnectedUser->getAddress()==null ||
                   $profileConnectedUser->getLocation()==null 
             
                ){
                    return $this->redirectToRoute('app_coach_settings');
                }
            return $this->redirectToRoute('app_Coach_dashbord');
        } 
        else {
            
                $errors = "invalid Credentials ";
        
        }
    }
    
    return $this->render('user/LoginPage.html.twig', [
        'formSignUp' => $formSignUp->createView(),
        'formSignIn' => $formSignIn->createView(),
        'formForgetPassword'=> $formForgetPassword->createView(),
        'formUpdatePassword' => $formUpdatePassword->createView(),
        'errors'=>$errors
    ]);
}



    #[Route('/show/{id}', name: 'app_user_show', methods: ['GET'])]
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }
    #[Route('/dashbordAdmin', name:'app_Admin_dashbord',methods:['GET','POST'])]
        public function switchToDashBord(Request $request , ProfileRepository $profileRepository ,ProfileAdminRepository $profileAdminRepository):Response
        {
            if(isset($_COOKIE['ConnectedUser']))
            {
              if(isset($_SESSION['status']))
              {
                if($_SESSION['status']!='Admin')
                {
                  setcookie('ConnectedUser', '', -1, '/');
                  unset($_SESSION['Userid']);
                   return $this->redirectToRoute('SignInSignUp');
                }
              }
                
            }
            else
            {
                return $this->redirectToRoute('SignInSignUp');
    
            }
            $userId = $request->get('user');
            $user = $this->session->get('user');
           $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
            $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
            return $this->render('/AdminTemplate/dashbordAdmin.html.twig', ["user"=>$user,"profile"=>$profile,"profileAdmin"=>$profileAdmin]);
        }
   
    
    #[Route('/edit/{id}', name: 'app_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

    #[Route('/delete/{id}', name: 'app_user_delete', methods: ['POST','GET'])]
    public function delete(Request $request, User $user, EntityManagerInterface $entityManager, UserRepository $userRepository,ProfileRepository $profileRepository ,ProfileAdminRepository $profileAdminRepository,$id): Response
    {
        $user1 = $this->session->get('user');
        if($request->isMethod('GET'))
        {
            $user2= $userRepository->findOneBy(["id"=>$id]);
            $entityManager->remove($user2);
            $entityManager->flush();
            if (isset($_COOKIE['ConnectedUser'])) {
                unset($_COOKIE['ConnectedUser']); 
                setcookie('ConnectedUser', '', -1, '/');
            }
    
            return $this->redirectToRoute('SignInSignUp');
        }
        else{
            $profile = $profileRepository->findOneBy(['idUser' => $user1->getId()]);
            $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
           if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
               $entityManager->remove($user);
               $entityManager->flush();
           }
           
        }
     

        return $this->redirectToRoute('app_Admin_UserMangement', ["profile"=>$profile , 'user'=>$user1], Response::HTTP_SEE_OTHER);
    }

       
    #[Route('/universEducatif', name: 'app_user_welcomePage', methods: ['GET'])]
    public function switchToWelcomePage(Request $request , EntityManagerInterface $em , UserRepository $userRepository  ): Response
    {   $user=NULL;
        if(isset($_COOKIE['ConnectedUser']))
        {
          if(isset($_SESSION['status']))
          {
            if($_SESSION['status']!='User')
            {
              setcookie('ConnectedUser', '', -1, '/');
              unset($_SESSION['Userid']);
               return $this->redirectToRoute('SignInSignUp');
            }
          }
            
        }
        else
        {
            return $this->redirectToRoute('SignInSignUp');

        }
    
        if(!isset($_SESSION['Userid']))
          {
            $user=$userRepository->findOneBy(['id'=>$_COOKIE['ConnectedUser']]);
            $_SESSION['Userid']=$user->getId();
            $this->session->set('user', $user);
            
          }
          else{
            $userId = $request->get('user');
            $user = $this->session->get('user');
          }
            
        return $this->render('StudentTemplate/welcomePage.html.twig', ['user'=>$user]);
    }

    public function editUser(Request $request, User $user, EntityManagerInterface $entityManager, ProfileRepository $profileRepository, ProfileAdminRepository $profileAdminRepository): Response
{
    $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
    $profileAdmin = $profileAdminRepository->findOneBy(['idProfile' => $profile->getId()]);
    $form = $this->createForm(SettingsType::class, $profile);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $entityManager = $this->getDoctrine()->getManager();

        $entityManager->persist($profile);
        $entityManager->flush();
        $entityManager->persist($profileAdmin);
        $entityManager->flush();

        return $this->redirectToRoute('user_edit', ['id' => $user->getId()]);
    }

    return $this->render('user/edit.html.twig', [
        'form' => $form->createView(),
    ]);
}

#[Route('/ajax', name: 'app_user_ajax', methods: ['GET', 'POST'])]
public function ajaxTest(Request $request, UserRepository $userRepository)
{

    if ($request->isXmlHttpRequest())
    {

        $user = $userRepository->findOneBy(['email' => $_POST['email']]);
        if($user != null)
        {
            $jsonData = array();
            $jsonData['id'] = $user->getId();
            $email = $_POST['email'];
            $jsonData['email'] = $email;
            $this->verificationCode = random_int(100000, 999999);
            $string = $this->verificationCode;
            $jsonData['verificationCode'] = $this->verificationCode;
            $tranport = Transport::fromDsn("smtp://amin.warghi@gmail.com:dywmwexhazvgtrjb@smtp.gmail.com:587");
            $mailer = new Mailer($tranport);
            $email = (new Email())
            ->from('amin.warghi@gmail.com')
            ->to($email)
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->html("Verification Code : ".$this->verificationCode);

                $mailer->send($email);
                return new JsonResponse($jsonData);
            }
            else
            {
                $jsonData = array();
                return new JsonResponse($jsonData);
            }
        }
    else
    {
       return new JsonResponse("Hey there i m  not working");
    }
}
#[Route('/ajaxPass', name: 'app_user_password', methods: ['GET', 'POST'])]
public function modifierMotDePasse(Request $request, UserRepository $userRepository , EntityManagerInterface $em)
{

    if ($request->isXmlHttpRequest())
    {
  
        $user = $userRepository->findOneBy(['email' => $_POST['email']]);
        if($user != null)
        {
            $jsonData = array();
            
            $user->setPassword(password_hash($_POST['password'],PASSWORD_BCRYPT));
            $jsonData['id'] = $user->getId();
            $em->flush();
            return new JsonResponse($jsonData);
        }
            else
            {
                $jsonData = array();
                return new JsonResponse($jsonData);
            }
        }
    else
    {
       return new JsonResponse("hi there i m not workking");
    }
}

#[Route('/ValidationSignUp', name: 'app_user_controle', methods: ['GET', 'POST'])]
public function controleUser(Request $request, ValidatorInterface $validator, UserRepository $userRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $username = $request->request->get('userName');
        $email = $request->request->get('email');
        $password = $request->request->get('password');
        $user = new User();
        $user->setUserName($username);
        $user->setEmail($email);
        $user->setPassword($password);
        $errors = $validator->validate($user);
        if(count($errors) > 0)
        {
            foreach ($errors as $violation) {
                $jsonData[$violation->getPropertyPath()][] = $violation->getMessage();
            }
            return new JsonResponse($jsonData);
        }
        else
        {
            $existingUser = $userRepository->findOneBy(['email' => $email]);
            if($existingUser)
            {
                $jsonData['email'] = "Email already used";
                return new JsonResponse($jsonData);
            }
            else
                return new JsonResponse($jsonData);
        }
    }
    else
    {
       return new JsonResponse();
    }
}


#[Route('/universEducatif/profile', name: 'app_studentProfile_index', methods: ['GET'])]
public function switchToPofileStudent(ProfileRepository $profileRepository,UserRepository $userRepository , NotesRepository $notesRepository): Response
{
    if(isset($_COOKIE['ConnectedUser']))
    {
      if(isset($_SESSION['status']))
      {
        if($_SESSION['status']!='User')
        {
          setcookie('ConnectedUser', '', -1, '/');
          unset($_SESSION['Userid']);
           return $this->redirectToRoute('SignInSignUp');
        }
      }
        
    }
    else
    {
        return $this->redirectToRoute('SignInSignUp');

    }
    
      $user = $this->session->get('user');
        $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $userDetails= $userRepository->findOneBy(['id'=>$user->getId()]);
        $notes= $notesRepository->findNotesOrderDESC($user->getId());
        return $this->render('StudentTemplate/profilePage.html.twig', [
            'profile' => $profile,
            'userDetails'=>$userDetails,
            'notes'=>$notes
    
        ]);
    
}


#[Route('/UniversEducatif/profile/updatePicture', name: 'update_picture_student')]
public function UpdatePicture(Request $request,ManagerRegistry $managerRegistry , ProfileRepository $profileRepository):Response
{   
   $databaseFilePath = "http://127.0.0.1/img/";

   $user = $this->session->get('user');
   $profile = $profileRepository->findProfileByUserId($user->getId());
   // Check if a file has been uploaded
   $uploadedFile = $request->files->get('picture');
   if ($uploadedFile instanceof FileUploadedFile) {
       $fileName = $uploadedFile->getClientOriginalName();
       $destinationPath = 'c:/xampp/htdocs/img/';
       $newFilePath = $destinationPath . $fileName;

       // Remove existing image if it exists in the destination directory
       if (file_exists($newFilePath)) {
           unlink($newFilePath);
       }

       // Move the uploaded file to the desired directory
       $uploadedFile->move($destinationPath, $fileName);
       
       // Update the profile picture URL
       $profile->setPicture($databaseFilePath . $fileName);

       // Persist changes to the database
       $em = $managerRegistry->getManager();
       $em->flush();
       return  $this->redirectToRoute("app_studentProfile_index");
   }
   return   new Response($uploadedFile);
}
#[Route('/dashbordUser/SettingsUser', name: 'app_user_settings')]
public function SettingsCoach(Request $request, EntityManagerInterface $entityManager,UserRepository $userRepository,ProfileRepository $profileRepository,ValidatorInterface  $validatorInterface): Response
{
    if(isset($_COOKIE['ConnectedUser']))
    {
      if(isset($_SESSION['status']))
      {
        if($_SESSION['status']!='User')
        {
          setcookie('ConnectedUser', '', -1, '/');
          unset($_SESSION['Userid']);
           return $this->redirectToRoute('SignInSignUp');
        }
      }
    }
    else
    {
        return $this->redirectToRoute('SignInSignUp');

    }
    $SettingsForm = $this->createForm(SettingsCoachType::class);
    $passwordForm = $this->createForm(AdminPasswordUpdateType::class);
    $userId = $this->session->get('user');
    $user = $userRepository->findOneBy(['id' => $userId]);
    $profile = $profileRepository->findProfileByUserId($userId->getId());
    $SettingsForm->handleRequest($request);
    if ($SettingsForm->isSubmitted() ) {
        $data = $SettingsForm->getData();
        $address= $data['address'];
        $location=$data['location'];
        $phoneNumber= $data['phoneNumber'];
        $firstName=$data['firstName'];
        $lastName= $data['lastName'];
        $email=$data['email'];
        $profile->setAddress($address);
        $profile->setLocation($location);
        $profile->setPhoneNumber($phoneNumber);
        $profile->setFirstName($firstName);
        $profile->setLastName($lastName);
        $user->setEmail($email);      
        $errors= $validatorInterface->validate($profile);
        if(count($errors)>0)
        {
           return new Response("test test");
        }
        else{
           $entityManager->flush();
        }     
    }
    $passwordForm->handleRequest($request);
    if ($passwordForm->isSubmitted()) {
        $data = $passwordForm->getData();
        $newPassword=  $data['newPassword'];
        $user->setPassword(password_hash($newPassword,PASSWORD_BCRYPT));
        $errors= $validatorInterface->validate($user);
        $entityManager->flush();
    }
    return $this->renderForm('/StudentTemplate/Settings.html.twig', [
        'user' => $user,
        'profile'=>$profile,
        'form' => $SettingsForm,
        'formUpdatePasswordAdmin' => $passwordForm
    ]);
}


#[Route('/ValidationSettingsUser', name: 'app_UserSettings_controle', methods: ['GET', 'POST'])]
public function controleSettingsUser(Request $request, ValidatorInterface $validator)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $firstname = $request->request->get('firstname');
        $lastname = $request->request->get('lastname');
        $email = $request->request->get('email');
        $address = $request->request->get('address');
        $location = $request->request->get('location');
        $phoneNumber = $request->request->get('phoneNumber');
        $PhoneNumber = (int)($phoneNumber);

        $user = new User();
        $user->setEmail($email);
        $profile = new Profile();
        $profile->setAddress($address);
        $profile->setPhoneNumber($PhoneNumber);
        $profile->setFirstName($firstname);
        $profile->setLastName($lastname);
        $profile->setLocation($location);
        $user->setEmail($email);
        $errors = $validator->validate($user);
        $errorsProfile = $validator->validate($profile);
        if(count($errors) > 0)
        {
            foreach ($errors as $violation) {
                $jsonData[$violation->getPropertyPath()][] = $violation->getMessage();
            }
            
        }
        if(count($errorsProfile) > 0)
        {
            foreach ($errorsProfile as $violation) {
                $jsonData[$violation->getPropertyPath()][] = $violation->getMessage();
            }
            
        }

        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse();
    }
}

#[Route('/UpdatePasswordUser', name: 'app_User_Password_update', methods: ['GET', 'POST'])]
public function UpdatePasswordUser(Request $request, ValidatorInterface $validator, UserRepository $userRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $password = $request->request->get('oldPassword');
        $newPassword = $request->request->get('newPassword');
        $confirmPassword = $request->request->get('confirmPassword');
        $user = $userRepository->findOneBy(['id' => $this->session->get('user')]);
        if(!password_verify($password, $user->getPassword()))
        {
            $jsonData['passwordError'] = "Your password is not correct";
        }
        if($newPassword != $confirmPassword)
        {
            $jsonData['passwordConfirmationError'] = "Verify your password";
        }
        else if($newPassword== $confirmPassword)
        {
            $jsonData['passwordConfirmationError'] = null;
        }
        
    
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse();
    }
}

#[Route('/logout', name: 'app_user_Logout')]
public function logoutUser():Response
{
    if (isset($_COOKIE['ConnectedUser'])) {
        unset($_COOKIE['ConnectedUser']); 
        setcookie('ConnectedUser', '', -1, '/');
    }

    return $this->redirectToRoute('SignInSignUp');
} 

}
