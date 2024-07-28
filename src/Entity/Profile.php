<?php

namespace App\Entity;

use App\Repository\ProfileRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: ProfileRepository::class)]
class Profile
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idProfile")]
    private ?int $id = null;

    #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(name:"idUser",referencedColumnName:"IdUser",nullable: false)]
    private ?User $idUser = null;

    #[ORM\Column(name:"firstName",length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(min:4)]
    private ?string $firstName = null;

    #[ORM\Column(name:"lastName",length: 255)]
    #[Assert\NotBlank]
    #[Assert\Length(min:4)]
    private ?string $lastName = null;

    #[ORM\Column(name:"picture",length: 255)]
    private ?string $picture = null;
    
    #[Assert\NotBlank]
    #[Assert\Length(min: 4)]
    #[ORM\Column(name:"address",length: 255)]
    private ?string $address = null;
    
    
    #[Assert\NotBlank]
    #[Assert\Length(min: 4)]
    #[ORM\Column(name:"location",length: 255)]
    private ?string $location = null;
   

    #[ORM\Column(name:"phoneNumber")]
    #[Assert\NotBlank]
    #[Assert\Length(min: 8, max: 8)]
    private ?int $phoneNumber = null;

    #[ORM\OneToOne(targetEntity: ProfileCoach::class, mappedBy: 'idProfile')]
    private ?ProfileCoach $profileCoach = null;


    public function getProfileCoach()
    {
        return $this->profileCoach;
    }
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

    public function getFirstName(): ?string
    {
        return $this->firstName;
    }

    public function setFirstName(string $firstName): static
    {
        $this->firstName = $firstName;

        return $this;
    }

    public function getLastName(): ?string
    {
        return $this->lastName;
    }

    public function setLastName(string $lastName): static
    {
        $this->lastName = $lastName;

        return $this;
    }

    public function getPicture(): ?string
    {
        return $this->picture;
    }

    public function setPicture(string $picture): static
    {
        $this->picture = $picture;

        return $this;
    }

    public function getAddress(): ?string
    {
        return $this->address;
    }

    public function setAddress(string $address): static
    {
        $this->address = $address;

        return $this;
    }

    public function getLocation(): ?string
    {
        return $this->location;
    }

    public function setLocation(string $location): static
    {
        $this->location = $location;

        return $this;
    }

    public function getPhoneNumber(): ?int
    {
        return $this->phoneNumber;
    }

    public function setPhoneNumber(int $phoneNumber): static
    {
        $this->phoneNumber = $phoneNumber;

        return $this;
    }

    public function __toString()
    {
        return $this->firstName." ".$this->lastName;
    }
}
