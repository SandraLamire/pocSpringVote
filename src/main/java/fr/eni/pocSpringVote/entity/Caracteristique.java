package fr.eni.pocSpringVote.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Caracteristique {
    @Id
    @GeneratedValue
    private Integer idCaracteristique;

    @Column
    private String libelle;

    @Column
    private String description;

    public Caracteristique(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
}

