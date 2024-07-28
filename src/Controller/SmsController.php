<?php

namespace App\Controller;

use App\Service\SmsGenerator;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Repository\SuiviRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Modules;

class SmsController extends AbstractController
{
    #[Route('/envoyer-sms-suivis', name: 'envoyer_sms_suivis')]
    public function envoyerSmsSuivis(SuiviRepository $suiviRepository, SmsGenerator $smsGenerator): Response
    {
        $suivis = $suiviRepository->findSuivisDepassantUneSemaine();

        foreach ($suivis as $suivi) {
            $module = $this->getDoctrine()->getRepository(Modules::class)->find($suivi->getIdModule());

            $smsGenerator->sendSms("+21629319685", $module->getNom());
        }

        return new Response('Les SMS ont été envoyés avec succès aux utilisateurs concernés.');
    }
}
