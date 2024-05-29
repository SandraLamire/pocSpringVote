package fr.eni.pocSpringVote.repository;

import fr.eni.pocSpringVote.entity.Candidat;
import org.springframework.data.repository.CrudRepository;


public interface CandidatRepository extends CrudRepository<Candidat, Integer>{

}
