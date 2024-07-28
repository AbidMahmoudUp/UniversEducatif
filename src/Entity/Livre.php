<?php

namespace App\Entity;

use App\Repository\LivreRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LivreRepository::class)]
class Livre
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idLivre")]
    private ?int $id = null;

    #[ORM\Column(name:"type",length: 255)]
    private ?string $type = null;

    #[ORM\Column(name:"titre",length: 255)]
    private ?string $titre = null;

    #[ORM\Column(name:"auteur",length: 255)]
    private ?string $auteur = null;

    #[ORM\Column(name:"langue",length: 255)]
    private ?string $langue = null;

    #[ORM\Column(name:"image",length: 255)]
    private ?string $image = null;

    #[ORM\Column(name:"lienPDF",length: 255)]
    private ?string $pathPDF = null;

    #[ORM\ManyToOne(inversedBy: 'livres')]
    #[ORM\JoinColumn(name: "idBib",referencedColumnName:"idBib",nullable: false)]
    private ?Bibliotheque $bibliotheque = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): static
    {
        $this->titre = $titre;

        return $this;
    }

    public function getAuteur(): ?string
    {
        return $this->auteur;
    }

    public function setAuteur(string $auteur): static
    {
        $this->auteur = $auteur;

        return $this;
    }

    public function getLangue(): ?string
    {
        return $this->langue;
    }

    public function setLangue(string $langue): static
    {
        $this->langue = $langue;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): static
    {
        $this->image = $image;

        return $this;
    }

    public function getPathPDF(): ?string
    {
        return $this->pathPDF;
    }

    public function setPathPDF(string $pathPDF): static
    {
        $this->pathPDF = $pathPDF;

        return $this;
    }

    public function getBibliotheque(): ?Bibliotheque
    {
        return $this->bibliotheque;
    }

    public function setBibliotheque(?Bibliotheque $idBib): static
    {
        $this->bibliotheque = $idBib;

        return $this;
    }
}
