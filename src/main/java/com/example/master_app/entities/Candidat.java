package com.example.master_app.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "candidat")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
public class Candidat extends Utilisateur {
    @OneToMany(mappedBy = "candidat")
    private List<Entretien> entretiens;

    public void consulterInformations() {
        // Logique pour consulter les informations
    }

    public void confirmerEntretien() {
        // Logique pour confirmer un entretien
    }
}