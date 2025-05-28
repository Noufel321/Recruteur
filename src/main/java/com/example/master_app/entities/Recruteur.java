package com.example.master_app.entities;

import com.example.master_app.enumes.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recruteur")
public class Recruteur extends Utilisateur {


    @OneToMany(mappedBy = "recruteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibiliteRecruteur> disponibilites = new ArrayList<>();
    @OneToMany(mappedBy = "recruteur")
    private List<Evaluation> evaluationsFaites;
    public Recruteur() {
        // Constructeur vide requis par JPA
    }

    public Recruteur(int id, String nom, String email, String motDePasse, Role role) {
        super(id, nom, email, motDePasse, role);
    }

    // ➕ Ajouter une disponibilité
    public void ajouterDisponibilite(DisponibiliteRecruteur disponibilite) {
        disponibilites.add(disponibilite);
        disponibilite.setRecruteur(this);
    }

    // ➖ Supprimer une disponibilité
    public void supprimerDisponibilite(DisponibiliteRecruteur disponibilite) {
        disponibilites.remove(disponibilite);
        disponibilite.setRecruteur(null);
    }
}
