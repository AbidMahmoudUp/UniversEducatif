<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Modules;
use App\Entity\User;
use App\Entity\Suivi;
use App\Repository\ModulesRepository;
use Symfony\Component\HttpFoundation\JsonResponse;
use App\Repository\SuiviRepository;

class SuiviController extends AbstractController
{
    #[Route('/suivi', name: 'app_suivi')]
    public function index(): Response
    {
        return $this->render('suivi/index.html.twig', [
            'controller_name' => 'SuiviController',
        ]);
    }
    #[Route('/suivi/{userId}/{moduleId}', name: 'app_suivre_module')]
    public function suivreModule(Request $request, int $userId, int $moduleId): Response
    {
        
        $user = $this->getDoctrine()->getRepository(User::class)->find($userId);

        if (!$user) {
            throw $this->createNotFoundException('Utilisateur non trouvé.');
        }

        $module = $this->getDoctrine()->getRepository(Modules::class)->find($moduleId);

        if (!$module) {
            throw $this->createNotFoundException('Module non trouvé.');
        }

        $dateConsultation = new \DateTimeImmutable('now', new \DateTimeZone('UTC'));

        $suivi = new Suivi();
        $suivi->setIdUser($user);
        $suivi->setIdModule($module);
        $suivi->setDateConsultation($dateConsultation);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($suivi);
        $entityManager->flush();

        return new Response('Suivi enregistré avec succès !');
    }
    #[Route('/nombre-suivis-par-module', name: 'nombre_suivis_par_module')]
public function nombreSuivisParModule(SuiviRepository $suiviRepository,ModulesRepository $modulesRepository): JsonResponse
{
    $suivis = $suiviRepository->nombreSuivisParModule();

    return new JsonResponse($suivis);
}
}
