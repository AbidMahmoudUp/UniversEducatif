<?php

namespace App\Controller;

use App\Entity\Logs;
use App\Entity\Profile;
use App\Entity\ProfileAdmin;
use App\Entity\ProfileCoach;
use App\Entity\User;
use App\Form\AdminPasswordUpdateType;
use App\Form\CoachType;
use App\Form\SettingsType;
use App\Repository\LogsRepository;
use App\Repository\NotesRepository;
use App\Repository\ProfileAdminRepository;
use App\Repository\ProfileRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Mime\Email;
use Symfony\Component\Validator\Validator\ValidatorInterface;

class AdminController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/admin', name: 'app_admin')]
    public function index(): Response
    {   
        return $this->render('admin/index.html.twig', [
            'controller_name' => 'AdminController',
        ]);
    }
    #[Route('/dashbordAdmin/Settings', name: 'app_admin_settings')]
    public function Settings(Request $request, EntityManagerInterface $entityManager,UserRepository $userRepository,ProfileRepository $profileRepository, ProfileAdminRepository $profileAdminRepository,ValidatorInterface  $validatorInterface): Response
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
        $SettingsForm = $this->createForm(SettingsType::class);
        $passwordForm = $this->createForm(AdminPasswordUpdateType::class);
        $user = $this->session->get('user');
        $profile = $profileRepository->findProfileByUserId($user->getId());
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        $SettingsForm->handleRequest($request);
        if ($SettingsForm->isSubmitted() && $SettingsForm->isValid()) {
            $data = $SettingsForm->getData();
            //profile data set
           $address= $data['address'];
           $location=$data['location'];
           $phoneNumber= $data['phoneNumber'];
           $res=$data['responsabilty'];
           $title= $data['title'];
           $firstName=$data['firstName'];
           $lastName= $data['lastName'];
           $email=$data['email'];

           $profile->setAddress($address);
            $profile->setLocation($location);
            $profile->setPhoneNumber($phoneNumber);
            $profile->setFirstName($firstName);
            $profile->setLastName($lastName);
            //user data set
            $user->setEmail($email);
            //profileAdmin Data set
            $profileAdmin->setResponsability($res); 
            $profileAdmin->setTitle($title);
            
             
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
        if ($passwordForm->isSubmitted() && $passwordForm->isValid()) {
            $data = $passwordForm->getData();
            $newPassword=  $data['newPassword'];
            $user->setPassword(password_hash($newPassword,PASSWORD_BCRYPT));
            $entityManager->flush();
            
        }

        return $this->renderForm('/AdminTemplate/Settings.html.twig', [
            'user' => $user,
            'profile'=>$profile,
            'profileAdmin'=>$profileAdmin,
            'form' => $SettingsForm,
            'formUpdatePasswordAdmin' => $passwordForm
        ]);
    }
    #[Route('/dashbordAdmin/profileAdmin', name:'app_Admin_Profile',methods:['GET','POST'])]
    public function switchToProfile(ProfileAdminRepository $profileAdminRepository,LogsRepository $logsRepository):Response
    {    // Retrieve user session data
      
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
        $user = $this->session->get('user');
     
        // Check if user session data exists
        if (!$user) {
            // Handle the case when user session data is not available
            throw $this->createNotFoundException('User session data not found');
        }
        $profileRepository = $this->getDoctrine()->getRepository(Profile::class);
        $profile = $profileRepository->findOneBy(['id' => $user->getId()]);
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        $logs=$logsRepository->findAllDESC();
        // Pass user and profile data to the template
        return $this->render('/AdminTemplate/Profile.html.twig', [
            'user' => $user,
            'profile' => $profile,
            'profileAdmin'=>$profileAdmin,
            'logs'=>$logs
            
        ]);
        // Pass user data to the template
     }
     #[Route('/dashbord/profileAdmin/updatePicture', name: 'update_picture')]
     public function UpdatePicture(Request $request,ManagerRegistry $managerRegistry , ProfileRepository $profileRepository):Response
    {   
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
            return  $this->redirectToRoute("app_Admin_Profile");
        }
        return   new Response($uploadedFile);
    }
    #[Route('/logout', name: 'app_Admin_Logout')]
    public function logout():Response
    {
        if (isset($_COOKIE['ConnectedUser'])) {
            unset($_COOKIE['ConnectedUser']); 
            setcookie('ConnectedUser', '', -1, '/');
        }

        return $this->redirectToRoute('SignInSignUp');
    } 
    #[Route('/dashbord/GestionUtilisateur', name: 'app_Admin_UserMangement')]
    public function GestionUtilisateur(Request $request,EntityManagerInterface $entityManager,UserRepository $userRepository,ProfileRepository $profileRepository, ProfileAdminRepository $profileAdminRepository): Response
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
      
        $formSignUpCoach = $this->createForm(CoachType::class);
      
        $user=$userRepository->findOneBy(['id'=>$_COOKIE["ConnectedUser"]]);
        $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        $users=$userRepository->findAll();
        $profiles=$profileRepository->findAll();
        
        
        $formSignUpCoach = $this->createForm(CoachType::class);
        $formSignUpCoach->handleRequest($request);
        if ($formSignUpCoach->isSubmitted() && $formSignUpCoach->isValid()) {
            $profileCoach = new ProfileCoach();
            $newUser = new User();
            $newProfile = new Profile();
            $newUser->setStatus("Coach");
            $newUser->setState("Active");
            $data =$formSignUpCoach->getData();
            $phoneNumber= $data['phonenumber'];
            $firstName=$data['firstName'];
            $lastName= $data['lastName'];
            $email=$data['email'];
            $password = $data['password'];
            $newUser->setPassword(password_hash($password,PASSWORD_BCRYPT));
            $newUser->setUserName($firstName.$lastName);
            $newUser->setEmail($email);
            $entityManager->persist($newUser);
            $newProfile->setIdUser($newUser);
            $newProfile->setFirstName($firstName);
            $newProfile->setLastName($lastName);
            $newProfile->setPicture("http://127.0.0.1/img/userDefaultIcon.png");
            $newProfile->setPhoneNumber($phoneNumber);
            $entityManager->persist($newProfile);
            $profileCoach->setIdProfile($newProfile);
            $profileCoach->setIdModule($data['module']);
           $entityManager->persist($profileCoach);
            $logs = new Logs();
            $logs->setAction1($firstName);
            $logs->setAction2("Coach");
            $logs->setDescription1("a ajouté");
            $logs->setDescription2("en tand que nouvel");
            $logs->setIdUser($user);
            $entityManager->persist($logs);

           $entityManager->flush();
            return $this->redirectToRoute('app_Admin_UserMangement');
  
    }      return $this->render('AdminTemplate/UserManagement.html.twig',
    ['user'=>$user,
    'profile'=>$profile,
    'profileAdmin'=>$profileAdmin,
    'profiles'=>$profiles,
    'users'=>$users,
    'formCoach'=>$formSignUpCoach->createView()
   ]);}

