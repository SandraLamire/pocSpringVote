package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Candidat;

import java.util.List;
import java.util.Optional;


public interface CandidatService {
    List<Candidat> getAllCandidat();
    Optional<Candidat> getCandidatById(Integer id);
    void addCandidat(Candidat candidat);
    void modifCandidat(Candidat candidat);
    void removeCandidat(Candidat candidat);
    void removeCandidatById(Integer id);


}
