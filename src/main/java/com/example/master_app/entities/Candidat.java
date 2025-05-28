package com.example.master_app.entities;

import com.example.master_app.enumes.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "candidat")
public class Candidat extends Utilisateur {

    // Champ spécifique au candidat
    private String cvPath;
    @OneToMany(mappedBy = "candidat")
    private List<Evaluation> evaluations;
    // Constructeur par défaut
    public Candidat() {
    }

    // Constructeur avec tous les champs
    public Candidat(int id, String nom, String email, String motDePasse, Role role, String cvPath) {
        super(id, nom, email, motDePasse, role);
        this.cvPath = cvPath;
    }

}