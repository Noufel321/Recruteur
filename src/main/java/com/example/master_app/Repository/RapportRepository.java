package com.example.master_app.Repository;


import com.example.master_app.entities.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    // Pas de méthode personnalisée pour l'instant, mais tu peux en ajouter si besoin
    // Exemple : List<Rapport> findByDateCreationBetween(LocalDate start, LocalDate end);
}