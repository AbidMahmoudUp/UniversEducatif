<?php

namespace App\Form;

use App\Entity\Cours;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

use Symfony\Component\Validator\Constraints as Assert;

class CoursType extends AbstractType
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
                    new Assert\Length([
                        'min' => 5,
                        'minMessage' => 'La description doit contenir au moins {{ limit }} caractères.',
                    ]),
                ],
            ])
            ->add('fichierPath', FileType::class, [
                'label' => 'Fichier PDF',
                'mapped' => false,
                'required' => false,
            ])
            ->add('tempsC', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Le champ tempsC est requis.']),
                    new Assert\Type([
                        'type' => 'integer',
                        'message' => 'Le champ tempsC doit être un entier.',
                    ]),
                ],
            ])
            ->add('videoPath', FileType::class, [
                'label' => 'Vidéo',
                'mapped' => false,
                'required' => false,
            
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Cours::class,
            'constraints' => [
                new UniqueEntity([
                    'fields' => 'nom',
                    'message' => 'Ce nom est déjà utilisé.'
                ]),
            ],
        ]);
    }
}
