package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Bureau;


import java.util.List;

public interface BureauService {
    List<Bureau> getAllBureaux();
    public void addBureau(Bureau bureau);

}
