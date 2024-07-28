<?php

namespace App\Form;

use App\Entity\Modules;
use App\Repository\ModulesRepository;
use Doctrine\DBAL\Types\TextType;
use Doctrine\ORM\Mapping\Entity;
use LogicException;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractRendererEngine;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType as TypeTextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormView;
use Symfony\Component\Form\Test\FormInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CoachType extends AbstractType
{
  
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('firstName',null,['attr'=>['id'=>'name']])
        ->add('lastName')
        ->add('email')
        

        ->add('password',PasswordType::class)
        ->add('module',EntityType::class,[
            'class'=>Modules::class,
            'query_builder'=> function ( ModulesRepository $modulesRepository){
                $modulesRepository->findAll();
            }
        ])
        ->add('phonenumber')
        ->add('signUp',SubmitType::class)

        ;
    }


}
