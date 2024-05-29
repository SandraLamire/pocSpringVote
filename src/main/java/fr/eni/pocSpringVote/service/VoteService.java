package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Votant;

import java.util.List;

import java.util.List;

import  fr.eni.pocSpringVote.entity.Votant;

public interface VoteService {
    void voter(Votant votant) throws VoteException;
    List<Votant> getAll();
    String winner();
    Integer moyenneAgeVotants();
    Integer moyenneAgeVotantsVoteParti(String parti);
}

