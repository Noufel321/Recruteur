package com.example.master_app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rh")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
public class RH extends Utilisateur {
    @OneToMany(mappedBy = "rh")
    private List<Entretien> entretiens;

    public void gererUtilisateurs() {
        // Logique pour gérer les utilisateurs
    }

    public void superviserEntretiens() {
        // Logique pour superviser les entretiens
    }

    public void genererRapports() {
        // Logique pour générer des rapports
    }
}
