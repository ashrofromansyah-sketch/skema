package com.smaek.smaek;

import com.smaek.smaek.model.Anggota;
import com.smaek.smaek.model.Prestasi;
import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.PrestasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/prestasi")
public class PrestasiController {

    @Autowired
    private PrestasiRepository repo;

    @Autowired
    private AnggotaRepository anggotaRepo;

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String tingkat,
                        Model model) {
        List<Prestasi> hasil;
        if (q != null && !q.isEmpty()) {
            hasil = repo.findByJudulContainingIgnoreCaseOrNamaContainingIgnoreCase(q, q);
        } else if (tingkat != null && !tingkat.isEmpty()) {
            hasil = repo.findByTingkat(tingkat);
        } else {
            hasil = repo.findAll();
        }
        int totalPoin = repo.findAll().stream().mapToInt(Prestasi::getPoin).sum();
        model.addAttribute("prestasiList", hasil);
        model.addAttribute("anggotaList", anggotaRepo.findAll());
        model.addAttribute("totalPoin", totalPoin);
        model.addAttribute("q", q);
        model.addAttribute("tingkat", tingkat);
        return "prestasi";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Prestasi prestasi, RedirectAttributes ra) {
        anggotaRepo.findById(prestasi.getAnggotaId()).ifPresent(a -> prestasi.setNama(a.getNama()));
        repo.save(prestasi);
        ra.addFlashAttribute("successMsg",
            prestasi.getId() != null ? "Prestasi berhasil diperbarui!" : "Prestasi berhasil ditambahkan!");
        return "redirect:/prestasi";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Prestasi berhasil dihapus!");
        return "redirect:/prestasi";
    }
}