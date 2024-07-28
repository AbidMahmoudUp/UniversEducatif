<?php

namespace App\Entity;

use App\Repository\ProfileCoachRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ProfileCoachRepository::class)]
class ProfileCoach
{
    
    #[ORM\Id]
    #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(name:"idProfile",referencedColumnName:"idProfile",nullable: false)]
    private ?Profile $idProfile = null;

    #[ORM\ManyToOne(inversedBy: 'profileCoaches')]
    #[ORM\JoinColumn(name:"idModule",referencedColumnName:"idModule",nullable: false)]
    private ?Modules $idModule = null;


    public function getIdProfile(): ?Profile
    {
        return $this->idProfile;
    }

    public function setIdProfile(?Profile $idProfile): static
    {
        $this->idProfile = $idProfile;

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
}
