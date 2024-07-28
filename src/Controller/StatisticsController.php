<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Bibliotheque;
use App\Repository\BibliothequeRepository;

class StatisticsController extends AbstractController
{
    #[Route('/bibliotheque/statistics', name: 'bibliotheque_statistics')]
    public function generateStatistics(BibliothequeRepository $bibliothequeRepository): Response
    {
        // Récupérer les statistiques des centres par ville
        $statistics = $bibliothequeRepository->getStatisticsNom();

        // Formater les données pour Chart.js
        $labels = [];
        $data = [];
        foreach ($statistics as $statistic) {
            $labels[] = $statistic['nom'];
            $data[] = $statistic['bibliothequeCount'];
        }

        // Convertir les données en format JSON pour le graphique
        $chartData = [
            'labels' => $labels,
            'data' => $data,
        ];

        // Passer les données à la vue Twig
        return $this->render('bibliotheque/stat.html.twig', [
            'chartData' => json_encode($chartData),
        ]);
    }
}

