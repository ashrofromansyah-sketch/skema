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

import com.smaek.smaek.model.Prestasi;
import com.smaek.smaek.repository.AnggotaRepository;
import com.smaek.smaek.repository.PrestasiRepository;

@Controller
@RequestMapping("/prestasi")
public class PrestasiController {

    @Autowired private PrestasiRepository repo;
    @Autowired private AnggotaRepository anggotaRepo;

    private boolean isEditor(Authentication auth) {
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PEMBINA"));
    }

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String tingkat,
                        Model model, Authentication auth) {
        List<Prestasi> hasil;
        if (q != null && !q.isEmpty()) hasil = repo.findByJudulContainingIgnoreCaseOrNamaContainingIgnoreCase(q, q);
        else if (tingkat != null && !tingkat.isEmpty()) hasil = repo.findByTingkat(tingkat);
        else hasil = repo.findAll();
        int totalPoin = repo.findAll().stream().mapToInt(Prestasi::getPoin).sum();
        model.addAttribute("prestasiList", hasil);
        model.addAttribute("anggotaList", anggotaRepo.findAll());
        model.addAttribute("totalPoin", totalPoin);
        model.addAttribute("q", q);
        model.addAttribute("tingkat", tingkat);
        model.addAttribute("isEditor", isEditor(auth));
        return "prestasi";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Prestasi prestasi, RedirectAttributes ra) {
        anggotaRepo.findById(prestasi.getAnggotaId()).ifPresent(a -> prestasi.setNama(a.getNama()));
        repo.save(prestasi);
        ra.addFlashAttribute("successMsg", "Prestasi berhasil disimpan!");
        return "redirect:/prestasi";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Prestasi berhasil dihapus!");
        return "redirect:/prestasi";
    }
}