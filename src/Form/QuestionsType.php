<?php

namespace App\Form;

use App\Entity\Questions;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

class QuestionsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('question')
            ->add('choix1')
            ->add('choix2')
            ->add('choix3')        
            ->add('choixcorrect', null, [
                'constraints' => new Callback([$this, 'validateChoixcorrect'])
            ])
            ->add('duree')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Questions::class,
        ]);
    }
    public function validateChoixcorrect($value, ExecutionContextInterface $context)
{
    $question = $context->getRoot()->getData(); 

    $choix1 = $question->getChoix1();
    $choix2 = $question->getChoix2();
    $choix3 = $question->getChoix3();

    if ($value !== $choix1 && $value !== $choix2 && $value !== $choix3) {
        $errorMessage = 'The chosen correct choice is invalid.';
        $context->buildViolation($errorMessage)
            ->atPath('choixcorrect')
            ->addViolation();

        echo '<script>window.alert("' . $errorMessage . '");</script>';
    }
}

}
