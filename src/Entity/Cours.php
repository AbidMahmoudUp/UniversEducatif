<?php

namespace App\Entity;

use App\Repository\CoursRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CoursRepository::class)]
class Cours
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idCours")]
    private ?int $id = null;

    #[ORM\Column(name:"nom",length: 255)]
    private ?string $nom = null;

    #[ORM\Column(name:"description",length: 255)]
    private ?string $description = null;

    #[ORM\ManyToOne(inversedBy: 'cours')]
    #[ORM\JoinColumn(name: "ID_Module",referencedColumnName:"idModule",nullable: false)]
    private ?Modules $ID_Module = null;

    #[ORM\Column(name:"fichierPath",length: 255)]
    private ?string $fichierPath = null;

    #[ORM\Column(name:"tempsC")]
    private ?int $tempsC = null;

    #[ORM\Column(name:"audioPath",length: 255)]
    private ?string $audioPath = null;

    #[ORM\Column(name:"videoPath",length: 255)]
    private ?string $videoPath = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): static
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getIDModule(): ?Modules
    {
        return $this->ID_Module;
    }

    public function setIDModule(?Modules $ID_Module): static
    {
        $this->ID_Module = $ID_Module;

        return $this;
    }

    public function getFichierPath(): ?string
    {
        return $this->fichierPath;
    }

    public function setFichierPath(string $fichierPath): static
    {
        $this->fichierPath = $fichierPath;

        return $this;
    }

    public function getTempsC(): ?int
    {
        return $this->tempsC;
    }

    public function setTempsC(int $tempsC): static
    {
        $this->tempsC = $tempsC;

        return $this;
    }

    public function getAudioPath(): ?string
    {
        return $this->audioPath;
    }

    public function setAudioPath(string $audioPath): static
    {
        $this->audioPath = $audioPath;

        return $this;
    }

    public function getVideoPath(): ?string
    {
        return $this->videoPath;
    }

    public function setVideoPath(string $videoPath): static
    {
        $this->videoPath = $videoPath;

        return $this;
    }
}
