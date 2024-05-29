package fr.eni.pocSpringVote;

import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.service.VotantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webservice")
public class VoteController {

    // TEST D'AFFICHAGE DU WEB SERVICE : http://localhost:8080/webservice/candidats
//    @GetMapping("/candidats")
//    public List<Candidat> getCandidats() {
//        return Arrays.asList(new Candidat("MeAlone", "Liv", "parti sans laisser d’adresse"),new Candidat("Moi", "Aimé", "Love and Peace"));
//    }

    @Autowired
    private VotantService votantService;

    @GetMapping("/candidatsWithId")
    public Map<Candidat, Long> getCandidatsWithVotesUnformat() {
        List<Votant> votants = votantService.getAll();

        // Calculer le nombre de votes pour chaque candidat
        // et retourner la map de candidats avec leur nombre de votes
        return votants.stream()
                .collect(Collectors.groupingBy(Votant::getCandidat, Collectors.counting()));
    }

    @GetMapping("/candidats")
    public List<Map<String, Object>> getCandidatsWithVotesformat() {
        List<Votant> votants = votantService.getAll();

        // Calculer le nombre de votes pour chaque candidat
        Map<Candidat, Long> votesByCandidat = votants.stream()
                .collect(Collectors.groupingBy(Votant::getCandidat, Collectors.counting()));

        // Mapper les données de chaque candidat dans une Map
        return votesByCandidat.entrySet().stream()
                .map(entry -> {
                    Candidat candidat = entry.getKey();
                    int nombreDeVotes = entry.getValue().intValue();
                    Map<String, Object> candidatData = Map.of(
                            "nom", candidat.getNom(),
                            "prenom", candidat.getPrenom(),
                            "parti", candidat.getParti()
                    );

                    return Map.of(
                            "candidat", candidatData, "nombre De Votes", nombreDeVotes
                    );
                })
                .collect(Collectors.toList());
    }
}

// getCandidatsWithVotesUnformat
//{
//        "Candidat(idCandidat=1, nom=Moi, prenom=Aimé, parti=Sans Laisser D'Adresse)": 2,
//        "Candidat(idCandidat=2, nom=Lemiller, prenom=Jessy, parti=Denrire)": 1
//}


// getCandidatsWithVotesformat
//[
//        {
//        "candidat": {
//        "parti": "Sans Laisser D'Adresse",
//        "nom": "Moi",
//        "prenom": "Aimé"
//        },
//        "nombre De Votes": 2
//        },
//        {
//        "candidat": {
//        "parti": "Denrire",
//        "nom": "Lemiller",
//        "prenom": "Jessy"
//        },
//        "nombre De Votes": 1
//        }
//]