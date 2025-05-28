package com.example.master_app.services;

import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse()) // Déjà encodé
                .roles(utilisateur.getRole().name()) // Assure-toi que Role = ENUM("RECRUTEUR", ...)
                .build();
    }
}
