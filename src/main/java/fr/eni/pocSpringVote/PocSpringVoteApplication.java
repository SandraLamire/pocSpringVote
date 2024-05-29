package fr.eni.pocSpringVote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.pocSpringVote.entity.Bureau;
import fr.eni.pocSpringVote.entity.Candidat;
import fr.eni.pocSpringVote.entity.Caracteristique;
import fr.eni.pocSpringVote.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.eni.pocSpringVote.entity.Votant;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
//public class PocSpringVoteApplication{
//	public static void main(String[] args) {
//		SpringApplication.run(PocSpringVoteApplication.class, args);
//	}
//}

// REMPLACER POUR AFFICHAGE WEB
public class PocSpringVoteApplication implements CommandLineRunner {

//    private final CandidatRepository candidatRepository;
//    private VotantService votantService;
//    private CandidatService candidatService;

	private VoteService service;

	public static void main(String[] args) {
		SpringApplication.run(PocSpringVoteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		// Créer d'abord un candidat
//		Candidat candidat1 = Candidat.builder()
//				.nom("MeAlone")
//				.prenom("Liv")
//				.parti("parti sans laisser d’adresse")
//				.build();
//
//		candidatService.enregistrerCandidat(candidat1);
//
//		// Ensuite, utiliser cet objet candidat pour construire le votant
//		Votant votant1 = Votant.builder()
//				.nom("Terrieur")
//				.prenom("Alex")
//				.ddn(LocalDate.now().minusYears(21))
//				.candidat(candidat1)
//				.build();
//
//		// Appeler le service pour enregistrer le votant et son candidat
//		votantService.voter(votant1);
//
//        Candidat candidat2 = Candidat.builder()
//                .nom("Moi")
//                .prenom("Aimé")
//                .parti("Love and Peace")
//                .build();
//
//		candidatService.enregistrerCandidat(candidat2);
//
//        Votant votant2 = Votant.builder()
//                .nom("Ine")
//                .prenom("Michel")
//                .ddn(LocalDate.now().minusYears(21))
//                .candidat(candidat2)
//                .build();
//
//        votantService.voter(votant2);
//
//        Candidat candidat3 = Candidat.builder()
//                .nom("LeBlanc")
//                .prenom("Juste")
//                .parti("parti sans laisser d’adresse")
//                .build();
//
//		candidatService.enregistrerCandidat(candidat3);
//
//        Votant votant3 = Votant.builder()
//                .nom("Machin")
//                .prenom("Mike")
//                .ddn(LocalDate.now().minusYears(21))
//                .candidat(candidat1)
//                .build();
//
//        votantService.voter(votant3);

//		System.out.println("---------------------------");
//		votantService.getAll().forEach(System.out::println);
//		System.out.println("---------------------------");
//
//		System.out.println("------- VOTES -------");
//		System.out.println("Votant 1 " + votant1.getNom() + "(" + votant1.getDdn() + ") vote pour : " + votant1.getCandidat().getNom());
//		System.out.println("Votant 2 vote pour : " + votant2.getCandidat().getNom());
//		System.out.println("Votant 3 vote pour : " + votant3.getCandidat().getNom());
//
//		System.out.println("---------------------------");
//		System.out.println("Le gagnant est .....");
//		System.out.println(votantService.winner());
//
//		System.out.println("----- AGE MOYEN DES VOTANTS D'UN PARTI------");

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
