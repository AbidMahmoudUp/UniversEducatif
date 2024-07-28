<?php

namespace App\Entity;

use App\Repository\AchatRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: AchatRepository::class)]
class Achat
{
    

    #[ORM\Id]
    #[ORM\JoinColumn(nullable: false, name: "id_produit",referencedColumnName:"id_produit")]
    #[ORM\ManyToOne(inversedBy: 'achats')]
    private ?Produit $id_produit = null;

    #[ORM\Column]
    private ?int $qte = null;

    #[ORM\Column]
    private ?int $mont_tot = null;

    #[ORM\Id]
    #[ORM\JoinColumn(nullable: false, name: "idc",referencedColumnName:"idc")]
    #[ORM\ManyToOne(inversedBy: 'achats')]
    private ?Commande $idc = null;


    public function getIdProduit(): ?Produit
    {
        return $this->id_produit;
    }

    public function setIdProduit(?Produit $id_produit): static
    {
        $this->id_produit = $id_produit;

        return $this;
    }

    public function getQte(): ?int
    {
        return $this->qte;
    }

    public function setQte(int $qte): static
    {
        $this->qte = $qte;

        return $this;
    }

    public function getMontTot(): ?int
    {
        return $this->mont_tot;
    }

    public function setMontTot(int $mont_tot): static
    {
        $this->mont_tot = $mont_tot;

        return $this;
    }

    public function getIdc(): ?Commande
    {
        return $this->idc;
    }

    public function setIdc(?Commande $idc): static
    {
        $this->idc = $idc;

        return $this;
    }
}
