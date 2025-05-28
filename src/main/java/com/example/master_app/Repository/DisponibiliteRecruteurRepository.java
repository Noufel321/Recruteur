package com.example.master_app.Repository;

import com.example.master_app.entities.DisponibiliteRecruteur;
import com.example.master_app.entities.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibiliteRecruteurRepository extends JpaRepository<DisponibiliteRecruteur, Long> {
    List<DisponibiliteRecruteur> findByRecruteurId(Long recruteurId);
    // Méthode pour récupérer les disponibilités d'un recruteur
    List<DisponibiliteRecruteur> findByRecruteur(Recruteur recruteur);
}
