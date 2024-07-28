<?php

namespace App\Controller;

use App\Entity\Exams;
use App\Entity\Modules;
use App\Entity\Notes;
use App\Entity\ProfileCoach;
use App\Entity\User;
use App\Form\NotesType;
use App\Repository\ExamsRepository;
use App\Repository\ModulesRepository;
use App\Repository\NotesRepository;
use App\Repository\ProfileCoachRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/notes')]
class NotesController extends AbstractController
{

    private $session;
    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }
    
    #[Route('/', name: 'app_notes_index', methods: ['GET'])]
    public function index(NotesRepository $notesRepository, ProfileRepository $profileRepository): Response
    {    if(isset($_COOKIE['ConnectedUser']))
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
        return $this->render('notes/index.html.twig', [
            'notes' => $notesRepository->findAll(),
            'profile' => $profile
        ]);
    }

    #[Route('/new', name: 'app_notes_new', methods: ['POST','GET'])]
public function new(Request $request, EntityManagerInterface $entityManager, ProfileRepository $profileRepository,ProfileCoachRepository $profileCoachRepository , ModulesRepository $modulesRepository,ExamsRepository $examsRepository): Response
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
    $examId = $request->request->get('examId');
    $userId = $request->request->get('userId');
    $score = $request->request->get('score');
    $note = new Notes();
    $note->setNote($score);
    
    // Retrieve the corresponding Exam and User entities from the database
    $exam = $entityManager->getRepository(Exams::class)->find($examId);
    $user = $entityManager->getRepository(User::class)->find($userId);
    $module=$modulesRepository->findOneBy(['nom'=>$exam->getModule()]);
    // Set the Exam and User entities for the Notes object
    $note->setIdexam($exam);
    $note->setIdUser($user);
    $note->setModule($module);
    // Persist the Notes object to the database
    $entityManager->persist($note);
    $entityManager->flush();

    // Return a success response
    return new Response('Note added successfully', Response::HTTP_OK);
}
    #[Route('show/{idexam}', name: 'app_notes_show', methods: ['GET'])]
    public function show(Notes $note, ProfileRepository $profileRepository): Response
    {    if(isset($_COOKIE['ConnectedUser']))
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
        return $this->render('notes/show.html.twig', [
            'note' => $note,
            'profile' => $profile
        ]);
    }

    #[Route('/edit/{idexam}', name: 'app_notes_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Notes $note, EntityManagerInterface $entityManager, ProfileRepository $profileRepository): Response
    {
        $user = $this->session->get('user');
        $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        $form = $this->createForm(NotesType::class, $note);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_notes_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('notes/edit.html.twig', [
            'note' => $note,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('delete/{idexam}', name: 'app_notes_delete', methods: ['POST'])]
    public function delete(Request $request, Notes $note, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$note->getIdexam(), $request->request->get('_token'))) {
            $entityManager->remove($note);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_notes_index', [], Response::HTTP_SEE_OTHER);
    }
}
