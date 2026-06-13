package com.smaek.smaek;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalAnggota",    AnggotaController.database.size());
        model.addAttribute("totalPrestasi",   PrestasiController.database.size());
        model.addAttribute("totalDisiplin",   DisiplinController.database.size());
        model.addAttribute("totalPelanggaran",PelanggaranController.database.size());
        model.addAttribute("totalLatihan",    LatihanController.database.size());
        return "dashboard";
    }

    @GetMapping("/laporan")
    public String laporan(Model model) {
        model.addAttribute("anggotaList",     AnggotaController.database);
        model.addAttribute("prestasiList",    PrestasiController.database);
        model.addAttribute("disiplinList",    DisiplinController.database);
        model.addAttribute("pelanggaranList", PelanggaranController.database);
        return "laporan";
    }
}
