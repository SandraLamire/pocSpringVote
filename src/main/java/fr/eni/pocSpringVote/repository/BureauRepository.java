package fr.eni.pocSpringVote.repository;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Candidat;
import org.springframework.data.repository.CrudRepository;


public interface BureauRepository extends CrudRepository<Bureau, Integer>{

}
