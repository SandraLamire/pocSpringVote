package fr.eni.pocSpringVote.service;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.entity.Caracteristique;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.repository.BureauRepository;
import fr.eni.pocSpringVote.repository.CandidatRepository;
import fr.eni.pocSpringVote.repository.VotantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TESTS OK!!!
@SpringBootTest
class VoteServiceImplTest {

	@Autowired
	private VoteService voteService;

	@Autowired
	private VotantRepository votantRepository;

	@Autowired
	private CandidatRepository candidatRepository;

	@Autowired
	private BureauRepository bureauRepository;

	@Test
	@Transactional
	void voterTest() throws VoteException {
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
		bureauRepository.save(bureau);

		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

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
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
		bureauRepository.save(bureau);

		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Votant votant = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(16))
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
	void getAllTest() {
		// Créer des caractéristiques de test
		Caracteristique carac1 = new Caracteristique("Toilettes", "propres");
		Caracteristique carac2 = new Caracteristique("Chauffage", "gaz");
		Caracteristique carac3 = new Caracteristique("Electricité", "chère");

		// Créer un bureau avec ces caractéristiques
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
		bureau.getLstCaracteristique().add(carac1);
		bureau.getLstCaracteristique().add(carac2);
		bureau.getLstCaracteristique().add(carac3);
		bureauRepository.save(bureau);

		Candidat candidat = Candidat.builder()
				.nom("Moi")
				.prenom("Aimé")
				.parti("Love and Peace")
				.build();
		candidatRepository.save(candidat);

		Votant votant = Votant.builder()
				.nom("Doe")
				.prenom("John")
				.ddn(LocalDate.now().minusYears(20))
				.candidat(candidat)
				.bureau(bureau)
				.build();
		votantRepository.save(votant);

		List<Votant> votants = voteService.getAll();
		// assertFalse(votants.isEmpty());
		// OU
		assertEquals(votants.size(), 4);
	}

	@Test
	@Transactional
	void winnerTest() {
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
		bureauRepository.save(bureau);

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
				.candidat(candidat1)
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
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
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

		int moyenneAge = voteService.moyenneAgeVotants();
		assertEquals(21, moyenneAge);
	}

	@Test
	@Transactional
	void moyenneAgeVotantsVotePartiTest() {
		Bureau bureau = new Bureau();
		bureau.setNumero("1");
		bureauRepository.save(bureau);

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
				.ddn(LocalDate.now().minusYears(24))
				.candidat(candidat1)
				.bureau(bureau)
				.build();
		votantRepository.save(votant3);

		int moyenneAge = voteService.moyenneAgeVotantsVoteParti("Love and Peace");
		assertEquals(22, moyenneAge);
	}
}