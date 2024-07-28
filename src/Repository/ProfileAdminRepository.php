<?php

namespace App\Repository;

use App\Entity\ProfileAdmin;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ProfileAdmin>
 *
 * @method ProfileAdmin|null find($id, $lockMode = null, $lockVersion = null)
 * @method ProfileAdmin|null findOneBy(array $criteria, array $orderBy = null)
 * @method ProfileAdmin[]    findAll()
 * @method ProfileAdmin[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProfileAdminRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ProfileAdmin::class);
    }

//    /**
//     * @return ProfileAdmin[] Returns an array of ProfileAdmin objects
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

//    public function findOneBySomeField($value): ?ProfileAdmin
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
