package com.smaek.smaek;

import com.smaek.smaek.model.Disiplin;
import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.DisiplinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/disiplin")
public class DisiplinController {

    @Autowired
    private DisiplinRepository repo;

    @Autowired
    private AnggotaRepository anggotaRepo;

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String status,
                        Model model) {
        List<Disiplin> hasil;
        if (q != null && !q.isEmpty()) {
            hasil = repo.findByNamaContainingIgnoreCase(q);
        } else if (status != null && !status.isEmpty()) {
            hasil = repo.findByStatus(status);
        } else {
            hasil = repo.findAll();
        }
        model.addAttribute("disiplinList", hasil);
        model.addAttribute("anggotaList", anggotaRepo.findAll());
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        return "disiplin";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Disiplin disiplin, RedirectAttributes ra) {
        anggotaRepo.findById(disiplin.getAnggotaId()).ifPresent(a -> {
            disiplin.setNama(a.getNama());
            disiplin.setKelas(a.getKelas());
        });
        repo.save(disiplin);
        ra.addFlashAttribute("successMsg", "Data disiplin berhasil ditambahkan!");
        return "redirect:/disiplin";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Data disiplin berhasil dihapus!");
        return "redirect:/disiplin";
    }
}