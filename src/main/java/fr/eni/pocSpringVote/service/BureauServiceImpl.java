package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.repository.BureauRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BureauServiceImpl implements BureauService {
    private BureauRepository repo;


    @Override
    public List<Bureau> getAllBureaux() {
        return (List<Bureau>) repo.findAll();
    }

    @Override
    public void addBureau(Bureau bureau) {
        repo.save(bureau);
    }

}
