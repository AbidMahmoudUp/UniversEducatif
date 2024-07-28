<?php

namespace App\Controller;

use App\Entity\Cours;
use App\Entity\Modules;
use App\Entity\Suivi;
use App\Form\CoursType;
use App\Repository\CoursRepository;
use App\Repository\ProfileCoachRepository;
use App\Repository\ProfileRepository;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Smalot\PdfParser\Parser;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\VarDumper\VarDumper;




#[Route('/cours')]
class CoursController extends AbstractController
{
    private $session;
    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }

    #[Route('/', name: 'app_cours_index', methods: ['GET'])]
    public function index(CoursRepository $coursRepository, ProfileRepository $profileRepository): Response
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
       $profileCoach = $profile->getProfileCoach();
        return $this->render('cours/index.html.twig', [
            'cours' => $coursRepository->findBy(['ID_Module' => $profileCoach->getIdModule()]),
            'profile' => $profile
        ]);
    }
    #[Route('/cours/{id}', name: 'app_cours_show_ByIdModule', methods: ['GET'])]
public function showCoursByIdModule(Modules $module, ProfileRepository $profileRepository): Response
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
    // Récupérez les cours associés à ce module
    $cours = $module->getCours();

    // Afficher la vue avec les cours associés
    return $this->render('StudentTemplate/coursesPage.html.twig', [
        'module' => $module,
        'cours' => $cours,
        'profile' => $profile
    ]);
}


#[Route('/new', name: 'app_cours_new', methods: ['GET', 'POST'])]
public function new(Request $request, EntityManagerInterface $entityManager, ProfileRepository $profileRepository , ProfileCoachRepository $profileCoachRepository): Response
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
    $cour = new Cours();
    $user = $this->session->get('user');
    $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
    $profileCoach = $profileCoachRepository->findOneBy(['idProfile'=>$profile->getId()]);
    $form = $this->createForm(CoursType::class, $cour);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $videoFile = $form->get('videoPath')->getData();
        if ($videoFile) {
            $destinationPath = 'http://127.0.0.1/files/';
            $originalFilename = $videoFile->getClientOriginalName();
            $newFilePath = $destinationPath . $originalFilename;
            if (file_exists($newFilePath)) {
            } else {
                try {
                    $videoFile->move($destinationPath, $originalFilename);
                } catch (FileException $e) {
                }
            }
        }

        $pdfFile = $form->get('fichierPath')->getData();

        if ($pdfFile) {
            $destinationPath = 'http://127.0.0.1/files';
            $xamppPath = "C:/xampp/htdocs/files";
        
            // Générer un nom unique pour le fichier PDF
            $originalFilename = pathinfo($pdfFile->getClientOriginalName(), PATHINFO_FILENAME);
            $newFilename = basename($originalFilename);
        
            // Déplacer le fichier PDF vers le répertoire de destination
            try {
                $pdfFile->move($xamppPath, $newFilename);
            } catch (FileException $e) {
                // Gérer l'erreur si le déplacement du fichier échoue
            }
            
            // Mettre à jour le chemin du fichier PDF dans l'entité Cours
            $cour->setFichierPath($destinationPath.'/'.$newFilename.'.pdf');
            $cour->setVideoPath($newFilePath);
            $cour->setIDModule($profileCoach->getIdModule());
        
            // Extraire le contenu textuel du PDF
            // Initialize and load PDF Parser library
            $parser = new \Smalot\PdfParser\Parser();
        
            // Chemin complet du fichier PDF
            $file = $xamppPath . '/' . $newFilename;
        
            // Parse the PDF file using the Parser library
            $pdf = $parser->parseFile($file);
        
            // Extraire le texte du PDF
            $textContent = $pdf->getText();
        
            // Chemin complet du fichier texte
           // $textFilePath = $destinationPath . '/' . $originalFilename . '.txt';
        
            // Écrire le contenu extrait dans un fichier texte
           // file_put_contents($textFilePath, $textContent);
        
        
            //hneee partie speech to text
          // Appel de l'API pour convertir le texte en discours
          $curl = curl_init();
          $api_key ="31a571e9388d600dd873ffc1ae53e781";
          $voice_id="21m00Tcm4TlvDq8ikWAM";
          $model_id = 'eleven_multilingual_v2';
          $url = "https://api.elevenlabs.io/v1/text-to-speech/" . $voice_id;

          $text =substr($textContent, 0, 100);
          
          
          // Set up the request headers
          $headers = [
            "Content-Type: application/json",
            "xi-api-key: $api_key"
          ];
          
          // Set up the request parameters
          $data = json_encode( array(
            'text'           => $text,
            'voiceId'        => $voice_id,
            'model_id'       => $model_id,
            'voice_settings' => array(
                'stability'         => 0,
                'similarity_boost'  => 0,
                'style'             => 0,
                'use_speaker_boost' => 1
            )
          ) );

          // Set up cURL options
          curl_setopt_array( $curl, [
            CURLOPT_URL            => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING       => "",
            CURLOPT_MAXREDIRS      => 10,
            CURLOPT_TIMEOUT        => 30,
            CURLOPT_HTTP_VERSION   => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST  => "POST",
            CURLOPT_POSTFIELDS     => $data,
            CURLOPT_HTTPHEADER     => $headers,
          ] );

          // Execute cURL request
          $response = curl_exec( $curl );
          $err      = curl_error( $curl );

          // Close cURL session
          curl_close( $curl );

          // Check for cURL errors
          if ( $err ) {
            echo "cURL Error #:" . $err;
          } else {
            // Save the audio stream as an MP3 file on the server
            $file_path = 'http://127.0.0.1/files/' . $newFilename . '.mp3';
            $res = file_put_contents( $xamppPath.'/'. $newFilename .'.mp3', $response );
            $cour->setAudioPath($file_path);
            // Print a success message
            echo "MP3 file successfully saved.";
          }
        
          // Mettre à jour le champ tempsT du module associé
          $module = $cour->getIDModule();
          $module->setTempsT($module->getTempsT() + $cour->getTempsC());

          // Enregistrer l'entité dans la base de données
          $entityManager->persist($cour);
          
          $entityManager->flush();

          // Rediriger vers la page d'index des cours après la création réussie
          return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
        }
    }

    return $this->renderForm('cours/new.html.twig', [
        'cour' => $cour,
        'form' => $form,
        'profile' => $profile

    ]);
}
    #[Route('/cours/{id}/suivi/add', name: 'app_suivi_add', methods: ['POST'])]
