package com.smaek.smaek;

import com.smaek.smaek.model.Anggota;
import com.smaek.smaek.repository.AnggotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/anggota")
public class AnggotaController {

    @Autowired
    private AnggotaRepository repo;

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String jenis,
                        @RequestParam(required = false) String disiplin,
                        Model model) {
        List<Anggota> hasil;
        if (q != null && !q.isEmpty()) {
            hasil = repo.findByNamaContainingIgnoreCaseOrKelasContainingIgnoreCase(q, q);
        } else if (jenis != null && !jenis.isEmpty() && disiplin != null && !disiplin.isEmpty()) {
            hasil = repo.findByJenisAndDisiplin(jenis, disiplin);
        } else if (jenis != null && !jenis.isEmpty()) {
            hasil = repo.findByJenis(jenis);
        } else if (disiplin != null && !disiplin.isEmpty()) {
            hasil = repo.findByDisiplin(disiplin);
        } else {
            hasil = repo.findAll();
        }
        model.addAttribute("anggotaList", hasil);
        model.addAttribute("q", q);
        model.addAttribute("jenis", jenis);
        model.addAttribute("disiplin", disiplin);
        return "anggota";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Anggota anggota, RedirectAttributes ra) {
        repo.save(anggota);
        ra.addFlashAttribute("successMsg",
            anggota.getId() != null ? "Anggota berhasil diperbarui!" : "Anggota berhasil ditambahkan!");
        return "redirect:/anggota";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Anggota berhasil dihapus!");
        return "redirect:/anggota";
    }
}