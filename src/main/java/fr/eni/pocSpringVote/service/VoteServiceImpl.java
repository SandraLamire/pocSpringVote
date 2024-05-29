package fr.eni.pocSpringVote.service;

import java.time.LocalDate;
import java.util.List;


import  fr.eni.pocSpringVote.entity.Votant;
import  fr.eni.pocSpringVote.repository.BureauRepository;
import  fr.eni.pocSpringVote.repository.CandidatRepository;
import  fr.eni.pocSpringVote.repository.VotantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService{
    private VotantRepository repoVotant;
    private CandidatRepository repoCandidat;
    private BureauRepository repoBureau;

    private final static Integer MAJORITE = 18;

    @Override
    @Transactional
    public void voter(Votant votant) throws VoteException {
        if(votant.getDdn().isAfter(LocalDate.now().minusYears(MAJORITE))) {
            throw new VoteException("Trop Jeune");
        }
        repoBureau.save(votant.getBureau());
        repoCandidat.save(votant.getCandidat());
        repoVotant.save(votant);
    }

    @Override
    public List<Votant> getAll() {
        return (List<Votant>) repoVotant.findAll();
    }

    @Override
    public String winner() {
        Integer aime = repoVotant.countWhen("Moi");
        Integer jessy = repoVotant.countWhen("Lemilleur");
        Integer jeffrey = repoVotant.countWhen("Toupourvous");
        if(aime>jessy && aime>jeffrey) {
            return "Aimé Moi";
        }
        else if(jessy > aime && jessy > jeffrey) {
            return "Jessy Lemilleur";
        }
        else {
            return "Jeffrey Toupourvous";
        }
    }

    @Override
    public Integer moyenneAgeVotants() {
        return (int) getAll().stream().mapToInt(v->v.getAge()).average().orElse(0);
    }

    @Override
    public Integer moyenneAgeVotantsVoteParti(String parti) {
        return (int) repoVotant.findAllToParti(parti).stream().mapToInt(v->v.getAge()).average().orElse(0);
    }

}