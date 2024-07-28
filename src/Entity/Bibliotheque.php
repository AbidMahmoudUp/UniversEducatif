<?php

namespace App\Entity;

use App\Repository\BibliothequeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BibliothequeRepository::class)]
class Bibliotheque
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"idBib")]
    private ?int $id = null;

    #[ORM\Column(name:"nom",length: 255)]
    private ?string $nom = null;

    #[ORM\Column(name:"mail",length: 255)]
    private ?string $mail = null;

    #[ORM\Column(name:"nbrLivre")]
    private ?int $nbrLivre = null;

    #[ORM\OneToMany(targetEntity: Livre::class, mappedBy: 'idBib')]
    private Collection $livres;

    public function __construct()
    {
        $this->livres = new ArrayCollection();
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

    public function getMail(): ?string
    {
        return $this->mail;
    }

    public function setMail(string $mail): static
    {
        $this->mail = $mail;

        return $this;
    }

    public function getNbrLivre(): ?int
    {
        return $this->nbrLivre;
    }

    public function setNbrLivre(int $nbrLivre): static
    {
        $this->nbrLivre = $nbrLivre;

        return $this;
    }

    /**
     * @return Collection<int, Livre>
     */
    public function getLivres(): Collection
    {
        return $this->livres;
    }

    public function addLivre(Livre $livre): static
    {
        if (!$this->livres->contains($livre)) {
            $this->livres->add($livre);
            $livre->setIdBib($this);
        }

        return $this;
    }

    public function removeLivre(Livre $livre): static
    {
        if ($this->livres->removeElement($livre)) {
            // set the owning side to null (unless already changed)
            if ($livre->getIdBib() === $this) {
                $livre->setIdBib(null);
            }
        }

        return $this;
    }

    public function __toString()
    {
        return $this->nom;
    }
}
