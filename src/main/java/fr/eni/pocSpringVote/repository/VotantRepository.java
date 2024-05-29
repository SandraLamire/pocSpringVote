package fr.eni.pocSpringVote.repository;

import fr.eni.pocSpringVote.entity.Candidat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.eni.pocSpringVote.entity.Votant;

import java.util.List;

public interface VotantRepository extends CrudRepository<Votant, Integer>{

    @Query("SELECT count(v) FROM Votant v WHERE v.candidat.nom=:candidat")
    Integer countWhen(@Param("candidat") String candidat);


//    @Query("SELECT AVG(YEAR(current date)-YEAR(v.ddn)) FROM Votant v WHERE v.candidat.parti=:parti")
//    Double findAverageAgeVotantsForAParti(@Param("parti") String parti);
    @Query("SELECT v FROM Votant v WHERE v.candidat.parti=:parti")
    List<Votant> findAllToParti(@Param("parti") String parti);


    @Query("SELECT v.candidat.nom, COUNT(v) FROM Votant v GROUP BY v.candidat.nom")
    List<Object[]> countVotesPerCandidat();
}
