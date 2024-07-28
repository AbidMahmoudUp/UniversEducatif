<?php

namespace App\Controller;

use App\Entity\Dossier;
use App\Form\DossierType;
use App\Repository\DossierRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/dossier')]
class DossierController extends AbstractController
{
    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }
    #[Route('/', name: 'app_dossier_index', methods: ['GET'])]
    public function index(DossierRepository $dossierRepository , ProfileRepository $profileRepository): Response
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
        return $this->render('dossier/index.html.twig', [
            'dossiers' => $dossierRepository->findAll(),
            'profile'=>$profile
        ]);
    }

    #[Route('/new', name: 'app_dossier_new', methods: ['GET', 'POST'])]
    public function new(Request $request,  $entityManager): Response
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
        $dossier = new Dossier();
        $form = $this->createForm(DossierType::class, $dossier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($dossier);
            $entityManager->flush();

            return $this->redirectToRoute('app_dossier_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('dossier/new.html.twig', [
            'dossier' => $dossier,
            'form' => $form,
        ]);
    }

    #[Route('/details/{idUser}/{idOffre}', name: 'app_dossier_show', methods: ['GET'])]
    public function show(Dossier $dossier , ProfileRepository $profileRepository): Response
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
        return $this->render('dossier/show.html.twig', [
            'dossier' => $dossier,
            'profile'=>$profile
        ]);
    }

    #[Route('/edit/{idUser}/{idOffre}', name: 'app_dossier_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Dossier $dossier, EntityManagerInterface $entityManager , ProfileRepository $profileRepository): Response
    { if(isset($_COOKIE['ConnectedUser']))
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
        $form = $this->createForm(DossierType::class, $dossier);
        $form->handleRequest($request);

        $user = $this->session->get('user');
        $profile = $profileRepository->findOneBy(['idUser'=>$user]);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();
            $data = $form->getData();
            $status = $data->getStatus();
            $subject = $dossier->getIdOffre()->getIdSociete()->getNomSociete()." Reponse de votre dossier";
            $message = "";
            if(strcasecmp($status,"Accepté") == 0)
            {
                $message = "Félicitations pour avoir été accepté au sein de notre société ! Ton admission témoigne de ton talent exceptionnel et de ton engagement envers l'excellence. En tant que nouvel employé, tu fais maintenant partie intégrante de notre équipe dynamique et innovante. C'est une opportunité passionnante de contribuer au succès de notre société et de participer à des projets stimulants. Nous croyons fermement que tes compétences et ton enthousiasme vont enrichir notre environnement de travail. N'hésite pas à explorer les différentes ressources et à t'impliquer dans les activités de l'entreprise. Nous encourageons la collaboration et l'échange d'idées, car c'est ensemble que nous atteindrons de nouveaux sommets. Bienvenue dans notre équipe, nous sommes impatients de voir les grandes choses que nous accomplirons ensemble !";
                $this->sendMail($subject,$message,$dossier->getidUser()->getEmail());
                
            }
            else if(strcasecmp($status,"Refusé") == 0)
            {
                $message = "Nous comprenons que recevoir une notification de refus peut être décevant, mais nous tenons à te remercier sincèrement pour l'intérêt que tu as manifesté envers notre société. La sélection est toujours un processus difficile et compétitif, et la qualité des candidats était exceptionnelle. Bien que ta candidature n'ait pas été retenue pour le moment, cela ne doit pas être interprété comme un reflet de tes compétences et de ta valeur. Nous encourageons vivement à continuer à développer tes compétences et à explorer d'autres opportunités. Les chemins professionnels sont variés, et nous espérons que tu trouveras le cadre idéal pour mettre en valeur tes talents. Nous te remercions encore une fois pour ton intérêt et te souhaitons beaucoup de succès dans tes futures démarches professionnelles.";
                $this->sendMail($subject,$message,$dossier->getidUser()->getEmail());
               
            }
            return $this->redirectToRoute('app_dossier_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('dossier/edit.html.twig', [
            'dossier' => $dossier,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/delete/{idUser}/{idOffre}', name: 'app_dossier_delete', methods: ['POST'])]
    public function delete(Request $request, Dossier $dossier, EntityManagerInterface $entityManager): Response
    { if(isset($_COOKIE['ConnectedUser']))
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
        if ($this->isCsrfTokenValid('delete'.$dossier->getIdUser(), $request->request->get('_token'))) {
            $entityManager->remove($dossier);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_dossier_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/filtreAjax', name: 'app_dossier_filtre', methods: ['GET', 'POST'])]
public function filtreDossier(Request $request, DossierRepository $dossierRepository)
{

    if ($request->isXmlHttpRequest())
    {
        if($_POST['ordre'] == "")
        {
            $dossiers = $dossierRepository->findAll();
        }
        else
        {
            $dossiers = $dossierRepository->findByfiltre($_POST['ordre']);
        }

        $jsonData = array();
        
        for($i = 0; $i < count($dossiers); $i++)
        {
            $jsonData[$i]['idUser'] = $dossiers[$i]->getIdUser()->getUsername();
            $jsonData[$i]['idOffre'] = $dossiers[$i]->getIdOffre()->getDescription();
            $jsonData[$i]['status'] = $dossiers[$i]->getStatus();
            $jsonData[$i]['actions'] = "<a href=\"details/".$dossiers[$i]->getIdUser()."/".$dossiers[$i]->getIdOffre()."\">show</a><a href=\"edit/".$dossiers[$i]->getIdUser()."/".$dossiers[$i]->getIdOffre()."\"> edit</a>";
        }
        return new JsonResponse($jsonData);
    }
    else
    {
       return new JsonResponse();
    }
}

public function sendMail($subject,$content,$email)
{
            $tranport = Transport::fromDsn("smtp://amin.warghi@gmail.com:dywmwexhazvgtrjb@smtp.gmail.com:587");
            $mailer = new Mailer($tranport);
            $email = (new Email())
            ->from('amin.warghi@gmail.com')
            ->to($email)
            ->subject($subject)
            ->text($content);

                $mailer->send($email);
}
}