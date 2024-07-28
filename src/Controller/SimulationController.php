<?php

namespace App\Controller;

use App\Entity\Dossier;
use App\Entity\Offre;
use App\Entity\User;
use App\Repository\DossierRepository;
use App\Repository\OffreRepository;
use App\Repository\ProfileRepository;
use App\Repository\SocieteRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManager;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

class SimulationController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/simulation', name: 'app_simulation')]
    public function index(ProfileRepository $profileRepository): Response
    {    if(isset($_COOKIE['ConnectedUser']))
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
        $profile = $profileRepository->findProfileByUserId($user->getId());
        $response = new Response($this->renderView('WebTest3D/index.html.twig', [
            'controller_name' => 'SimulationController',
            'profile'=>$profile
        ]));
        $response->headers->set('Access-Control-Allow-Origin','*');
        return $response;
    }

    #[Route('/simulation/societe', name: 'societe_data', methods: ['GET'])]
    public function getSociete(SocieteRepository $societeRepository): Response
    {
         $response =  new Response($this->renderView('WebTest3D/societe.html.twig', [
            'controller_name' => 'SimulationController',
            'societes' => $societeRepository->findAll()
        ]));

        $response->headers->set('Access-Control-Allow-Origin','*');

        return $response;
    }

    #[Route('/simulation/dossier', name: 'dossier_data', methods: ['GET'])]
    public function getDossier(DossierRepository $dossierRepository): Response
    {
        $idUser = 4;
        if(isset($_COOKIE['ConnectedUser']))
        {
            $idUser = $_COOKIE['ConnectedUser'];
        }
        
         $response = new Response($this->renderView('WebTest3D/dossier.html.twig', [
            'controller_name' => 'SimulationController',
            'dossiers' => $dossierRepository->findBy(['idUser' => $idUser])

        ]));

        $response->headers->set('Access-Control-Allow-Origin','*');

        return $response;
    }

    #[Route('/simulation/offre/{id}', name: 'offre_data', methods: ['GET'])]
    public function getOffre(OffreRepository $offreRepository, $id): Response
    {
         $response =  new Response($this->renderView('WebTest3D/offre.html.twig', [
            'controller_name' => 'SimulationController',
            'offres' => $offreRepository->findby(['idSociete' => $id])
        ]));

        $response->headers->set('Access-Control-Allow-Origin','*');

        return $response;
    }

    #[Route('/simulation/user', name: 'user_data', methods: ['GET'])]
    public function getUserData(UserRepository $userRepository): Response
    {
        $idUser = 4;
        if(isset($_COOKIE['ConnectedUser']))
        {
            $idUser = $_COOKIE['ConnectedUser'];
        }
        
         $response = new Response($this->renderView('WebTest3D/user.html.twig', [
            'controller_name' => 'SimulationController',
            'users' => $userRepository->findBy(['id' => $idUser])

        ]));

        $response->headers->set('Access-Control-Allow-Origin','*');

        return $response;
    }

    #[Route('/simulation/addDossier/{id}', name: 'simulation_dossier_ajout', methods: ['GET'])]
    public function addDossier(EntityManagerInterface $entityManager, $id,UserRepository $userRepository,OffreRepository $offreRepository)
    {
        $idUser = 4;
        if(isset($_COOKIE['ConnectedUser']))
        {
            $idUser = $_COOKIE['ConnectedUser'];
        }
        $offre = new Offre();
        $offre = $offreRepository->findOneById($id);
        $user = new User();
        $user = $userRepository->findOneById($idUser);
        $dossier = new Dossier();
        $dossier->setIdOffre($offre);
        $dossier->setIdUser($user);
        $dossier->setStatus("En Attente");
        $entityManager->persist($dossier);
        $entityManager->flush();

        return $this->render('WebTest3D/ajoutDossier.html.twig', [
            'controller_name' => 'SimulationController',
            'dossier' => $dossier]);
    }


}
