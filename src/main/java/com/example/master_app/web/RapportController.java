package com.example.master_app.web;

import com.example.master_app.entities.Rapport;
import com.example.master_app.services.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping
    public String getAllRapports(Model model) {
        List<Rapport> rapports = rapportService.getAllRapports();
        model.addAttribute("rapports", rapports); // Correction : utiliser "rapports" au lieu de "rapport"
        return "rapport/list";
    }

    @GetMapping("/new")
    public String showRapportForm(Model model) {
        model.addAttribute("rapport", new Rapport());
        return "rapport/form";
    }

    @PostMapping
    public String createRapport(@ModelAttribute Rapport s, Model model) {
        try {
            rapportService.createRapport(s);
            return "redirect:/rapport";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création : " + e.getMessage());
            return "rapport/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRapport(@PathVariable int id) {
        rapportService.deleteRapport(id);
        return "redirect:/rapport";
    }

    @GetMapping("/generate/{id}/pdf")
    public String generatePDF(@PathVariable int id, Model model) throws Exception {
        File file = rapportService.genererPDF(id);
        model.addAttribute("filePath", file.getAbsolutePath());
        return "rapport/generate";
    }

    @GetMapping("/generate/{id}/excel")
    public String exportExcel(@PathVariable int id, Model model) throws Exception {
        File file = rapportService.exporterExcel(id);
        model.addAttribute("filePath", file.getAbsolutePath());
        return "rapport/generate";
    }
}