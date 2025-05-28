package com.example.master_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "evaluation")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String commentaire;

    private double note;
    private LocalDate dateEvaluation;

    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;


}
