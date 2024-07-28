<?php

namespace App\Entity;

use App\Repository\NotesRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: NotesRepository::class)]
class Notes
{


    #[ORM\JoinColumn(nullable: false, name: "module",referencedColumnName:"idModule")]
    #[ORM\ManyToOne(inversedBy: 'notes')]
    private ?Modules $module = null;

    #[ORM\Column]
    private ?int $note = null;

    #[ORM\Id]
    #[ORM\JoinColumn(nullable: false, name: "idexam",referencedColumnName:"idExam")]
    #[ORM\ManyToOne(inversedBy: 'notes')]
    private ?Exams $idexam = null;


    #[ORM\Id]
    #[ORM\JoinColumn(nullable: false, name: "idUser",referencedColumnName:"IdUser")]
    #[ORM\ManyToOne(inversedBy: 'notes')]
    private ?User $idUser = null;

    public function getModule()
    {
        return $this->module;
    }

    public function setModule(Modules $module): static
    {
        $this->module = $module;

        return $this;
    }

    public function getNote(): ?int
    {
        return $this->note;
    }

    public function setNote(int $note): static
    {
        $this->note = $note;

        return $this;
    }

    public function getIdexam(): ?Exams
    {
        return $this->idexam;
    }

    public function setIdexam(?Exams $idexam): static
    {
        $this->idexam = $idexam;

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
