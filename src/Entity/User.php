<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: UserRepository::class)]
class User implements UserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: "IdUser")]
    private ?int $id = null;

    #[Assert\NotBlank(message:"Username cannot be blank.")]
    #[Assert\Length(min: 3, minMessage: "Username must be at least {{ limit }} characters long.")]
    #[ORM\Column(name: "UserName")]
    private ?string $userName = null;

    #[Assert\NotBlank(message:"Email cannot be blank.")]
    #[Assert\Email(message:"Invalid email format.")]
    #[ORM\Column(name: "Email",length: 255)]
    private ?string $email = null;

    #[Assert\NotBlank(message:"Password cannot be blank.")]
    #[Assert\Length(min: 3, minMessage:"Password must be at least {{ limit }} characters.")]
    #[ORM\Column(name: "Password",length: 255)]
    private ?string $password = null;

    #[ORM\Column(name: "Status",length: 255)]
    private ?string $status = null;

    #[ORM\Column(name: "State",length: 255)]
    private ?string $state = null;

    #[ORM\OneToMany(targetEntity: Dossier::class, mappedBy: 'IdUser')]
    private Collection $dossiers;

    #[ORM\OneToMany(targetEntity: Notes::class, mappedBy: 'idUser')]
    private Collection $notes;

    #[ORM\OneToMany(targetEntity: Suivi::class, mappedBy: 'idUser',cascade:["persist","remove"])]
    private Collection $suivis;

    #[ORM\OneToMany(targetEntity: Logs::class, mappedBy: 'idUser')]
    private Collection $logs;

    #[ORM\OneToMany(targetEntity: Commande::class, mappedBy: 'id_user')]
    private Collection $commandes;

    #[ORM\OneToOne(targetEntity: Profile::class, mappedBy: 'idUser')]
    private Profile $profile;

    #[ORM\OneToOne(targetEntity: GoogleOauth2::class, mappedBy: 'user')]
    private GoogleOauth2 $googleOauth;
    private ?array $roles = null;

    public function __construct()
    {
        $this->dossiers = new ArrayCollection();
        $this->notes = new ArrayCollection();
        $this->suivis = new ArrayCollection();
        $this->logs = new ArrayCollection();
        $this->commandes = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUserName(): ?string
    {
        return $this->userName;
    }

    public function setUserName(string $userName): static
    {
        $this->userName = $userName;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): static
    {
        $this->email = $email;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): static
    {
        $this->password = $password;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): static
    {
        $this->status = $status;

        return $this;
    }

    public function getState(): ?string
    {
        return $this->state;
    }

    public function setState(string $state): static
    {
        $this->state = $state;

        return $this;
    }

    public function getProfile()
    {
        return $this->profile;
    }

    /**
     * @return Collection<int, Dossier>
     */
    public function getDossiers(): Collection
    {
        return $this->dossiers;
    }

    public function addDossier(Dossier $dossier): static
    {
        if (!$this->dossiers->contains($dossier)) {
            $this->dossiers->add($dossier);
            $dossier->setIdUser($this);
        }

        return $this;
    }

    public function removeDossier(Dossier $dossier): static
    {
        if ($this->dossiers->removeElement($dossier)) {
            // set the owning side to null (unless already changed)
            if ($dossier->getIdUser() === $this) {
                $dossier->setIdUser(null);
            }
        }

        return $this;
    }

    public function __toString()
    {
        return $this->userName;
    }

    /**
     * @return Collection<int, Notes>
     */
    public function getNotes(): Collection
    {
        return $this->notes;
    }

    public function addNote(Notes $note): static
    {
        if (!$this->notes->contains($note)) {
            $this->notes->add($note);
            $note->setIdUser($this);
        }

        return $this;
    }

    public function removeNote(Notes $note): static
    {
        if ($this->notes->removeElement($note)) {
            // set the owning side to null (unless already changed)
            if ($note->getIdUser() === $this) {
                $note->setIdUser(null);
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
            $suivi->setIdUser($this);
        }

        return $this;
    }

    public function removeSuivi(Suivi $suivi): static
    {
        if ($this->suivis->removeElement($suivi)) {
            // set the owning side to null (unless already changed)
            if ($suivi->getIdUser() === $this) {
                $suivi->setIdUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Logs>
     */
    public function getlogs(): Collection
    {
        return $this->logs;
    }

    public function addlogs(Logs $logs): static
    {
        if (!$this->logs->contains($logs)) {
            $this->logs->add($logs);
            $logs->setIdUser($this);
        }

        return $this;
    }

    public function removelogs(Logs $logs): static
    {
        if ($this->logs->removeElement($logs)) {
            // set the owning side to null (unless already changed)
            if ($logs->getIdUser() === $this) {
                $logs->setIdUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Commande>
     */
    public function getCommandes(): Collection
    {
        return $this->commandes;
    }

    public function addCommande(Commande $commande): static
    {
        if (!$this->commandes->contains($commande)) {
            $this->commandes->add($commande);
            $commande->setIdUser($this);
        }

        return $this;
    }

    public function removeCommande(Commande $commande): static
    {
        if ($this->commandes->removeElement($commande)) {
            // set the owning side to null (unless already changed)
            if ($commande->getIdUser() === $this) {
                $commande->setIdUser(null);
            }
        }

        return $this;
    }
    public function getGoogleOauth()
    {
        return $this->googleOauth;
    }
    public function setGoogleOauth(GoogleOauth2 $googleOauth)
    {
         $this->googleOauth=$googleOauth;
    }
    public function getSalt(): ?string
    {
        return null;
    }

    public function eraseCredentials(): void
    {
      
    }
    public function getUserIdentifier(): string
    {
 
        return 'user' . $this->id;
    }
    public function setRoles(?array $roles): static
    {
        $this->roles = $roles;

        return $this;
    }
    public function getRoles(): array
    {
        // Return the roles for the user
        // You can adjust this based on your application logic
        return ['ROLE_USER'];
    }


}
