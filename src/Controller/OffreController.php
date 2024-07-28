<?php

namespace App\Controller;

use App\Entity\Offre;
use App\Form\OffreType;
use App\Form\OffreUpdateType;
use App\Repository\DossierRepository;
use App\Repository\OffreRepository;
use App\Repository\ProfileAdminRepository;
use App\Repository\ProfileRepository;
use App\Repository\SocieteRepository;
use DateTimeInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Validator\ValidatorInterface;

#[Route('/offre')]
class OffreController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/', name: 'app_offre_index', methods: ['GET' , 'POST'])]
    public function index(Request $request,EntityManagerInterface $entityManager,OffreRepository $offreRepository,ProfileRepository $profileRepository,ProfileAdminRepository $profileAdminRepository): Response
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
        $offre = new Offre();
        $form = $this->createForm(OffreType::class, $offre);
        $formUpdate = $this->createForm(OffreUpdateType::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($offre);
            $entityManager->flush();

            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }
        $formUpdate->handleRequest($request);
        if($formUpdate->isSubmitted())
        {
            
           
           $data= $formUpdate->getData();
           $offre = $offreRepository->findOneBy(['id' => $data->getId()]);
           $offre->setDescription($data->getDescription());
           $offre->setNiveau($data->getNiveau());
           $offre->setDateDebut($data->getDateDebut());
           $offre->setDateFin($data->getDateFin());
           $offre->setIdSociete($data->getIdSociete());
            $entityManager->flush();
            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }
        $user = $this->session->get('user');
        $profile = $profileRepository->findProfileByUserId($user->getId());
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        return $this->render('offre/index.html.twig', [
            'offres' => $offreRepository->findAll(),
            'profile'=>$profile,
            'user'=>$user,
            'formOffre' => $form->createView(),
            'formOffreUpdate' => $formUpdate->createView()
        ]);
    }
    #[Route('/new', name: 'app_offre_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
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
        $offre = new Offre();
        $form = $this->createForm(OffreType::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($offre);
            $entityManager->flush();

            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('offre/new.html.twig', [
            'offre' => $offre,
            'form' => $form,
        ]);
    }

    #[Route('/details/{id}', name: 'app_offre_show', methods: ['GET'])]
    public function show(Offre $offre , ProfileRepository $profileRepository): Response
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
        $profile = $profileRepository->findOneBy(['idUser'=>$user]);
        return $this->render('offre/show.html.twig', [
            'offre' => $offre,
            'profile'=>$profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_offre_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Offre $offre, EntityManagerInterface $entityManager , ProfileRepository $profileRepository): Response
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
        $profile = $profileRepository->findOneBy(['idUser'=>$user]);
        $form = $this->createForm(OffreType::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('offre/edit.html.twig', [
            'offre' => $offre,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/delete/{id}', name: 'app_offre_delete', methods: ['POST'])]
    public function delete(Request $request, Offre $offre, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$offre->getId(), $request->request->get('_token'))) {
            $entityManager->remove($offre);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/triOffreAjax', name: 'app_offre_tri', methods: ['GET', 'POST'])]
public function triSociete(Request $request, OffreRepository $offreRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $offres = $offreRepository->findAllSorted($_POST['ordre']);
        for($i = 0; $i < count($offres); $i++)
        {
            $jsonData[$i]['id'] = $offres[$i]->getId();
            $jsonData[$i]['niveau'] = $offres[$i]->getNiveau();
            $jsonData[$i]['description'] = $offres[$i]->getDescription();
            $jsonData[$i]['dateDebut'] = $offres[$i]->getDateDebut()->format('Y-m-d');
            $jsonData[$i]['dateFin'] = $offres[$i]->getDateFin()->format('Y-m-d');
            $jsonData[$i]['actions'] = "<a href=\"details/".$offres[$i]->getId()."\">show</a><a href=\"edit/".$offres[$i]->getId()."\"> edit</a>";
        }
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse("HELLO");
    }
}

#[Route('/checkAjax', name: 'app_offre_controle', methods: ['GET', 'POST'])]
public function controleSociete(Request $request, ValidatorInterface $validator, EntityManagerInterface $entityManager, SocieteRepository $societeRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $niveau = (int) $request->request->get('niveau');
        $description = $request->request->get('description');
        $dayDateDeb = $request->request->get('dayDateDeb');
        $yearDateDeb = $request->request->get('yearDateDeb');
        $monthDateDeb = $request->request->get('monthDateDeb');
        $dayDateFin = $request->request->get('dayDateFin');
        $yearDateFin = $request->request->get('yearDateFin');
        $monthDateFin = $request->request->get('monthDateFin');
        $date = $yearDateDeb."-".$monthDateDeb."-".$dayDateDeb;
        $dateDeb=date_create($date);
        $date = $yearDateFin."-".$monthDateFin."-".$dayDateFin;
        $dateFin = date_create($date);
        $idSociete = $request->request->get('idSociete');
        $societe = $societeRepository->findOneBy(['id' => $idSociete]);
        $offre = new Offre();
        $offre->setDescription($description);
        $offre->setNiveau($niveau);
        $offre->setDateDebut($dateDeb);
        $offre->setDateFin($dateFin);
        $offre->setIdSociete($societe);
        $errors = $validator->validate($offre);
        if(count($errors) > 0)
        {
            foreach ($errors as $violation) {
                $jsonData[$violation->getPropertyPath()][] = $violation->getMessage();
            }
            return new JsonResponse($jsonData);
        }
        else
        {
            return new JsonResponse($jsonData);
        }
    }
    else
    {
       return new JsonResponse();
    }
}

#[Route('/offreSearch', name: 'app_offre_search', methods: ['GET', 'POST'])]
public function searchOffre(Request $request, OffreRepository $offreRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $offres = $offreRepository->findBySearch($_POST['search']);
        for($i = 0; $i < count($offres); $i++)
        {
            $jsonData[$i]['id'] = $offres[$i]->getId();
            $jsonData[$i]['niveau'] = $offres[$i]->getNiveau();
            $jsonData[$i]['description'] = $offres[$i]->getDescription();
            $jsonData[$i]['dateDebut'] = $offres[$i]->getDateDebut()->format('Y-m-d');
            $jsonData[$i]['dateFin'] = $offres[$i]->getDateFin()->format('Y-m-d');
            $jsonData[$i]['societe'] = $offres[$i]->getIdSociete()->getNomSociete();
            $jsonData[$i]['actions'] = "<a href=\"#\" onclick=\"callEdit(".$offres[$i]->getId().")\"> edit </a><a href=\"details/".$offres[$i]->getId()."\"> show </a>";
        }
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse("HELLO");
    }
}

#[Route('/offreData', name: 'app_offre_get_data', methods: ['GET', 'POST'])]
public function getData(Request $request, OffreRepository $offreRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $offre = $offreRepository->findOneBy(['id' => $_POST['idOffre']]);
        $jsonData['idOffre'] = $offre->getId();
        $jsonData['niveau'] = $offre->getNiveau();
        $jsonData['description'] = $offre->getDescription();
        $jsonData['dateDeb'] = $offre->getDateDebut();
        $jsonData['dateFin'] = $offre->getDateFin();
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse("HELLO");
    }
}

}