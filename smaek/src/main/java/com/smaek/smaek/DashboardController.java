package com.smaek.smaek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.DisiplinRepository;
import com.smaek.smaek.repository.LatihanRepository;
import com.smaek.smaek.repository.PelanggaranRepository;
import com.smaek.smaek.repository.PrestasiRepository;

@Controller
public class DashboardController {

    @Autowired private AnggotaRepository anggotaRepo;
    @Autowired private PrestasiRepository prestasiRepo;
    @Autowired private DisiplinRepository disiplinRepo;
    @Autowired private PelanggaranRepository pelanggaranRepo;
    @Autowired private LatihanRepository latihanRepo;

    private boolean isEditor(Authentication auth) {
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PEMBINA"));
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("totalAnggota",     anggotaRepo.count());
        model.addAttribute("totalPrestasi",    prestasiRepo.count());
        model.addAttribute("totalDisiplin",    disiplinRepo.count());
        model.addAttribute("totalPelanggaran", pelanggaranRepo.count());
        model.addAttribute("totalLatihan",     latihanRepo.count());
        model.addAttribute("isEditor",         isEditor(auth));
        return "dashboard";
    }

    @GetMapping("/laporan")
    public String laporan(Model model, Authentication auth) {
        model.addAttribute("anggotaList",     anggotaRepo.findAll());
        model.addAttribute("prestasiList",    prestasiRepo.findAll());
        model.addAttribute("disiplinList",    disiplinRepo.findAll());
        model.addAttribute("pelanggaranList", pelanggaranRepo.findAll());
        model.addAttribute("isEditor",        isEditor(auth));
        return "laporan";
    }
}