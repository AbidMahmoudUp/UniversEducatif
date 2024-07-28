<?php

namespace App\Entity;

use App\Repository\ModulesRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity(repositoryClass: ModulesRepository::class)]
class Modules
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idModule")]
    private ?int $id = null;

    #[ORM\Column(name:"nom",length: 255)]
    private ?string $nom = null;

    #[ORM\Column(name:"description",length: 255)]
    private ?string $description = null;

    #[ORM\Column(name:"imgPath",length: 255)]
    private ?string $imgPath = null;

    #[ORM\Column(name:"tempsT")]
    private ?int $tempsT = null;

    #[ORM\OneToMany(targetEntity: ProfileCoach::class, mappedBy: 'idModule')]
    private Collection $profileCoaches;

    #[ORM\OneToMany(targetEntity: Cours::class, mappedBy: 'ID_Module')]
    private Collection $cours;

    #[ORM\OneToMany(targetEntity: Suivi::class, mappedBy: 'idModule')]
    private Collection $suivis;

    #[ORM\OneToMany(targetEntity: Notes::class, mappedBy: 'module')]
    private Collection $notes;



    public function __construct()
    {
        $this->profileCoaches = new ArrayCollection();
        $this->cours = new ArrayCollection();
        $this->suivis = new ArrayCollection();
        $this->tempsT = 0; 
    }

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

    public function getImgPath(): ?string
    {
        return $this->imgPath;
    }

    public function setImgPath(string $imgPath): static
    {
        $this->imgPath = $imgPath;

        return $this;
    }

    public function getTempsT(): ?int
    {
        return $this->tempsT;
    }

    public function setTempsT(int $tempsT): static
    {
        $this->tempsT = $tempsT;

        return $this;
    }

    /**
     * @return Collection<int, ProfileCoach>
     */
    public function getProfileCoaches(): Collection
    {
        return $this->profileCoaches;
    }

    public function addProfileCoach(ProfileCoach $profileCoach): static
    {
        if (!$this->profileCoaches->contains($profileCoach)) {
            $this->profileCoaches->add($profileCoach);
            $profileCoach->setIdModule($this);
        }

        return $this;
    }

    public function removeProfileCoach(ProfileCoach $profileCoach): static
    {
        if ($this->profileCoaches->removeElement($profileCoach)) {
            // set the owning side to null (unless already changed)
            if ($profileCoach->getIdModule() === $this) {
                $profileCoach->setIdModule(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Cours>
     */
    public function getCours(): Collection
    {
        return $this->cours;
    }

    public function addCour(Cours $cour): static
    {
        if (!$this->cours->contains($cour)) {
            $this->cours->add($cour);
            $cour->setIDModule($this);
        }

        return $this;
    }

    public function removeCour(Cours $cour): static
    {
        if ($this->cours->removeElement($cour)) {
            // set the owning side to null (unless already changed)
            if ($cour->getIDModule() === $this) {
                $cour->setIDModule(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Suivi>
     */
    public function getSuivis(): Collection
    {
        return $this->suivis;
    }

    public function addSuivi(Suivi $suivi): static
    {
        if (!$this->suivis->contains($suivi)) {
            $this->suivis->add($suivi);
            $suivi->setIdModule($this);
        }

        return $this;
    }

    public function removeSuivi(Suivi $suivi): static
    {
        if ($this->suivis->removeElement($suivi)) {
            // set the owning side to null (unless already changed)
            if ($suivi->getIdModule() === $this) {
                $suivi->setIdModule(null);
            }
        }

        return $this;
    }
   
    public function getNotes(): Collection
    {
        return $this->notes;
    }
   
   
    public function __toString()
{
    return $this->nom; 
}

}
