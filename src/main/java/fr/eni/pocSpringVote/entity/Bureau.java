package fr.eni.pocSpringVote.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bureau {
    @Id
    @GeneratedValue
    private Integer idBureau;
    private String numero;
    private String adresse;

    @OneToMany
    @ToString.Exclude
    private List<Votant> lstVotants = new ArrayList<>();

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Caracteristique> lstCaracteristique = new ArrayList<>();

    public Bureau(String numero, String adresse) {
        super();
        this.numero = numero;
        this.adresse = adresse;
    }

    public void addVotant(Votant votant) {
        lstVotants.add(votant);
        votant.setBureau(this);
    }
}

