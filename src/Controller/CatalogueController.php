<?php

namespace App\Controller;

use App\Repository\ProduitRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Snappy\Pdf;

class CatalogueController extends AbstractController
{
    #[Route('/catalogue', name: 'app_catalogue')]
    public function index(ProduitRepository $produitRepository, Request $request): Response
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
        $query = $request->query->get('q');
        if ($query) {
            $produits = $produitRepository->findBySearchQuery($query);
        } else {
            $produits = $produitRepository->findAll();
        }

        return $this->render('catalogue/index.html.twig', [
            'produits' => $produits,
        ]);
    }
    private $pdf;

    public function __construct(Pdf $pdf)
    {
        $this->pdf = $pdf;
    }
    
 
    #[Route('/pdf', name: 'app_panier_pdf', methods: ['GET', 'POST'])]
    public function generatePDF(Request $request): Response
    {     if(isset($_COOKIE['ConnectedUser']))
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
        $session = $request->getSession();
        $cart = $session->get('cart', []);

        $total = 0;

    // Calculate the total cost of the cart items
    foreach ($cart as $item) {
        $total += $item['qte'] * $item['prixInit'];
    }

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($pdfOptions);

        $html = $this->renderView('catalogue/pdf.html.twig', [
            'cart' => $cart,
            'total' => $total
            
        ]);

        $dompdf->loadHtml($html);

        $dompdf->setPaper('A4', 'portrait');

        $dompdf->render();

        $pdfContent = $dompdf->output();


        $response = new Response($pdfContent);

        $response->headers->set('Content-Type', 'application/pdf');

        $response->headers->set('Content-Disposition', 'attachment; filename="panier.pdf"');

        return $response;
    }
    #[Route('/catalogue/search', name: 'catalogue_search', methods: ['GET'])]
    public function search(Request $request, ProduitRepository $produitRepository): Response
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
        $query = $request->query->get('q');
        $produits = $produitRepository->findBySearchQuery($query);
        return $this->render('catalogue/index.html.twig', [
            'produits' => $produits,
        ]);
    }

    #[Route('/catalogue/sort', name: 'catalogue_sort', methods: ['GET'])]
    public function sort(Request $request, ProduitRepository $produitRepository): Response
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
        $sortOrder = $request->query->get('sort', 'asc');
        $produits = $produitRepository->findBy([], ['prix_init' => $sortOrder]);
        return $this->render('catalogue/index.html.twig', [
            'produits' => $produits,
        ]);
    }


    #[Route('/cart', name: 'cart_path', methods: ['GET'])]
    public function cart( Request $request): Response
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
        $session = $request->getSession();
        $cart = $session->get('cart', []);

        $total = 0;

    // Calculate the total cost of the cart items
    foreach ($cart as $item) {
        $total += $item['qte'] * $item['prixInit'];
    }
        // Dummy response for the cart, update with your actual logic
        return $this->render('catalogue/panier.html.twig', [
            // Data for the cart can be added here
            'cart' => $cart,
            'total' => $total 
        ]);
       
    }
    

    #[Route('/add-to-cart/{produitId}/{quantity}/{prixInit}', name: 'addToCart', methods: ['POST'])]
    public function addToCart(int $produitId, int $quantity,float $prixInit ,Request $request)
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
        $session = $request->getSession();
        $cart = $session->get('cart', []);
      
        if (isset($cart[$produitId])) {
            $cart[$produitId]['qte'] += $quantity;
        } else {
            $cart[$produitId] = ['qte' => $quantity, 'prixInit' => $prixInit];
        }
        

        $session->set('cart', $cart);
        
        return $this->redirectToRoute('app_catalogue');  // Redirect to the catalogue page
    }
#[Route('/increment_quantity', name: 'increment_quantity', methods: ['POST'])]
public function incrementQuantity(Request $request)
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
    $productId = $request->request->get('produit_id');

    $session = $request->getSession();
    $cart = $session->get('cart', []);

    if (isset($cart[$productId])) {
        $cart[$productId]['qte'] += 1;
    }

    $session->set('cart', $cart);
    return $this->redirectToRoute('/catalogue');  // Redirect to the catalogue page
}
#[Route('decrement_quantity', name: 'decrement_quantity', methods: ['POST'])]
public function decrementQuantity(Request $request , $produitId)
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
    $productId = $request->request->get('produit_id');

    $session = $request->getSession();
    $cart = $session->get('cart', []);

    if (isset($cart[$produitId]) && $cart[$produitId]['qte'] > 1) {
        $cart[$produitId]['qte'] -= 1;
    } else {
        unset($cart[$produitId]);  // Remove the product from cart if quantity is less than 1
    }

    $session->set('cart', $cart);
   return $this->redirectToRoute('catalogue', ['param1' => 'value1']);
 // Redirect to the catalogue page
}

#[Route('/remove-from-cart/{produitId}', name: 'remove_from_cart', methods: ['POST'])]
    public function removeFromCart(int $produitId, Request $request): Response
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
        $session = $request->getSession();
        $cart = $session->get('cart', []);

        // Check if the item exists in the cart and remove it
        if (isset($cart[$produitId])) {
            unset($cart[$produitId]);
            $session->set('cart', $cart);  // Update the cart in the session
            $this->addFlash('success', 'Produit supprimé avec succès.');
        } else {
            $this->addFlash('error', 'Produit non trouvé dans le panier.');
        }

        return $this->redirectToRoute('cart_path');  // Redirect to the cart view
    }

#[Route('/increment_quantityp/{produitId}', name: 'increment_quantityp', methods: ['POST'])]
  public function incrementQuantityp (int $produitId,Request $request)
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
    $productId = $request->request->get('produit_id');

    $session = $request->getSession();
    $cart = $session->get('cart', []);

    if (isset($cart[$productId])) {
        $cart[$productId]['qte'] += 1;
    }

    $session->set('cart', $cart);
    return $this->redirectToRoute('cart_path'); 
}
#[Route('/decrement-quantityp', name: 'decrement_quantityp', methods: ['POST'])]
public function decrementQuantityp(Request $request)
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
    $produitId = $request->request->get('produit_id');

    $session = $request->getSession();
    $cart = $session->get('cart', []);

    if (isset($cart[$produitId]) && $cart[$produitId]['qte'] > 1) {
        $cart[$produitId]['qte'] -= 1;
    } else {
        unset($cart[$produitId]);  // Remove the product from cart if quantity is less than or equal to 1
    }

    $session->set('cart', $cart);
    return $this->redirectToRoute('cart_path');
}



}





