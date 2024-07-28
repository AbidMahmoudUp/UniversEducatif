<?php

namespace App\Form;

use App\Entity\Modules;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;


class ModulesType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('nom', null, [
            'constraints' => [
                new Assert\NotBlank(['message' => 'Le champ nom est requis.']),
                new Assert\Regex([
                    'pattern' => '/^[a-zA-Z\s]+$/',
                    'message' => 'Le nom doit contenir uniquement des lettres.',
                ]),
            ],
        ])
        ->add('description', null, [
            'constraints' => [
                new Assert\NotBlank(['message' => 'Le champ description est requis.']),
            ],
        ])
            ->add('imgPath', FileType::class, [
                'label' => 'Image', // Libellé du champ dans le formulaire
                'mapped' => false, // Indique si ce champ est mappé à une propriété de l'entité
                'required' => false, // Indique si ce champ est obligatoire
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Modules::class,
            'constraints' => [
                new UniqueEntity([
                    'fields' => 'nom',
                    'message' => 'Ce nom est déjà utilisé.'
                ]),
            ],
        ]);
    }
    }

