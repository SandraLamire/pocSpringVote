package fr.eni.pocSpringVote.ws;

import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.service.CandidatService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidat")
public class CandidatWS {

    @Autowired
    CandidatService service;

    @PostConstruct
    public void init() {
        service.addCandidat(new Candidat("Martin", "Michel", "MicheMiche party"));
        service.addCandidat(new Candidat("Boulechite", "Ly", "Crois moi!"));
        service.addCandidat(new Candidat("Cancan", "France", "Pour le pays"));
    }

    @GetMapping
    public List<Candidat> getAll() {
        return service.getAllCandidat();
    }

    @GetMapping("/{id}")
    public Optional<Candidat> getById(@PathVariable("id") Integer id){
        return service.getCandidatById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        service.removeCandidatById(id);
    }

    @PostMapping
    public Candidat addCandidat(@RequestBody Candidat candidat) {
        service.addCandidat(candidat);
        return candidat;
    }

    @PutMapping
    public Candidat modifCandidat(@RequestBody Candidat candidat) {
        service.modifCandidat(candidat);
        return candidat;
    }


}
