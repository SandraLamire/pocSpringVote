package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.repository.CandidatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CandidatServiceImpl implements CandidatService {

    private CandidatRepository repo;

    @Override
    public List<Candidat> getAllCandidat() {
        return (List<Candidat>) repo.findAll();
    }

    @Override
    public Optional<Candidat> getCandidatById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void addCandidat(Candidat candidat) {
        repo.save(candidat);
    }

    @Override
    public void modifCandidat(Candidat candidat) {
        repo.save(candidat);
    }

    @Override
    public void removeCandidat(Candidat candidat) {
        repo.delete(candidat);
    }

    @Override
    public void removeCandidatById(Integer id) {
        repo.deleteById(id);
    }
}
