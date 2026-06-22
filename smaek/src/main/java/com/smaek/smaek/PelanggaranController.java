package com.smaek.smaek;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smaek.smaek.model.Pelanggaran;
import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.PelanggaranRepository;

@Controller
@RequestMapping("/pelanggaran")
public class PelanggaranController {

    @Autowired private PelanggaranRepository repo;
    @Autowired private AnggotaRepository anggotaRepo;

    private boolean isEditor(Authentication auth) {
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PEMBINA"));
    }

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String jenis,
                        Model model, Authentication auth) {
        List<Pelanggaran> hasil;
        if (q != null && !q.isEmpty()) hasil = repo.findByNamaContainingIgnoreCase(q);
        else if (jenis != null && !jenis.isEmpty()) hasil = repo.findByJenis(jenis);
        else hasil = repo.findAll();
        model.addAttribute("pelanggaranList", hasil);
        model.addAttribute("anggotaList", anggotaRepo.findAll());
        model.addAttribute("q", q);
        model.addAttribute("jenis", jenis);
        model.addAttribute("isEditor", isEditor(auth));
        return "pelanggaran";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Pelanggaran p, RedirectAttributes ra) {
        anggotaRepo.findById(p.getAnggotaId()).ifPresent(a -> { p.setNama(a.getNama()); p.setKelas(a.getKelas()); });
        repo.save(p);
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