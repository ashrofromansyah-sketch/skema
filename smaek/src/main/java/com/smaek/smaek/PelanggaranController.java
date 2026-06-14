package com.smaek.smaek;

import com.smaek.smaek.model.Pelanggaran;
import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.PelanggaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/pelanggaran")
public class PelanggaranController {

    @Autowired
    private PelanggaranRepository repo;

    @Autowired
    private AnggotaRepository anggotaRepo;

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String jenis,
                        Model model) {
        List<Pelanggaran> hasil;
        if (q != null && !q.isEmpty()) {
            hasil = repo.findByNamaContainingIgnoreCase(q);
        } else if (jenis != null && !jenis.isEmpty()) {
            hasil = repo.findByJenis(jenis);
        } else {
            hasil = repo.findAll();
        }
        model.addAttribute("pelanggaranList", hasil);
        model.addAttribute("anggotaList", anggotaRepo.findAll());
        model.addAttribute("q", q);
        model.addAttribute("jenis", jenis);
        return "pelanggaran";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Pelanggaran pelanggaran, RedirectAttributes ra) {
        anggotaRepo.findById(pelanggaran.getAnggotaId()).ifPresent(a -> {
            pelanggaran.setNama(a.getNama());
            pelanggaran.setKelas(a.getKelas());
        });
        repo.save(pelanggaran);
        ra.addFlashAttribute("successMsg", "Pelanggaran berhasil ditambahkan!");
        return "redirect:/pelanggaran";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Pelanggaran berhasil dihapus!");
        return "redirect:/pelanggaran";
    }
}