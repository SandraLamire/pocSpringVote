package fr.eni.pocSpringVote.ws;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.service.BureauService;
import fr.eni.pocSpringVote.service.VotantService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bureau")
public class BureauWS {

    @Autowired
    BureauService service;

    @PostConstruct
    public void init() {
        service.addBureau(new Bureau("1A","rue du Vote"));
        service.addBureau(new Bureau("2B","rue du Pour"));
        service.addBureau(new Bureau("3T","rue du Contre"));
    }

    @GetMapping
    public List<BureauDTO> getAll() {
        List<BureauDTO> lstBureaux = new ArrayList<>();
        service.getAllBureaux().forEach(b->lstBureaux.add(new BureauDTO(b)));
        return lstBureaux;
    }

}
