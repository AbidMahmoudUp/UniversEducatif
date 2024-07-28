<?php

namespace App\Controller;

use App\Entity\Profile;
use App\Entity\User;
use App\Form\AdminPasswordUpdateType;
use App\Form\SettingsCoachType;
use App\Form\SettingsType;
use App\Repository\LogsRepository;
use App\Repository\ProfileCoachRepository;
use App\Repository\ProfileRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Validator\ValidatorInterface;

class CoachController extends AbstractController
{

    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/coach', name: 'app_coach')]
    public function index(): Response
    {
        
        return $this->render('coach/index.html.twig', [
            'controller_name' => 'CoachController',
        ]);
    }


    #[Route('/dashbordCoach', name:'app_Coach_dashbord',methods:['GET','POST'])]
    public function switchToDashBordCoach(Request $request , ProfileRepository $profileRepository ,ProfileCoachRepository $profileAdminRepository):Response
    {
        if(isset($_COOKIE['ConnectedUser']))
        {
          if(isset($_SESSION['status']))
          {
            if($_SESSION['status']!='Coach')
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
        return $this->render('/CoachTemplate/dashbordCoach.html.twig', ["user"=>$user,"profile"=>$profile]);
    }

    #[Route('/dashbordCoach/profileCoach', name:'app_Coach_Profile',methods:['GET','POST'])]
    public function switchToProfileCoach(ProfileCoachRepository $profileAdminRepository,LogsRepository $logsRepository):Response
    {    // Retrieve user session data
        
        if(isset($_COOKIE['ConnectedUser']))
        {
          if(isset($_SESSION['status']))
          {
            if($_SESSION['status']!='Coach')
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
     
        // Check if user session data exists
        if (!$user) {
            // Handle the case when user session data is not available
            throw $this->createNotFoundException('User session data not found');
        }
        $profileRepository = $this->getDoctrine()->getRepository(Profile::class);
        $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        
        // Pass user and profile data to the template
        return $this->render('/CoachTemplate/Profile.html.twig', [
            'user' => $user,
            'profile' => $profile,
            
        ]);
        // Pass user data to the template
     }
     #[Route('/dashbordCoach/profileCoach/updatePicture', name: 'update_picture_coach')]
     public function UpdatePicture(Request $request,ManagerRegistry $managerRegistry , ProfileRepository $profileRepository):Response
    {       if(isset($_COOKIE['ConnectedUser']))
        {
          if(isset($_SESSION['status']))
          {
            if($_SESSION['status']!='Coach')
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
        $databaseFilePath = "http://127.0.0.1/img/";
        $user = $this->session->get('user');
        $profile = $profileRepository->findProfileByUserId($user->getId());
        // Check if a file has been uploaded
        $uploadedFile = $request->files->get('picture');
        if ($uploadedFile instanceof UploadedFile) {
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
            return  $this->redirectToRoute("app_Coach_Profile");
        }
        return   new Response($uploadedFile);
    }


    
    #[Route('/logout', name: 'app_Coach_Logout')]
    public function logout():Response
    {
        if (isset($_COOKIE['ConnectedUser'])) {
            unset($_COOKIE['ConnectedUser']); 
            setcookie('ConnectedUser', '', -1, '/');
        }

        return $this->redirectToRoute('SignInSignUp');
    }

    #[Route('/dashbordCoach/SettingsCoach', name: 'app_coach_settings')]
    public function SettingsCoach(Request $request, EntityManagerInterface $entityManager,UserRepository $userRepository,ProfileRepository $profileRepository,ValidatorInterface  $validatorInterface): Response
    {
        if(isset($_COOKIE['ConnectedUser']))
        {
          if(isset($_SESSION['status']))
          {
            if($_SESSION['status']!='Coach')
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
        if ($SettingsForm->isSubmitted() && $SettingsForm->isValid()) {
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
        return $this->renderForm('/CoachTemplate/Settings.html.twig', [
            'user' => $user,
            'profile'=>$profile,
            'form' => $SettingsForm,
            'formUpdatePasswordAdmin' => $passwordForm
        ]);
    }
    #[Route('/ValidationSettingsCoach', name: 'app_CoachSettings_controle', methods: ['GET', 'POST'])]
    public function controleSettingsCoach(Request $request, ValidatorInterface $validator)
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
    
    #[Route('/UpdatePasswordCoach', name: 'app_Coach_Password_update', methods: ['GET', 'POST'])]
    public function UpdatePasswordAdmin(Request $request, ValidatorInterface $validator, UserRepository $userRepository)
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

    
}
