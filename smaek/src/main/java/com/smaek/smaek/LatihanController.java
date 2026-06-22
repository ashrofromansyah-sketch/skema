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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smaek.smaek.model.Latihan;
import com.smaek.smaek.repository.LatihanRepository;

@Controller
public class LatihanController {

    @Autowired private LatihanRepository repo;

    private boolean isEditor(Authentication auth) {
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PEMBINA"));
    }

    @GetMapping("/latihan")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String status,
                        Model model, Authentication auth) {
        List<Latihan> hasil;
        if (q != null && !q.isEmpty()) hasil = repo.findByJudulContainingIgnoreCase(q);
        else if (status != null && !status.isEmpty()) hasil = repo.findByStatus(status);
        else hasil = repo.findAll();
        model.addAttribute("latihanList", hasil);
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        model.addAttribute("isEditor", isEditor(auth));
        return "latihan";
    }

    @GetMapping("/riwayat")
    public String riwayat(Model model, Authentication auth) {
        model.addAttribute("latihanList", repo.findAll());
        model.addAttribute("isEditor", isEditor(auth));
        return "riwayat";
    }

    @PostMapping("/latihan/simpan")
    public String simpan(@ModelAttribute Latihan latihan, RedirectAttributes ra) {
        repo.save(latihan);
        ra.addFlashAttribute("successMsg", "Catatan latihan berhasil ditambahkan!");
        return "redirect:/latihan";
    }

    @PostMapping("/latihan/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("successMsg", "Catatan latihan berhasil dihapus!");
        return "redirect:/latihan";
    }
}