public function addSuivi(Request $request, Modules $module, UserRepository $userRepository): Response
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
    // Récupérer l'utilisateur avec l'ID 1
    $user = $this->session->get('user');
    $userDataBase=$userRepository->findOneBy(['id'=>$user->getId()]);
    // Vérifier s'il existe déjà un suivi pour cet utilisateur et ce module
    $suivi = $this->getDoctrine()->getRepository(Suivi::class)->findOneBy(['idUser' => $userDataBase->getId(), 'idModule' => $module]);

    if ($suivi) {
        // Mettre à jour la date de consultation avec la date actuelle
        $suivi->setDateConsultation(new \DateTime());

        // Enregistrer les modifications dans la base de données
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
    } else {
        // S'il n'y a pas de suivi existant, créer un nouveau suivi
        $suivi = new Suivi();
        $suivi->setDateConsultation(new \DateTime());
        $suivi->setIdUser($userDataBase);
        $suivi->setIdModule($module);

        // Enregistrer le nouveau suivi dans la base de données
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($suivi);
        $entityManager->flush();
    }

    // Rediriger l'utilisateur vers la page des cours avec un message de succès
    return $this->redirectToRoute('app_cours_show_ByIdModule', ['id' => $module->getId()]);
}


    #[Route('show/{id}', name: 'app_cours_show', methods: ['GET'])]
    public function show(Cours $cour, ProfileRepository $profileRepository): Response
    {     if(isset($_COOKIE['ConnectedUser']))
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
        return $this->render('cours/show.html.twig', [
            'cour' => $cour,
            'profile' => $profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_cours_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Cours $cour, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        // Créer un clone du cours avant modification
        $originalCour = clone $cour;
        $user = $this->session->get('user');
       $profile = $profileRepository->findOneBy(['idUser' => $user->getId()]);
        // Créer le formulaire avec le cours existant
        $form = $this->createForm(CoursType::class, $cour);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Récupérer le fichier PDF téléchargé
            $pdfFile = $form->get('fichierPath')->getData();
    
            // Vérifier si un nouveau fichier PDF a été téléchargé
            if ($pdfFile) {
                // Définir le chemin de destination
                $destinationPath = 'C:/xampp/htdocs/files';
    
                // Générer un nom unique pour le fichier
                $originalFilename = pathinfo($pdfFile->getClientOriginalName(), PATHINFO_FILENAME);
                $newFilename = basename($originalFilename);
    
                // Déplacer le fichier vers le répertoire de destination
                try {
                    $pdfFile->move($destinationPath, $newFilename);
                } catch (FileException $e) {
                    // Gérer l'erreur si le déplacement du fichier échoue
                }
    
                // Mettre à jour le chemin du fichier PDF dans l'entité Cours
                $cour->setFichierPath($newFilename);
            }
    
            // Calculer la différence de tempsC
            $tempsCDifference = $cour->getTempsC() - $originalCour->getTempsC();
    
            // Mettre à jour le champ tempsT du module associé
            $module = $cour->getIDModule();
            $module->setTempsT($module->getTempsT() + $tempsCDifference);
    
            $entityManager->flush();
    
            return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('cours/edit.html.twig', [
            'cour' => $cour,
            'form' => $form,
            'profile' => $profile
        ]);
    }
    

    #[Route('delete/{id}', name: 'app_cours_delete', methods: ['POST'])]
    public function delete(Request $request, Cours $cour, EntityManagerInterface $entityManager): Response
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
        if ($this->isCsrfTokenValid('delete'.$cour->getId(), $request->request->get('_token'))) {
               // Mettre à jour le champ tempsT du module associé
            $module = $cour->getIDModule();
            $module->setTempsT($module->getTempsT() - $cour->getTempsC());
            $entityManager->remove($cour);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_cours_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('pdf/{filename}', name: 'app_pdf_download')]
    public function downloadPdf(string $filename): Response
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
    $path = 'C:/xampp/htdocs/files/' . $filename; // Chemin absolu vers le fichier PDF
    $response = new Response(file_get_contents($path));

    // Définir le type de contenu du fichier PDF
    $response->headers->set('Content-Type', 'application/pdf');

    return $response;
}
 
 
 
    #[Route('/search', name: 'app_cours_search', methods: ['GET'])]
    public function search(Request $request, CoursRepository $coursRepository): JsonResponse
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
        $input = $request->query->get('input');
        $cours = $coursRepository->findBySearchInput($input);
        
        // Créer un tableau des données des cours pour renvoyer en réponse JSON
        $coursData = [];
        foreach ($cours as $cour) {
            $coursData[] = [
                'nom' => $cour->getNom(),
                'description' => $cour->getDescription(),
                'fichierPath' => $cour->getFichierPath(),
                'tempsC' => $cour->getTempsC(),
                'audioPath' => $cour->getAudioPath(),
                'videoPath' => $cour->getVideoPath(),
                // Ajoutez d'autres champs si nécessaire
            ];
        }
    
        // Retourner les données sous forme de réponse JSON
        return new JsonResponse($coursData);
    }
    
    #[Route('/sort/{sortBy}/{sortDirection}', name: 'app_cours_sort', methods: ['GET'])]
    public function sort(Request $request, CoursRepository $coursRepository, string $sortBy, string $sortDirection): JsonResponse
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
        // Vérifier si le champ de tri est valide
        $validSortFields = ['nom', 'tempsC'];
        if (!in_array($sortBy, $validSortFields)) {
            return new JsonResponse(['error' => 'Invalid sort field'], Response::HTTP_BAD_REQUEST);
        }
    
        // Vérifier si la direction de tri est valide
        if (!in_array($sortDirection, ['asc', 'desc'])) {
            return new JsonResponse(['error' => 'Invalid sort direction'], Response::HTTP_BAD_REQUEST);
        }
    
        // Récupérer les cours triés à partir du repository
        $cours = $coursRepository->findBy([], [$sortBy => $sortDirection]);
    
        // Formatage des données des cours comme un tableau associatif
        $coursData = [];
        foreach ($cours as $cour) {
            $coursData1[] = [
                'nom' => $cour->getNom(),
                'description' => $cour->getDescription(),
                'fichierPath' => $cour->getFichierPath(),
                'tempsC' => $cour->getTempsC(),
                'audioPath' => $cour->getAudioPath(),
                'videoPath' => $cour->getVideoPath(),
                // Ajoutez d'autres champs si nécessaire
            ];
        }
    
        // Retourner les données triées des cours en tant que réponse JSON
        return new JsonResponse($coursData1);
    }
    #[Route('/front/{id}', name: 'app_cours_front', methods: ['GET'])]
    public function showCours(Cours $cour): Response
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
        return $this->render('StudentTemplate/course-detail.html.twig', [
            'cour' => $cour,
        ]);
    }
}
