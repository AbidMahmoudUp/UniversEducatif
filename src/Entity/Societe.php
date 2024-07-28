<?php

namespace App\Entity;

use App\Repository\SocieteRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: SocieteRepository::class)]
class Societe
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idSociete")]
    private ?int $id = null;

    #[ORM\Column(name: "nomSociete",length: 30)]
    #[Assert\NotBlank]
    #[Assert\Length(min:4)]
    private ?string $nomSociete = null;

    #[ORM\Column(name: "description",length: 100)]
    #[Assert\NotBlank]
    #[Assert\Length(min:8)]
    private ?string $description = null;

    #[ORM\OneToMany(targetEntity: Offre::class, mappedBy: 'idSociete')]
    private Collection $offres;

    public function __construct()
    {
        $this->offres = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomSociete(): ?string
    {
        return $this->nomSociete;
    }

    public function setNomSociete(string $nomSociete): static
    {
        $this->nomSociete = $nomSociete;

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

    /**
     * @return Collection<int, Offre>
     */
    public function getOffres(): Collection
    {
        return $this->offres;
    }

    public function addOffre(Offre $offre): static
    {
        if (!$this->offres->contains($offre)) {
            $this->offres->add($offre);
            $offre->setIdSociete($this);
        }

        return $this;
    }

    public function removeOffre(Offre $offre): static
    {
        if ($this->offres->removeElement($offre)) {
            // set the owning side to null (unless already changed)
            if ($offre->getIdSociete() === $this) {
                $offre->setIdSociete(null);
            }
        }

        return $this;
    }

    public function __toString()
    {
        return $this->nomSociete;
    }
}
