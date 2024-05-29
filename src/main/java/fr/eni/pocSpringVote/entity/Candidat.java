package fr.eni.pocSpringVote.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidat {
    @Id
    @GeneratedValue
    private Integer idCandidat;
    private String nom;
    private String prenom;
    private String parti;

    public Candidat(String nom, String prenom, String parti) {
        this.nom = nom;
        this.prenom = prenom;
        this.parti = parti;
    }

    // Pour retrait idCandidat à l'affichage
//    @Override
//    public String toString() {
//        return "Candidat{" +
//                "nom='" + nom + '\'' +
//                ", prenom='" + prenom + '\'' +
//                ", parti='" + parti + '\'' +
//                '}';
//    }
}
