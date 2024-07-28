<?php

namespace App\Entity;

use App\Repository\GoogleOauth2Repository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;

#[ORM\Entity(repositoryClass: GoogleOauth2Repository::class)]
#[ORM\Table(name: "googleoauth2")]
class GoogleOauth2 implements UserInterface
{
    #[ORM\Id]
    #[ORM\JoinColumn(name:"id",referencedColumnName:"IdUser",nullable: false)]
    #[ORM\OneToOne]
    private User $user;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $email = null;

    #[ORM\Column(nullable: true)]
    private ?array $roles = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $password = null;
  

    public function getUsername(): string
    {
        // Return a unique identifier for the user, e.g., email
        return 'google_user_' . $this->user->getId();
    }

    public function getRoles(): array
    {
        // Return the roles for the user
        // You can adjust this based on your application logic
        return ['ROLE_USER'];
    }

    public function getPassword(): ?string
    {
        // Return the password (not applicable for OAuth users)
        return null;
    }

    public function getSalt(): ?string
    {
        // Return the salt (not applicable for OAuth users)
        return null;
    }

    public function eraseCredentials(): void
    {
        // Implement if you need to erase credentials
        // This method is called after the user has been authenticated
        // You may leave it empty if it's not needed
    }
    public function getUserIdentifier(): string
    {
        // Return a unique identifier for the user
        // You can use any unique property of the user, such as email or ID
        return 'google_user_' . $this->user->getId();
    }
    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(?string $email): static
    {
        $this->email = $email;

        return $this;
    }

    public function setRoles(?array $roles): static
    {
        $this->roles = $roles;

        return $this;
    }

    public function setPassword(?string $password): static
    {
        $this->password = $password;

        return $this;
    }

    public function getUser()
    {
        return $this->user;
    }
    
    public function setUser(User $user)
    {
         $this->user=$user;
    }
}
