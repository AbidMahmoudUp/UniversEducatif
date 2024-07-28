<?php

namespace App\Controller;

use App\Entity\Modules;
use App\Entity\Profile;
use App\Form\ModulesType;
use App\Repository\ModuleRepository;
use App\Repository\ModulesRepository;
use App\Repository\ProfileRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

define('IMG_DIR', 'C:/xampp/htdocs/img/');

#[Route('/modules')]

class ModulesController extends AbstractController
{

    private $session;


    private $verificationCode;

    

    public function __construct(SessionInterface $session , )
    {
        $this->session = $session;
        
    }


    
    #[Route('/universEducatif/Modules', name: 'app_modules_index', methods: ['GET'])]
    public function indexFront(ModulesRepository $modulesRepository): Response
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
        return $this->render('StudentTemplate/modulesPage.html.twig', [
            'modules' => $modulesRepository->findAll(),
        ]);
    }
    #[Route('/new', name: 'app_modules_new', methods: ['GET', 'POST'])]
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
        $module = new Modules();
        $form = $this->createForm(ModulesType::class, $module);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Récupérer le fichier téléchargé
            $imgFile = $form->get('imgPath')->getData();
    
            // Vérifier si un fichier a été téléchargé
            if ($imgFile) {
                // Définir le chemin de destination
                $destinationPath =  'C:\xampp\htdocs\img';
                $destination = 'http://127.0.0.1/img';

    
                // Récupérer le nom de fichier original
                $originalFilename = pathinfo($imgFile->getClientOriginalName(), PATHINFO_FILENAME);
    
                // Générer un nom unique pour le fichier
                $newFilename = $originalFilename.'.'.$imgFile->guessExtension();
                
                // Si le fichier existe déjà, écraser l'image existante
                if (file_exists($destinationPath . '/' . $newFilename)) {
                    unlink($destinationPath . '/' . $newFilename);
                }
    
                // Créer une copie du fichier dans le répertoire de destination
                $success = copy($imgFile->getPathname(), $destinationPath . '/' . $newFilename);
    
                if ($success) {
                    // Mettre à jour le chemin de l'image dans l'entité Modules
                    $module->setImgPath($destination.'/'.$newFilename);
                } else {
                    // Gérer l'erreur si la copie du fichier a échoué
                }
            }
            
            // Enregistrer l'entité dans la base de données
            $entityManager->persist($module);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_Modules', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('modules/new.html.twig', [
            'module' => $module,
            'form' => $form,
            'profile'=>$profile,
        ]);
    }
    
    

    #[Route('/show/{id}', name: 'app_modules_show', methods: ['GET'])]
    public function show(Modules $module,ProfileRepository $profileRepository): Response
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
        return $this->render('modules/show.html.twig', [
            'module' => $module,
            'profile'=>$profile
        ]);
    }

    #[Route('/edit/{id}', name: 'app_modules_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Modules $module, EntityManagerInterface $entityManager,ProfileRepository $profileRepository): Response
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
        $form = $this->createForm(ModulesType::class, $module);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Récupérer le fichier imgPath soumis dans le formulaire
            /** @var UploadedFile|null $newImgPath */
            $newImgPath = $form->get('imgPath')->getData();
    
            if ($newImgPath) {
                // Supprimer l'ancien fichier s'il existe
                $oldImgPath = $module->getImgPath();
               
    
                
                $module->setImgPath($oldImgPath);
            }
    
            $entityManager->flush();
    
            return $this->redirectToRoute('app_Modules', [], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('modules/edit.html.twig', [
            'module' => $module,
            'form' => $form,
            'profile'=>$profile
        ]);
    }

    #[Route('/delete/{id}', name: 'app_modules_delete', methods: ['POST'])]
    public function delete(Request $request, Modules $module, EntityManagerInterface $entityManager): Response
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
       
        if ($this->isCsrfTokenValid('delete'.$module->getId(), $request->request->get('_token'))) {
            $entityManager->remove($module);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_Modules', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/', name:'app_Modules',methods:['GET','POST'])]
    public function switchToModules(Request $request,ProfileRepository $profileRepository,ModulesRepository $modulesRepository):Response
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
                
        return $this->render('/AdminTemplate/ModulesManagement.html.twig',[
            'user' => $user,
            'profile' => $profile,
            'modules' => $modulesRepository->findAll(),
            
        ]);
    }
    #[Route('/cours', name:'app_Cours_Modules',methods:['GET','POST'])]
    public function switchToCours(Request $request):Response
    {
                
        return $this->render('/cours/index.html.twig');
    }
    #[Route('/search', name: 'app_modules_search', methods: ['GET'])]
    public function search(Request $request, ModulesRepository $modulesRepository): JsonResponse
    {
        
        $input = $request->query->get('input');
        $modules = $modulesRepository->findBySearchInput($input);
        
        // Créer un tableau des données des modules pour renvoyer en réponse JSON
        $moduleData = [];
        foreach ($modules as $module) {
            $moduleData[] = [
                'nom' => $module->getNom(),
                'description' => $module->getDescription(),
                'imgPath' => $module->getImgPath(),
                'tempsT' => $module->getTempsT(),
                // Ajoutez d'autres champs si nécessaire
            ];
        }
    
        // Retourner les données sous forme de réponse JSON
        return new JsonResponse($moduleData);
    }
    #[Route('/sort/{sortBy}/{sortDirection}', name: 'app_modules_sort', methods: ['GET'])]
    public function sort(Request $request, ModulesRepository $modulesRepository, string $sortBy, string $sortDirection): JsonResponse
    {
        // Vérifier si le champ de tri est valide
        $validSortFields = ['nom', 'tempsT'];
        if (!in_array($sortBy, $validSortFields)) {
            return new JsonResponse(['error' => 'Invalid sort field'], Response::HTTP_BAD_REQUEST);
        }
    
        // Vérifier si la direction de tri est valide
        if (!in_array($sortDirection, ['asc', 'desc'])) {
            return new JsonResponse(['error' => 'Invalid sort direction'], Response::HTTP_BAD_REQUEST);
        }
    
        // Récupérer les modules triés à partir du repository
        $modules = $modulesRepository->findBy([], [$sortBy => $sortDirection]);
    
        // Formatage des données des modules comme un tableau associatif
        $moduleData = [];
        foreach ($modules as $module) {
            $moduleData1[] = [
                'nom' => $module->getNom(),
                'description' => $module->getDescription(),
                'imgPath' => $module->getImgPath(),
                'tempsT' => $module->getTempsT(),
                // Ajoutez d'autres champs si nécessaire
            ];
        }
    
        // Retourner les données triées des modules en tant que réponse JSON
        return new JsonResponse($moduleData1);
    }
    
    
}
