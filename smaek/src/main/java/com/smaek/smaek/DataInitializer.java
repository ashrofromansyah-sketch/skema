package com.smaek.smaek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.smaek.smaek.model.User;
import com.smaek.smaek.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (!userRepo.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin2026"));
            admin.setRole("ANGGOTA");
            admin.setNamaLengkap("Administrator");
            admin.setAktif(true);
            userRepo.save(admin);
            System.out.println(" Admin default dibuat: username=admin, password=admin123");
        }

        if (!userRepo.existsByUsername("pembina")) {
            User pembina = new User();
            pembina.setUsername("pembina");
            pembina.setPassword(passwordEncoder.encode("pembina2026"));
            pembina.setRole("PEMBINA");
            pembina.setNamaLengkap("Pembina Ekskul");
            pembina.setAktif(true);
            userRepo.save(pembina);
            System.out.println(" Pembina default dibuat: username=pembina, password=pembina123");
        }
    }
}
