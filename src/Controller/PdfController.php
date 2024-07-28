<?php

namespace App\Controller;

use App\Repository\QuestionsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

class PdfController extends AbstractController
{
    private $dompdf;

    public function __construct(Dompdf $dompdf)
    {
        $this->dompdf = $dompdf;
    }

    /**
     * @Route("/generate-pdf", name="generate_pdf", methods={"POST"})
     */
    public function generatePdf(QuestionsRepository $questionsRepository,Request $request): Response
    {
        try {
            $requestData = json_decode($request->getContent(), true);
            dump($requestData); 

            $score = $requestData['score'];
            $questions = $questionsRepository->findAll();
        
            
            // Render the template to HTML
            $html = $this->renderView('exams/pdf.html.twig', [
                'score' => $score,
                'questions' => $questions,
            ]);

            $options = new Options();
            $options->set('isHtml5ParserEnabled', true);
            $this->dompdf->setOptions($options);

            // Load HTML content
            $this->dompdf->loadHtml($html);

            // Set paper size and orientation
            $this->dompdf->setPaper('A4', 'portrait');

            // Render the HTML as PDF
            $this->dompdf->render();

            // Output the generated PDF
            $output = $this->dompdf->output();

            // Return the PDF as a response with a filename
            $response = new Response($output);
            $response->headers->set('Content-Type', 'application/pdf');
            $response->headers->set('Content-Disposition', 'attachment; filename="quiz_answers.pdf"');
            
            return $response;
        } catch (\Exception $e) {
            // Return a generic error response
            return new Response('An error occurred while generating the PDF', Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
