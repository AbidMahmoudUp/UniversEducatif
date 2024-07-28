<?php

namespace App\Entity;

use App\Repository\SuiviRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: SuiviRepository::class)]
class Suivi
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idSuivi")]
    private ?int $id = null;

    #[ORM\Column(name:"dateConsultation",type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateConsultation = null;

    #[ORM\ManyToOne(inversedBy: 'suivis',cascade:["persist","remove"])]
    #[ORM\JoinColumn(nullable: false, name: "idModule",referencedColumnName:"idModule")]
    private ?Modules $idModule = null;

    #[ORM\ManyToOne(inversedBy: 'suivis',cascade:["persist","remove"])]
    #[ORM\JoinColumn(nullable: false, name: "idUser",referencedColumnName:"IdUser",)]
    private ?User $idUser = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateConsultation(): ?\DateTimeInterface
    {
        return $this->dateConsultation;
    }

    public function setDateConsultation(\DateTimeInterface $dateConsultation): static
    {
        $this->dateConsultation = $dateConsultation;

        return $this;
    }

    public function getIdModule(): ?Modules
    {
        return $this->idModule;
    }

    public function setIdModule(?Modules $idModule): static
    {
        $this->idModule = $idModule;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): static
    {
        $this->idUser = $idUser;

        return $this;
    }
}