#[Route('/ValidationSettings', name: 'app_userSettings_controle', methods: ['GET', 'POST'])]
public function controleSettings(Request $request, ValidatorInterface $validator)
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
        $title = $request->request->get('title');
        $responsabilty = $request->request->get('responsabilty');
        $user = new User();
        $user->setEmail($email);
        $profile = new Profile();
        $profile->setAddress($address);
        $profile->setPhoneNumber($PhoneNumber);
        $profile->setFirstName($firstname);
        $profile->setLastName($lastname);
        $profile->setLocation($location);
        
        $profileAdmin = new ProfileAdmin();
        $profileAdmin->setResponsability($responsabilty);
        $profileAdmin->setTitle($title);
        $user->setEmail($email);
        $errors = $validator->validate($user);
        $errorsProfile = $validator->validate($profile);
        $errorsProfileAdmin = $validator->validate($profileAdmin);
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
        if(count($errorsProfileAdmin) > 0)
        {
            foreach ($errorsProfileAdmin as $violation) {
                $jsonData[$violation->getPropertyPath()][] = $violation->getMessage();
            }
            
        }
        $jsonData["custom"]=$responsabilty;
        $jsonData["custom1"]=$title;
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse();
    }
}

#[Route('/ValidationCoach', name: 'app_userCoach_controle', methods: ['GET', 'POST'])]
public function controleCoach(Request $request, ValidatorInterface $validator)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $firstname = $request->request->get('firstname');
        $lastname = $request->request->get('lastname');
        $email = $request->request->get('email');
        $phoneNumber = $request->request->get('phoneNumber');
        $password = $request->request->get('password');
        $PhoneNumber = (int)($phoneNumber);

        $user = new User();
        $user->setEmail($email);
        $user->setPassword($password);
        $profile = new Profile();
     
        $profile->setPhoneNumber($PhoneNumber);
        $profile->setFirstName($firstname);
        $profile->setLastName($lastname);
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

#[Route('/UpdatePasswordAdmin', name: 'app_Admin_Password_update', methods: ['GET', 'POST'])]
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
#[Route('/BanUser/{id}', name: 'app_admin_banUser')]
public function BanUser($id,EntityManagerInterface $entityManager,ProfileRepository $profileRepository,UserRepository $userRepository): Response
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

    $user = $this->session->get('user');
    $formSignUpCoach = $this->createForm(CoachType::class);
    $users = $userRepository->findAll();
    $profile = $profileRepository->findProfileByUserId($user->getId());
    $selectedUser=$userRepository->findOneBy(['id'=>$id]);
    if($selectedUser->getState()=="Active")
    {
        $selectedUser->setState("Banned");
        $entityManager->flush();
    }
    else if($selectedUser->getState()=="Banned")
    {
        $selectedUser->setState("Active");
        $entityManager->flush();
    }
    return $this->render('AdminTemplate/UserManagement.html.twig', [
        'profile' => $profile,
        'user'=>$user,
        'formCoach'=>$formSignUpCoach->createView(),
        'users'=>$users
    ]);
}
#[Route('/details/{id}', name: 'app_admin_userDetails' ,methods: ['GET', 'POST'], requirements: ['id' => '\d+'])]
public function SiwtchToUserDetails($id,ProfileRepository $profileRepository,UserRepository $userRepository , NotesRepository $notesRepository): Response
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
    $user = $this->session->get('user');
    $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
    $userDetails= $userRepository->findOneBy(['id'=>$id]);
    $notes= $notesRepository->findNotesOrderDESC($id);
    return $this->render('AdminTemplate/UserDetails.html.twig', [
        'profile' => $profile,
        'userDetails'=>$userDetails,
        'notes'=>$notes

    ]);
}
}


