<?php

namespace App\Repository;

use App\Entity\Suivi;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Suivi>
 *
 * @method Suivi|null find($id, $lockMode = null, $lockVersion = null)
 * @method Suivi|null findOneBy(array $criteria, array $orderBy = null)
 * @method Suivi[]    findAll()
 * @method Suivi[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SuiviRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Suivi::class);
    }
    public function nombreSuivisParModule(): array
    {
        return $this->createQueryBuilder('s')
            ->select('m.id as idModule, COUNT(s.id) as nombreSuivis, m.nom as nomModule')
            ->leftJoin('s.idModule', 'm') 
            ->groupBy('m.id') 
            ->getQuery()
            ->getResult();
    }
    public function findSuivisDepassantUneSemaine(): array
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.dateConsultation < :date')
            ->setParameter('date', new \DateTime('-1 week'))
            ->getQuery()
            ->getResult();
    }

//    /**
//     * @return Suivi[] Returns an array of Suivi objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('s')
//            ->andWhere('s.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('s.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Suivi
//    {
//        return $this->createQueryBuilder('s')
//            ->andWhere('s.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
