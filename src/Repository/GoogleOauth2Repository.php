<?php

namespace App\Repository;

use App\Entity\GoogleOauth2;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<GoogleOauth2>
 *
 * @method GoogleOauth2|null find($id, $lockMode = null, $lockVersion = null)
 * @method GoogleOauth2|null findOneBy(array $criteria, array $orderBy = null)
 * @method GoogleOauth2[]    findAll()
 * @method GoogleOauth2[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class GoogleOauth2Repository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, GoogleOauth2::class);
    }

//    /**
//     * @return GoogleOauth2[] Returns an array of GoogleOauth2 objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('g')
//            ->andWhere('g.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('g.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?GoogleOauth2
//    {
//        return $this->createQueryBuilder('g')
//            ->andWhere('g.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
