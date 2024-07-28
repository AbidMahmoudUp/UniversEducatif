<?php
namespace App\Form;

use App\Entity\Produit;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ProduitType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextType::class)
            ->add('type', TextType::class)
            ->add('prix_init', TextType::class)
            ->add('marge', TextType::class)
            ->add('video', FileType::class, [
                'label' => 'Upload Video (formats: mp4, avi)',
                'mapped' => true,
                'required' => false,
            ])
            ->add('audio', FileType::class, [
                'label' => 'Upload Audio (formats: mp3, wav)',
                'mapped' => true,
                'required' => false,
            ])
            ->add('image', FileType::class, [
                'label' => 'Upload Image (formats: jpeg, png)',
                'mapped' => true,
                'required' => false,
            ])
            ->add('descrip', TextType::class);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }
}