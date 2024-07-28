<?php

namespace App\Entity;

use App\Repository\QuestionsRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: QuestionsRepository::class)]
class Questions
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idquestion")]
    private ?int $id = null;

    #[ORM\Column(name:"question",length: 255)]
    private ?string $question = null;

    #[ORM\Column(name:"choix1",length: 255)]
    private ?string $choix1 = null;

    #[ORM\Column(name:"choix2",length: 255)]
    private ?string $choix2 = null;

    #[ORM\Column(name:"choix3",length: 255)]
    private ?string $choix3 = null;

    #[ORM\Column(name:"choixcorrect",length: 255)]
    private ?string $choixcorrect = null;

    #[ORM\Column(name:"duree")]
    private ?int $duree = null;

    #[ORM\ManyToOne(inversedBy: 'questions')]
    #[ORM\JoinColumn(nullable: false, name: "idExam",referencedColumnName:"idExam")]
    private ?Exams $idExam = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuestion(): ?string
    {
        return $this->question;
    }

    public function setQuestion(string $question): static
    {
        $this->question = $question;

        return $this;
    }

    public function getChoix1(): ?string
    {
        return $this->choix1;
    }

    public function setChoix1(string $choix1): static
    {
        $this->choix1 = $choix1;

        return $this;
    }

    public function getChoix2(): ?string
    {
        return $this->choix2;
    }

    public function setChoix2(string $choix2): static
    {
        $this->choix2 = $choix2;

        return $this;
    }

    public function getChoix3(): ?string
    {
        return $this->choix3;
    }

    public function setChoix3(string $choix3): static
    {
        $this->choix3 = $choix3;

        return $this;
    }

    public function getChoixcorrect(): ?string
    {
        return $this->choixcorrect;
    }

    public function setChoixcorrect(string $choixcorrect): static
    {
        $this->choixcorrect = $choixcorrect;

        return $this;
    }

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): static
    {
        $this->duree = $duree;

        return $this;
    }

    public function getIdExam(): ?Exams
    {
        return $this->idExam;
    }

    public function setIdExam(?Exams $idExam): static
    {
        $this->idExam = $idExam;

        return $this;
    }
}
