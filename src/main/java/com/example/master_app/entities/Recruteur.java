package com.example.master_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "recruteur")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
public class Recruteur extends Utilisateur {
    @OneToMany(mappedBy = "recruteur")
    private List<Entretien> entretiens;

    public void planifierEntretien() {
        // Logique pour planifier un entretien
    }

    public void suivreCandidats() {
        // Logique pour suivre les candidats
    }

    public void ajouterFeedback() {
        // Logique pour ajouter un feedback
    }

    public void analyser() {
        // Logique pour analyser
    }
}
