package com.smaek.smaek;

import com.smaek.smaek.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired private AnggotaRepository anggotaRepo;
    @Autowired private PrestasiRepository prestasiRepo;
    @Autowired private DisiplinRepository disiplinRepo;
    @Autowired private PelanggaranRepository pelanggaranRepo;
    @Autowired private LatihanRepository latihanRepo;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalAnggota",     anggotaRepo.count());
        model.addAttribute("totalPrestasi",    prestasiRepo.count());
        model.addAttribute("totalDisiplin",    disiplinRepo.count());
        model.addAttribute("totalPelanggaran", pelanggaranRepo.count());
        model.addAttribute("totalLatihan",     latihanRepo.count());
        return "dashboard";
    }

    @GetMapping("/laporan")
    public String laporan(Model model) {
        model.addAttribute("anggotaList",     anggotaRepo.findAll());
        model.addAttribute("prestasiList",    prestasiRepo.findAll());
        model.addAttribute("disiplinList",    disiplinRepo.findAll());
        model.addAttribute("pelanggaranList", pelanggaranRepo.findAll());
        return "laporan";
    }
}