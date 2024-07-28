<?php

namespace App\Controller;

use App\Entity\Exams;
use App\Entity\ProfileCoach;
use App\Entity\Questions;
use App\Entity\User;
use App\Entity\Modules;
use App\Form\ExamsType;
use App\Repository\ExamsRepository;
use App\Repository\ModuleRepository;
use App\Repository\ModulesRepository;
use App\Repository\NotesRepository;
use App\Repository\ProfileCoachRepository;
use App\Repository\ProfileRepository;
use App\Repository\QuestionsRepository;
use App\Repository\SuiviRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Doctrine\ORM\Mapping\Id;
use Google\Service\AlertCenter\Alert;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Serializer\SerializerInterface;

#[Route('/exams')]
class ExamsController extends AbstractController
{

    private $session;
    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }


    #[Route('/', name: 'app_exam_index', methods: ['GET'])]
    public function index(ExamsRepository $examsRepository, ProfileRepository $profileRepository,ProfileCoachRepository $profileCoachRepository): Response
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
        $user = $this->session->get('user');
       $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
       $profileCoach=$profileCoachRepository->findOneBy(['idProfile'=>$profile->getId()]);
        return $this->render('exams/index.html.twig', [
            'exams' => $examsRepository->findByModuleAsCoach($profileCoach->getIdModule()),
            'profile' => $profile
        ]);
    }
    #[Route('/front', name: 'app_exams_index', methods: ['GET'])]
    public function indexfront(ExamsRepository $examsRepository, SuiviRepository $suiviRepository , ProfileRepository $profileRepository,ModulesRepository $moduleRepository,NotesRepository $notesRepository): Response
    { if(isset($_COOKIE['ConnectedUser']))
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
        $exams = $examsRepository->findAll();
        $entityManager = $this->getDoctrine()->getManager();
        $user = $entityManager->find(User::class, $user->getId());
        $suivis = $suiviRepository->findBy(['idUser' => $user]);
        $moduleNames = [];
        if (!empty($suivis)) {
        foreach ($suivis as $suivi) {
            $module = $suivi->getIdModule();
            if ($module) {
                $moduleName = $module->getNom();
                if (!in_array($moduleName, $moduleNames)) {
                    $moduleNames[] = $moduleName;
                }
            }
        }
    }
    // $moduledescriptions = [];
    // if (!empty($moduleNames)) {
    //     foreach ($moduleNames as $modulename) {
    //        $mod= $moduleRepository->findOneBy(['nom'=>$modulename ]);
    //        $moduledescription = $mod->getDescription();
    //        $moduledescriptions[] = $moduledescription;
    //     }
    // }
    
        $filteredExams = [];
        foreach ($exams as $exam) {
            if (in_array($exam->getModule(), $moduleNames)) {
                $filteredExams[] = $exam;
            }
        }
        $notes=$notesRepository->findBy(['idUser'=>$user->getId()]);
        $mod=[];
        foreach($notes as $notee)
        {
            $mod[]=$notee->getModule()->getNom();
        }

        $examnotpassed=[];
        foreach ($filteredExams as $ex) {
            if (! in_array($ex->getModule(), $mod)) {
                $examnotpassed[] = $ex;
            }
        }
        $moduledescriptions1 = [];
        $moduleimages=[];
    if (!empty($examnotpassed)) {
        foreach ($examnotpassed as $ex) {
           $mod= $moduleRepository->findOneBy(['nom'=>$ex->getModule() ]);
           $moduledescription = $mod->getDescription();
           $moduledeimage = $mod->getImgPath();
           $moduledescriptions1[] = $moduledescription;
           $moduleimages[] =$moduledeimage;
        }
    }

        
    
        $events = [];
        foreach ($examnotpassed as $exam) {
            $events[] = [
                'id' => $exam->getId(),
                'title' => $exam->getModule(),
                'start' => $exam->getDate()->format('Y-m-d')
            ];
        }
        $currentDate = date('Y-m-d');
        return $this->render('exams/calendar.html.twig', [
            'events' => json_encode($events),
            'exams' => $examnotpassed,
            'moduleNames' => $moduleNames,
            'profile'=>$profile,
            'descriptions'=>$moduledescriptions1,
            'images'=>$moduleimages,
            'dateTest' => $currentDate
        ]);
    }
    #[Route('/search', name: 'search_exams_ajax', methods: ['GET'])]
    public function searchExamsAjax(Request $request, ExamsRepository $examsRepository, SerializerInterface $serializer): JsonResponse
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
        $searchTerm = $request->query->get('searchTerm');
        
        // Query the repository to get filtered exams based on the search term
        $exams = $examsRepository->findBySearchTerm($searchTerm); // You need to implement findBySearchTerm method in ExamsRepository

        // Serialize the data to JSON format with the specified serialization group
        $jsonData = $serializer->serialize($exams, 'json', ['groups' => 'exams']);

        return new JsonResponse($jsonData, 200, [], true);
    }
    
    



    #[Route('/new', name: 'app_exams_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, UrlGeneratorInterface $urlGenerator,ProfileRepository $profileRepository,ProfileCoachRepository $profileCoachRepository): Response
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
        $user = $this->session->get('user');
        $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $profileCoach = $profileCoachRepository->findOneBy(['idProfile'=>$profile->getId()]);
        $exam = new Exams();
        $form = $this->createForm(ExamsType::class, $exam);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $exam->setModule($profileCoach->getIdModule());

            $entityManager->persist($exam);
            $entityManager->flush();
    
            // Get the date from the exam object
            $date = $exam->getDate()->format('d-m-Y');
    
            // Get the module from the exam object
            $module = $exam->getModule();
    
            // Generate the URL for app_google_calendar with the date and module parameters
            $url = $urlGenerator->generate('app_google_calendar', [
                'date' => $date,
                'module' => $module,
            ]);
    
            // Redirect to app_google_calendar with the date and module parameters
            return new RedirectResponse($url);
        }
    
        return $this->renderForm('exams/new.html.twig', [
            'exam' => $exam,
            'form' => $form,
            'profile'=>$profile
        ]);
    }
        #[Route('/results', name: 'results_page')]
