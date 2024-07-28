<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/produit')]
class ProduitController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    
    #[Route('/show/{id}', name: 'app_produit_show', methods: ['GET','POST'])]
     
     public function show(ProduitRepository $produitRepository, int $id,ProfileRepository $profileRepository): Response
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
         $produit = $produitRepository->find($id);
     
         if (!$produit) {
             throw $this->createNotFoundException('Aucun produit trouvé pour cet id '.$id);
         }
     
         return $this->render('produit/show.html.twig', [
             'produit' => $produit,
             'profile'=>$profile
         ]);
     }
     
    #[Route('/', name: 'app_produit_index', methods: ['GET', 'POST'])]
public function index(Request $request, EntityManagerInterface $entityManager ,ProduitRepository $produitRepository,ProfileRepository $profileRepository): Response
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
    $produit = new Produit();
    $form = $this->createForm(ProduitType::class, $produit);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $entityManager->persist($produit);
        $entityManager->flush();
        return $this->redirectToRoute('app_produit_index');
    }

    return $this->render('produit/index.html.twig', [
        'produits' => $produitRepository->findAll(),
        'formProduit' => $form->createView(), // This line is crucial
        'profile'=>$profile
    ]);
}

    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
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
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($produit);
            $entityManager->flush();
            return $this->redirectToRoute('app_produit_index');
        }

        return $this->renderForm('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/{id}/edit', name: 'app_produit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Produit $produit, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            return $this->redirectToRoute('app_produit_index');
        }

        return $this->renderForm('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/{id}', name: 'app_produit_delete', methods: ['POST'])]
    public function delete(Request $request, Produit $produit, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
    {        if(isset($_COOKIE['ConnectedUser']))
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
        if ($this->isCsrfTokenValid('delete' . $produit->getId(), $request->request->get('_token'))) {
            $entityManager->remove($produit);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_produit_index');
    }

    #[Route('/search', name: 'search_path', methods: ['GET'])]
    public function search(Request $request, ProduitRepository $produitRepository): Response
    {     if(isset($_COOKIE['ConnectedUser']))
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
        $query = $request->query->get('q');
        $produits = $produitRepository->findBySearchQuery($query);
        return $this->render('produit/index.html.twig', [
            'produits' => $produits,
        ]);
    }

    #[Route('/sort', name: 'catalogue_path', methods: ['GET'])]
    public function sort(Request $request, ProduitRepository $produitRepository): Response
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
        $sortOrder = $request->query->get('sort', 'asc');
        $produits = $produitRepository->findBy([], ['prix_init' => $sortOrder]);
        return $this->render('produit/index.html.twig', [
            'produits' => $produits,
        ]);
    }
}