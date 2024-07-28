<?php

namespace App\Controller;

use App\Entity\Livre;
use App\Form\LivreType;
use App\Repository\LivreRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManagerInterface;
use Dompdf\Dompdf;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/livre')]
class LivreController extends AbstractController
{
    private $session;
    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }
    #[Route('/', name: 'app_livre_index', methods: ['GET'])]
    public function index(Request $request,LivreRepository $livreRepository,ProfileRepository $profileRepository): Response
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
                case 'idd_asc':
                    $orderBy['id'] = 'ASC';
                    break;
                case 'idd_desc':
                    $orderBy['id'] = 'DESC';
                    break;
                case 'type_asc':
                    $orderBy['type'] = 'ASC';
                    break;
                case 'type_desc':
                    $orderBy['type'] = 'DESC';
                    break;
                case 'titre_asc':
                    $orderBy['titre'] = 'ASC';
                    break;
                case 'titre_desc':
                    $orderBy['titre'] = 'DESC';
                    break;
                case 'auteur_asc':
                    $orderBy['auteur'] = 'ASC';
                    break;
                case 'auteur_desc':
                    $orderBy['auteur'] = 'DESC';
                    break;
                case 'langue_asc':
                    $orderBy['langue'] = 'ASC';
                    break;
                case 'langue_desc':
                    $orderBy['langue'] = 'DESC';
                    break;
            }
        }

        // Récupérer les livres selon les critères de tri
        $livres = $livreRepository->findBy([], $orderBy);

        $user = $this->session->get('user');
       $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        return $this->render('livre/index.html.twig', [
            'livres' => $livres,
            'profile' => $profile
        ]);
    }

    #[Route('/new', name: 'app_livre_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, ProfileRepository $profileRepository): Response
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
        $livre = new Livre();
        $form = $this->createForm(LivreType::class, $livre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($livre);
            $entityManager->flush();

            return $this->redirectToRoute('app_livre_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('livre/new.html.twig', [
            'livre' => $livre,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('/show/{id}', name: 'app_livre_show', methods: ['GET'])]
    public function show(Livre $livre, ProfileRepository $profileRepository): Response
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
        return $this->render('livre/show.html.twig', [
            'livre' => $livre,
            'profile' => $profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_livre_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Livre $livre, EntityManagerInterface $entityManager, ProfileRepository $profileRepository): Response
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
        $form = $this->createForm(LivreType::class, $livre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_livre_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('livre/edit.html.twig', [
            'livre' => $livre,
            'form' => $form,
            'profile' => $profile
        ]);
    }

    #[Route('/delete/{id}', name: 'app_livre_delete', methods: ['POST'])]
    public function delete(Request $request, Livre $livre, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$livre->getId(), $request->request->get('_token'))) {
            $entityManager->remove($livre);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_livre_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/livre/{id}/pdf', name: 'app_livre_pdf', methods: ['GET'])]
    public function generatePdf(Livre $livre): Response
{    if(isset($_COOKIE['ConnectedUser']))
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
    // Rendu de la vue Twig avec les données du livre
    $htmlContent = $this->renderView('livre/pdf_livre.html.twig', [
        'livre' => $livre,
    ]);

    // Création de l'instance Dompdf
    $dompdf = new Dompdf();

    // Chargement du contenu HTML et rendu
    $dompdf->loadHtml($htmlContent);
    $dompdf->setPaper('A4', 'portrait');
    $dompdf->render();

    // Renvoi de la réponse HTTP avec le contenu du PDF
    return new Response(
        $dompdf->output(),
        Response::HTTP_OK,
        ['Content-Type' => 'application/pdf']
    );
}

}
