<?php

namespace App\Controller;

use App\Entity\Bibliotheque;
use App\Form\BibliothequeType;
use App\Repository\BibliothequeRepository;
use App\Repository\LivreRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/bibliotheque')]
class BibliothequeController extends AbstractController
{
    private $session;
    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }
    #[Route('/', name: 'app_bibliotheque_index', methods: ['GET'])]
    public function index(Request $request,BibliothequeRepository $bibliothequeRepository,ProfileRepository $profileRepository): Response
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
        $orderBy = [];

        // Vérifier si un paramètre de tri est passé dans l'URL
        $sort = $request->query->get('sort');
        if ($sort) {
            switch ($sort) {
                case 'id_asc':
                    $orderBy['id'] = 'ASC';
                    break;
                case 'id_desc':
                    $orderBy['id'] = 'DESC';
                    break;
                case 'nom_asc':
                    $orderBy['nom'] = 'ASC';
                    break;
                case 'nom_desc':
                    $orderBy['nom'] = 'DESC';
                    break;
                case 'nbrLivre_asc':
                    $orderBy['nbrLivre'] = 'ASC';
                    break;
                case 'nbrLivre_desc':
                    $orderBy['nbrLivre'] = 'DESC';
                    break;
            }
        }

        // Récupérer les bibliothèques selon les critères de tri
        $bibliotheques = $bibliothequeRepository->findBy([], $orderBy);

    
        $user = $this->session->get('user');
       $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        return $this->render('bibliotheque/index.html.twig', [
            'bibliotheques' => $bibliotheques,
            'profile' => $profile
        ]);
    }
    #[Route('/front', name: 'app_bibliothequeFront_index', methods: ['GET', 'POST'])]
    public function SwitchToBiliotheque(BibliothequeRepository $bibliothequeRepository): Response
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

        // Récupérer les bibliothèques selon les critères de tri
        $bibliotheques = $bibliothequeRepository->findAll();

        return $this->render('StudentTemplate/BibliothequePage.html.twig', [
            'bibliotheques' => $bibliotheques,
        ]);
    }
    
    #[Route('/front/{id}/livre', name: 'app_livreFront_index', methods: ['GET', 'POST'])]
    public function SwitchToLivre($id,LivreRepository $livreRepository): Response
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
        // Récupérer les bibliothèques selon les critères de tri
        $livres = $livreRepository->findBy(['bibliotheque' => $id]);

        return $this->render('StudentTemplate/BookPage.html.twig', [
            'livres' => $livres,
        ]);
    }

    #[Route('/new', name: 'app_bibliotheque_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        $bibliotheque = new Bibliotheque();
        $form = $this->createForm(BibliothequeType::class, $bibliotheque);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($bibliotheque);
            $entityManager->flush();

            return $this->redirectToRoute('app_bibliotheque_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('bibliotheque/new.html.twig', [
            'bibliotheque' => $bibliotheque,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('/show/{id}', name: 'app_bibliotheque_show', methods: ['GET'])]
    public function show(Bibliotheque $bibliotheque, ProfileRepository $profileRepository): Response
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
        return $this->render('bibliotheque/show.html.twig', [
            'bibliotheque' => $bibliotheque,
            'profile' => $profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_bibliotheque_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Bibliotheque $bibliotheque, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        $form = $this->createForm(BibliothequeType::class, $bibliotheque);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_bibliotheque_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('bibliotheque/edit.html.twig', [
            'bibliotheque' => $bibliotheque,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('/delete/{id}', name: 'app_bibliotheque_delete', methods: ['POST'])]
    public function delete(Request $request, Bibliotheque $bibliotheque, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$bibliotheque->getId(), $request->request->get('_token'))) {
            $entityManager->remove($bibliotheque);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_bibliotheque_index', [], Response::HTTP_SEE_OTHER);
    }
    
}
