package com.example.master_app.web;

import com.example.master_app.Repository.EvaluationRepository;
import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.entities.Evaluation;
import com.example.master_app.entities.Recruteur;
import com.example.master_app.entities.Utilisateur;
import com.example.master_app.enumes.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recruteur/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // ✅ Rediriger vers la liste
    @GetMapping("")
    public String redirectionVersListeEvaluations() {
        return "redirect:/recruteur/evaluations/liste";
    }

    // ✅ Formulaire d’ajout
    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("evaluation", new Evaluation());
        model.addAttribute("candidats", utilisateurRepository.findByRole(Role.CANDIDAT));
        return "recruteur/evaluation_form";
    }

    // ✅ Enregistrement
    @PostMapping("/ajouter")
    public String enregistrerEvaluation(@ModelAttribute("evaluation") Evaluation evaluation, Principal principal) {
        Optional<Utilisateur> optionalRecruteur = utilisateurRepository.findByEmail(principal.getName());

        if (optionalRecruteur.isPresent()) {
            Utilisateur utilisateur = optionalRecruteur.get();

            if (utilisateur.getRole() == Role.RECRUTEUR) {
                Recruteur recruteur = (Recruteur) utilisateur;
                evaluation.setRecruteur(recruteur);
                evaluation.setDateEvaluation(LocalDate.now());
                evaluationRepository.save(evaluation);
                return "redirect:/recruteur/evaluations/liste";
            } else {
                return "error/403";
            }
        } else {
            return "error/404";
        }
    }

    // ✅ Liste des évaluations
    @GetMapping("/liste")
    public String afficherListeEvaluations(Model model, Principal principal) {
        Optional<Utilisateur> optionalRecruteur = utilisateurRepository.findByEmail(principal.getName());

        if (optionalRecruteur.isPresent()) {
            Utilisateur utilisateur = optionalRecruteur.get();

            if (utilisateur.getRole() == Role.RECRUTEUR) {
                Recruteur recruteur = (Recruteur) utilisateur;
                List<Evaluation> evaluations = evaluationRepository.findByRecruteur(recruteur);
                model.addAttribute("evaluations", evaluations);
                return "recruteur/evaluation_liste";
            } else {
                return "error/403";
            }
        } else {
            return "error/404";
        }
    }

    // ✅ Formulaire de modification
    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(id);
        if (optionalEvaluation.isPresent()) {
            model.addAttribute("evaluation", optionalEvaluation.get());
            model.addAttribute("candidats", utilisateurRepository.findByRole(Role.CANDIDAT));
            return "recruteur/evaluation_form"; // même formulaire que l'ajout
        } else {
            return "error/404";
        }
    }

    // ✅ Enregistrement de la modification
    @PostMapping("/modifier")
    public String enregistrerModification(@ModelAttribute("evaluation") Evaluation evaluation) {
        Optional<Evaluation> existingEvalOpt = evaluationRepository.findById(evaluation.getId());

        if (existingEvalOpt.isPresent()) {
            Evaluation existingEval = existingEvalOpt.get();

            // On garde le recruteur et la date d'origine
            evaluation.setRecruteur(existingEval.getRecruteur());
            evaluation.setDateEvaluation(existingEval.getDateEvaluation());

            evaluationRepository.save(evaluation);
            return "redirect:/recruteur/evaluations/liste";
        } else {
            return "error/404";
        }
    }

    // ✅ Suppression d'une évaluation
    @GetMapping("/supprimer/{id}")
    public String supprimerEvaluation(@PathVariable Long id) {
        evaluationRepository.deleteById(id);
        return "redirect:/recruteur/evaluations/liste";
    }
}
