<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Google\Client as GoogleClient;
use Google\Service\Calendar;
use Google\Service\Calendar\Event;
use Symfony\Component\HttpFoundation\Response;
use DateTime;
use DateTimeZone;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class GoogleCalendarController extends AbstractController
{
    #[Route('/google/calendar', name: 'app_google_calendar')]
    public function index(Request $request, UrlGeneratorInterface $urlGenerator): RedirectResponse
    {
        $test="2024-04-21";
        // Get the date and module from the request parameters
        $date = $request->query->get('date');
        $module = $request->query->get('module');
    

    
    
        // Get the OAuth2 client
        $client = new GoogleClient();
        $client->setAuthConfig('../config/client_secret_1039449456756-4ja5goeaa85p440tvs5qsg2798a9n05k.apps.googleusercontent.com.json');
        $client->addScope(Calendar::CALENDAR);

        // Set up a redirect URI for Google OAuth2
        $redirectUri = $urlGenerator->generate('app_google_calendar', [], UrlGeneratorInterface::ABSOLUTE_URL);

        // Set redirect URI
        $client->setRedirectUri($redirectUri);

        // If authentication is needed, redirect to Google authentication page
        if (!$request->get('code')) {
            $authUrl = $client->createAuthUrl();
            $session = $request->getSession();
             $session->set('stored_date', $date);
             $session->set('stored_module', $module);
                return new RedirectResponse($authUrl);
        } else {
            $session = $request->getSession();
$datee = $session->get('stored_date');
$modulee = $session->get('stored_module');
            // Authenticate with the received authorization code
            $client->authenticate($request->get('code'));
            $accessToken = $client->getAccessToken();

            if (!$accessToken) {
                // Handle authentication error
                throw new \Exception('Unable to authenticate with Google.');
            }

            // You can now use $client to make requests to the Google Calendar API
            $service = new Calendar($client);

            // Define the time zone for the event
            $timeZone = new DateTimeZone('Europe/Paris');

            $startDateTime = new DateTime($datee . "T09:00:00", $timeZone);
            $endDateTime = new DateTime($datee. "T10:00:00", $timeZone);


            // Format the start and end times according to the Google Calendar API requirements
            $start = [
                'dateTime' => $startDateTime->format(DateTime::RFC3339),
                'timeZone' => $timeZone->getName(),
            ];

            $end = [
                'dateTime' => $endDateTime->format(DateTime::RFC3339),
                'timeZone' => $timeZone->getName(),
            ];

            // Create the event
            $event = new Event([
                'summary' => $modulee,
                'description' => 'Exam: ' . $modulee,
                'start' => $start,
                'end' => $end,
            ]);

            // Insert the event into the calendar
            $calendarId = 'primary'; // Use 'primary' for the primary calendar
            $event = $service->events->insert($calendarId, $event);
            
            // Optionally, you can redirect the user to a success page
            return $this->redirectToRoute('app_exam_index');
        }
    }
}