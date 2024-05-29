package fr.eni.pocSpringVote.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.repository.VotantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class VotantServiceImpl implements VotantService {
    private VotantRepository repo;

    private final static Integer MAJORITE = 18;

    @Override
    @Transactional
    public void voter(Votant votant) throws VotantException {
        if(votant.getDdn().isAfter(LocalDate.now().minusYears(MAJORITE))) {
            throw new VotantException("Trop Jeune");
        }
        repo.save(votant);
    }

    @Override
    public List<Votant> getAll() {
        return (List<Votant>) repo.findAll();
    }

    @Override
    public String winner() {
        List<Object[]> results = repo.countVotesPerCandidat();
        String winner = null;
        int maxVotes = 0;

        for (Object[] result : results) {
            String candidatNom = (String) result[0];
            int votes = ((Number) result[1]).intValue();

            if (votes > maxVotes) {
                maxVotes = votes;
                winner = candidatNom;
            }
        }

        if (winner == null) {
            throw new IllegalStateException("No votes found");
        }

        return winner;

//        Integer aime = repo.countWhen("Aimé Moi");
//        Integer jessy = repo.countWhen("Jessy Lemilleur");
//        Integer jeffrey = repo.countWhen("Jeffrey Toupourvous");
//        if(aime>jessy && aime>jeffrey) {
//            return "Aimé Moi";
//        }
//        else if(jessy > aime && jessy > jeffrey) {
//            return "Jessy Lemilleur";
//        }
//        else {
//            return "Jeffrey Toupourvous";
//        }
    }

//    @Override
//    public Double findAverageAgeVotantsForAParti(String parti) {
//        return repo.findAverageAgeVotantsForAParti(parti);
//    }


}
