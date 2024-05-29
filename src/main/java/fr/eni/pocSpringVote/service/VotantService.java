package fr.eni.pocSpringVote.service;

import java.util.List;

import fr.eni.pocSpringVote.entity.Votant;

public interface VotantService {
    void voter(Votant votant) throws VotantException;
    List<Votant> getAll();
    String winner();

//    Double findAverageAgeVotantsForAParti(String parti);
}
