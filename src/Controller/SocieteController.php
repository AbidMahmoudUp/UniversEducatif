<?php

namespace App\Controller;

use App\Entity\Societe;
use App\Form\SocieteType;
use App\Repository\OffreRepository;
use App\Repository\ProfileAdminRepository;
use App\Repository\ProfileRepository;
use App\Repository\SocieteRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Validator\ValidatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;

#[Route('/societe')]
class SocieteController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/', name: 'app_societe', methods: ['GET','POST'])]
    public function index(Request $request,EntityManagerInterface $entityManager,SocieteRepository $societeRepository,ProfileRepository $profileRepository,ProfileAdminRepository $profileAdminRepository): Response
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
        $societe = new Societe();
        $form = $this->createForm(SocieteType::class, $societe);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($societe);
            $entityManager->flush();

            return $this->redirectToRoute('app_societe', [], Response::HTTP_SEE_OTHER);
        }
        $user = $this->session->get('user');
        $profile = $profileRepository->findProfileByUserId($user->getId());
        $profileAdmin = $profileAdminRepository->findOneBy(["idProfile"=>$profile->getId()]);
        return $this->render('societe/index.html.twig', [
            'societes' => $societeRepository->findAll(),
            'profile'=>$profile,
            'user'=>$user,
            'formSociete' => $form->createView(),

        ]);
    }

    #[Route('/new', name: 'app_societe_new', methods: ['GET', 'POST'])]
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
        $societe = new Societe();
        $form = $this->createForm(SocieteType::class, $societe);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($societe);
            $entityManager->flush();

            return $this->redirectToRoute('app_societe', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('societe/new.html.twig', [
            'societe' => $societe,
            'form' => $form,
        ]);
    }

    #[Route('/details/{id}', name: 'app_societe_show', methods: ['GET'])]
    public function show(Societe $societe, ProfileRepository $profileRepository): Response
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
        return $this->render('societe/show.html.twig', [
            'societe' => $societe,
            'profile'=>$profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_societe_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Societe $societe, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        $form = $this->createForm(SocieteType::class, $societe);
        $form->handleRequest($request);

        $user = $this->session->get('user');
        $profile = $profileRepository->findOneBy(['idUser'=>$user]);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_societe', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('societe/edit.html.twig', [
            'societe' => $societe,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/delete/{id}', name: 'app_societe_delete', methods: ['POST'])]
    public function delete(Request $request, Societe $societe, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$societe->getId(), $request->request->get('_token'))) {
            $entityManager->remove($societe);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_societe', [], Response::HTTP_SEE_OTHER);
    }


    
#[Route('/triSocieteAjax', name: 'app_societe_tri', methods: ['GET', 'POST'])]
public function triSociete(Request $request, SocieteRepository $societeRepository)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $societes = $societeRepository->findAllSorted($_POST['ordre']);
        for($i = 0; $i < count($societes); $i++)
        {
            $jsonData[$i]['id'] = $societes[$i]->getId();
            $jsonData[$i]['nomSociete'] = $societes[$i]->getNomSociete();
            $jsonData[$i]['description'] = $societes[$i]->getDescription();
            $jsonData[$i]['actions'] = "<a href=\"details/".$societes[$i]->getId()."\">show</a><a href=\"edit/".$societes[$i]->getId()."\"> edit</a>";
        }
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse("HELLO");
    }
}

    
#[Route('/checkAjax', name: 'app_societe_controle', methods: ['GET', 'POST'])]
public function controleSociete(Request $request, ValidatorInterface $validator, EntityManagerInterface $entityManager)
{

    if ($request->isXmlHttpRequest())
    {
        $jsonData = array();
        $nom = $request->request->get('nom');
        $description = $request->request->get('description');
        $societe = new Societe();
        $societe->setNomSociete($nom);
        $societe->setDescription($description);
        $errors = $validator->validate($societe);
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




#[Route('/pdf/{id}', name: 'app_societe_pdf', methods: ['GET', 'POST'])]
    public function generatePDF(OffreRepository $offreRepository,SocieteRepository $societeRepository, $id): Response
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

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $name = $societeRepository->findOneBy(['id' => $id])->getNomSociete();
        $dompdf = new Dompdf($pdfOptions);

        $html = $this->renderView('societe/pdf.html.twig', [
            'title' => "Les offres", 'offres' => $offreRepository->findBy(['idSociete' => $id]),
            'nomSociete' => $name
        ]);

        $dompdf->loadHtml($html);

        $dompdf->setPaper('A4', 'portrait');

        $dompdf->render();

        $pdfContent = $dompdf->output();


        $response = new Response($pdfContent);

        $response->headers->set('Content-Type', 'application/pdf');

        $response->headers->set('Content-Disposition', 'attachment; filename="Societe_Offres.pdf"');

        return $response;
    }

}