public function showResults(Request $request, QuestionsRepository $questionsRepository, ProfileRepository $profileRepository): Response
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
    $answersParam = $request->query->get('answers');
    $scoreParam = $request->query->get('score');

    $questions = $questionsRepository->findAll();
    $answers = json_decode(urldecode($answersParam), true);
    $score = json_decode(urldecode($scoreParam), true);

    return $this->render('exams/results.html.twig', [
        'questions' => $questions,
        'answers' => json_encode($answers),
        'score' => json_encode($score),
        'profile' => $profile
    ]);
}




    #[Route('show/{id}', name: 'app_exams_show', methods: ['GET'])]
    public function show(Exams $exam, QuestionsRepository $questionsRepository, SessionInterface $session,ProfileRepository $profileRepository): Response
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
    $user = $this->session->get('user');
    $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $session->set('exam_id', $exam->getId());
        $questions = $questionsRepository->findBy(['idExam' => $exam]);
        return $this->render('questions/index.html.twig', [
            'questions' => $questions,
            'profile' => $profile
        ]);
    }
   
    #[Route('/test/{id}', name: 'app_exams_showtest', methods: ['GET'])]
    public function showtest(Exams $exam, QuestionsRepository $questionsRepository, SessionInterface $session, ProfileRepository $profileRepository): Response
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
        $session->set('exam_id', $exam->getId());
        $questions = $questionsRepository->findBy(['idExam' => $exam]);
        return $this->render('exams/Quiz.html.twig', [
            'questions' => $questions,
            'exam' => $exam,
            'profile' => $profile
        ]);
    }


    #[Route('/edit/{id}', name: 'app_exams_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Exams $exam, EntityManagerInterface $entityManager, ProfileRepository $profileRepository): Response
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
    $user = $this->session->get('user');
    $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $form = $this->createForm(ExamsType::class, $exam);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_exam_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('exams/edit.html.twig', [
            'exam' => $exam,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('delete/{id}', name: 'app_exams_delete', methods: ['GET','POST'])]
    public function delete(Request $request, Exams $exam, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$exam->getId(), $request->request->get('_token'))) {
            $entityManager->remove($exam);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_exam_index', [], Response::HTTP_SEE_OTHER);
    }
}
