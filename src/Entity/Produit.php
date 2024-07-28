<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ProduitRepository::class)]
class Produit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:"id_produit")]
    private ?int $id = null;

    #[ORM\Column(name:"nom",length: 255)]
    private ?string $nom = null;

    #[ORM\Column(name:"type",length: 255)]
    private ?string $type = null;

    #[ORM\Column(name:"prix_init")]
    private ?float $prix_init = null;

    #[ORM\Column(name:"marge")]
    private ?float $marge = null;

    #[ORM\Column(name:"video",length: 255)]
    private ?string $video = null;

    #[ORM\Column(name:"audio",length: 255)]
    private ?string $audio = null;

    #[ORM\Column(name:"image",length: 255)]
    private ?string $image = null;

    #[ORM\Column(name:"descrip",length: 255)]
    private ?string $descrip = null;

    #[ORM\OneToMany(targetEntity: Achat::class, mappedBy: 'id_produit')]
    private Collection $achats;

    public function __construct()
    {
        $this->achats = new ArrayCollection();
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

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getPrixInit(): ?float
    {
        return $this->prix_init;
    }

    public function setPrixInit(float $prix_init): static
    {
        $this->prix_init = $prix_init;

        return $this;
    }

    public function getMarge(): ?float
    {
        return $this->marge;
    }

    public function setMarge(float $marge): static
    {
        $this->marge = $marge;

        return $this;
    }

    public function getVideo(): ?string
    {
        return $this->video;
    }

    public function setVideo(?string $video): self
    {
        $this->video = $video;
    
        return $this;
    }
    public function getAudio(): ?string
    {
        return $this->audio;
    }

    public function setAudio(?string $audio): self
    {
        $this->audio = $audio;
    
        return $this;
    }
    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getDescrip(): ?string
    {
        return $this->descrip;
    }

    public function setDescrip(string $descrip): static
    {
        $this->descrip = $descrip;

        return $this;
    }

    /**
     * @return Collection<int, Achat>
     */
    public function getAchats(): Collection
    {
        return $this->achats;
    }

    public function addAchat(Achat $achat): static
    {
        if (!$this->achats->contains($achat)) {
            $this->achats->add($achat);
            $achat->setIdProduit($this);
        }

        return $this;
    }

    public function removeAchat(Achat $achat): static
    {
        if ($this->achats->removeElement($achat)) {
            // set the owning side to null (unless already changed)
            if ($achat->getIdProduit() === $this) {
                $achat->setIdProduit(null);
            }
        }

        return $this;
    }
}