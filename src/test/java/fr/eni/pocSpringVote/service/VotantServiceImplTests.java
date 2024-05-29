package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.repository.BureauRepository;
import fr.eni.pocSpringVote.repository.CandidatRepository;
import fr.eni.pocSpringVote.repository.VotantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// TESTS OK !!!
@SpringBootTest
class VotantServiceImplTests {

	@Autowired
	VoteService voteService;

	@Autowired
	VotantRepository votantRepository;

	@Autowired
	CandidatRepository candidatRepository;

	@Autowired
	BureauRepository bureauRepository;

	@Test
	@Transactional
	void voterTest() throws VoteException {
		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Bureau bureau = Bureau.builder()
				.numero("1")
				.build();
		bureauRepository.save(bureau);

		Votant votant = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(20))
				.candidat(candidat)
				.bureau(bureau)
				.build();

		voteService.voter(votant);

		assertNotNull(votant.getIdVotant());
		assertTrue(votantRepository.existsById(votant.getIdVotant()));
	}

	@Test
	@Transactional
	void voterTestTropJeune() {
		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Bureau bureau = Bureau.builder()
				.numero("1")
				.build();
		bureauRepository.save(bureau);

		Votant votant = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(17))
				.candidat(candidat)
				.bureau(bureau)
				.build();

		assertThrows(VoteException.class, () -> {
			voteService.voter(votant);
		});

		// Ne passe pas :
		// assertFalse(votantRepository.existsById(votant.getIdVotant()));
		// A remplacer par :
		// Vérifier que l'ID du votant est bien null après l'exception
		assertNull(votant.getIdVotant());
	}

	@Test
	@Transactional
	void winnerTest() {
		Candidat candidat1 = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat1);

		Candidat candidat2 = Candidat.builder()
				.nom("Lemilleur")
				.prenom("Jessy")
				.parti("Best Party")
				.build();
		candidatRepository.save(candidat2);

		Candidat candidat3 = Candidat.builder()
				.nom("Toupourvous")
				.prenom("Jeffrey")
				.parti("Great Party")
				.build();
		candidatRepository.save(candidat3);

		Bureau bureau = Bureau.builder()
				.numero("1")
				.build();
		bureauRepository.save(bureau);

		Votant votant1 = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(20))
				.candidat(candidat1)
				.bureau(bureau)
				.build();
		votantRepository.save(votant1);

		Votant votant2 = Votant.builder()
				.nom("Smith")
				.prenom("Jane")
				.ddn(LocalDate.now().minusYears(22))
				.candidat(candidat2)
				.bureau(bureau)
				.build();
		votantRepository.save(votant2);

		Votant votant3 = Votant.builder()
				.nom("Doe")
				.prenom("Alice")
				.ddn(LocalDate.now().minusYears(25))
				.candidat(candidat3)
				.bureau(bureau)
				.build();
		votantRepository.save(votant3);

		String winner = voteService.winner();
		assertNotNull(winner);
		assertEquals("Aimé Moi", winner);
	}

	@Test
	@Transactional
	void moyenneAgeVotantsTest() {
		Bureau bureau = Bureau.builder()
				.numero("1")
				.build();
		bureauRepository.save(bureau);

		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Votant votant1 = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(20))
				.candidat(candidat)
				.bureau(bureau)
				.build();
		votantRepository.save(votant1);

		Votant votant2 = Votant.builder()
				.nom("Smith")
				.prenom("Jane")
				.ddn(LocalDate.now().minusYears(22))
				.candidat(candidat)
				.bureau(bureau)
				.build();
		votantRepository.save(votant2);

		Integer moyenneAge = voteService.moyenneAgeVotants();
		assertEquals(21, moyenneAge); // (20 + 22) / 2 = 21
	}

	@Test
	@Transactional
	void moyenneAgeVotantsVotePartiTest() {
		Bureau bureau = Bureau.builder()
				.numero("1")
				.build();
		bureauRepository.save(bureau);

		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Votant votant1 = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(20))
				.candidat(candidat)
				.bureau(bureau)
				.build();
		votantRepository.save(votant1);

		Votant votant2 = Votant.builder()
				.nom("Smith")
				.prenom("Jane")
				.ddn(LocalDate.now().minusYears(22))
				.candidat(candidat)
				.bureau(bureau)
				.build();
		votantRepository.save(votant2);

		Integer moyenneAge = voteService.moyenneAgeVotantsVoteParti("Love and Peace");
		assertEquals(21, moyenneAge); // (20 + 22) / 2 = 21
	}
}
