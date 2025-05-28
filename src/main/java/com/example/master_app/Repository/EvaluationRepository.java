package com.example.master_app.Repository;

import com.example.master_app.entities.Evaluation;
import com.example.master_app.entities.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByRecruteur(Recruteur recruteur);
}
