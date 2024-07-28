<?php

namespace App\Entity;

use App\Repository\ProfileAdminRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ProfileAdminRepository::class)]
class ProfileAdmin
{
    

    #[ORM\Id]
    #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(name:"idProfile",referencedColumnName:"idProfile",nullable: false)]
    private ?Profile $idProfile = null;
    
    #[Assert\NotBlank]
    #[Assert\Length(min:4)]
    #[ORM\Column(name:"responsabilty",length: 255)]
    private ?string $responsability = null;
   
    #[Assert\NotBlank]
    #[Assert\Length(min:4)]
    #[ORM\Column(name:"title",length: 255)]
    private ?string $title = null;


    public function getIdProfile(): ?Profile
    {
        return $this->idProfile;
    }

    public function setIdProfile(?Profile $idProfile): static
    {
        $this->idProfile = $idProfile;

        return $this;
    }

    public function getResponsability(): ?string
    {
        return $this->responsability;
    }

    public function setResponsability(string $responsability): static
    {
        $this->responsability = $responsability;

        return $this;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }
}
