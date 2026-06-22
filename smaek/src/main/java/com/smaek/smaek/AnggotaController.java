package com.smaek.smaek;

import com.smaek.smaek.model.Anggota;
import com.smaek.smaek.repository.AnggotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    private boolean isEditor(Authentication auth) {
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PEMBINA"));
    }

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String jenis,
                        @RequestParam(required = false) String disiplin,
                        Model model, Authentication auth) {
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
        model.addAttribute("isEditor", isEditor(auth));
        return "anggota";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Anggota anggota, RedirectAttributes ra) {
        repo.save(anggota);
        ra.addFlashAttribute("successMsg", "Anggota berhasil disimpan!");
        return "redirect:/anggota";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Anggota berhasil dihapus!");
        return "redirect:/anggota";
    }
}