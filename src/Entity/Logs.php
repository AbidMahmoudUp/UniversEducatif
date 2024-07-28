<?php

namespace App\Entity;

use App\Repository\LogsRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LogsRepository::class)]
class Logs
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idLog")]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'logs')]
    #[ORM\JoinColumn(name: "idUser",referencedColumnName:"IdUser",nullable: false)]
    private ?User $idUser = null;

    #[ORM\Column(name:"description1",length: 255)]
    private ?string $description1 = null;

    #[ORM\Column(name:"date",type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(name:"action1",length: 255)]
    private ?string $action1 = null;

    #[ORM\Column(name:"action2",length: 255)]
    private ?string $action2 = null;

    #[ORM\Column(name:"description2",length: 255)]
    private ?string $description2 = null;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getDescription1(): ?string
    {
        return $this->description1;
    }

    public function setDescription1(string $description1): static
    {
        $this->description1 = $description1;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): static
    {
        $this->date = $date;

        return $this;
    }

    public function getAction1(): ?string
    {
        return $this->action1;
    }

    public function setAction1(string $action1): static
    {
        $this->action1 = $action1;

        return $this;
    }

    public function getAction2(): ?string
    {
        return $this->action2;
    }

    public function setAction2(string $action2): static
    {
        $this->action2 = $action2;

        return $this;
    }

    public function getDescription2(): ?string
    {
        return $this->description2;
    }

    public function setDescription2(string $description2): static
    {
        $this->description2 = $description2;

        return $this;
    }
}
