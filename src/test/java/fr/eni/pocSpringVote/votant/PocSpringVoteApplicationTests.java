package fr.eni.pocSpringVote.votant;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.entity.Caracteristique;
import fr.eni.pocSpringVote.entity.Votant;
import fr.eni.pocSpringVote.service.VoteException;
import fr.eni.pocSpringVote.service.VoteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
class PocSpringVoteApplicationTests {

	@Autowired
	private VoteService service;

	@Test
	@Transactional
	void contextLoads() throws VoteException {
		Caracteristique caracteristique1 = Caracteristique.builder().libelle("Chauffage").description("Mode été").build();
		Caracteristique caracteristique2 = Caracteristique.builder().libelle("Gaz").description("Vas payer d'abord!!!").build();

		Bureau b = Bureau.builder().numero("1B").adresse("là à coté").lstCaracteristique(new ArrayList<>()).build();

		b.getLstCaracteristique().add(caracteristique1);
		b.getLstCaracteristique().add(caracteristique2);

		Candidat c1 = Candidat.builder().nom("Moi").prenom("Aimé").parti("Sans Laisser D'Adresse").build();
		Candidat c2 = Candidat.builder().nom("Lemiller").prenom("Jessy").parti("Denrire").build();

		service.voter(Votant.builder().nom("Terrieur").prenom("alex").ddn(LocalDate.now().minusYears(21)).candidat(c1).bureau(b).build());
		try {
			service.voter(Votant.builder().nom("Tim").prenom("Vincent").ddn(LocalDate.now().minusYears(12)).candidat(c1).bureau(b).build());
		} catch (VoteException e) {
			System.err.println(e.getMessage());;
		}

		service.voter(Votant.builder().nom("Latournée").prenom("Jerémie").ddn(LocalDate.now().minusYears(21)).candidat(c1).bureau(b).build());
		service.voter(Votant.builder().nom("Conpas").prenom("Amédé").ddn(LocalDate.now().minusYears(21)).candidat(c2).bureau(b).build());

		System.out.println("---------------------------");
		service.getAll().forEach(System.out::println);

		System.out.println("Le gagnant est .....");
		System.out.println(service.winner());

		System.out.println("-----------------------------");
		System.out.println("MOYENNE AGE DES VOTANTS");
		System.out.println(service.moyenneAgeVotants());

		System.out.println("-----------------------------");
		System.out.println("MOYENNE AGE DES VOTANTS QUI ON VOTE POUR LE PARTI Sans Laisser D'Adresse");
		System.out.println(service.moyenneAgeVotantsVoteParti("Sans Laisser D'Adresse") + " ans");
	}

}
