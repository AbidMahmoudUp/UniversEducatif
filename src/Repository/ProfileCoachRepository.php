<?php

namespace App\Repository;

use App\Entity\ProfileCoach;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ProfileCoach>
 *
 * @method ProfileCoach|null find($id, $lockMode = null, $lockVersion = null)
 * @method ProfileCoach|null findOneBy(array $criteria, array $orderBy = null)
 * @method ProfileCoach[]    findAll()
 * @method ProfileCoach[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProfileCoachRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ProfileCoach::class);
    }

//    /**
//     * @return ProfileCoach[] Returns an array of ProfileCoach objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('p.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?ProfileCoach
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
