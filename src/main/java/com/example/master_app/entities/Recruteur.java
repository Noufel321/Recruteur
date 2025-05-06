package com.example.master_app.entities;

import com.example.master_app.enumes.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruteur")
public class Recruteur extends Utilisateur {

    // Champ spécifique au recruteur
    private String departement;

    // Constructeur par défaut
    public Recruteur() {
    }

    // Constructeur avec tous les champs
    public Recruteur(int id, String nom, String email, String motDePasse, Role role, String departement) {
        super(id, nom, email, motDePasse, role);
        this.departement = departement;
    }

    // Getters et Setters
    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
}