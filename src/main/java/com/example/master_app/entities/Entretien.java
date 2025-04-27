package com.example.master_app.entities;

import com.example.master_app.enumes.Statut;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "entretien")
@Getter
@Setter
@NoArgsConstructor
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String lieu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;


    @ManyToOne
    @JoinColumn(name = "recruteur_id", nullable = false)
    private Recruteur recruteur;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;

    @OneToMany(mappedBy = "entretien")
    private List<Notification> notifications;

    public void ajouterCompteRendu() {
        // Logique pour ajouter un compte-rendu
    }
}