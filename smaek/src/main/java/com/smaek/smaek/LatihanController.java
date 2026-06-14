package com.smaek.smaek;

import com.smaek.smaek.model.Latihan;
import com.smaek.smaek.repository.LatihanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class LatihanController {

    @Autowired
    private LatihanRepository repo;

    @GetMapping("/latihan")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String status,
                        Model model) {
        List<Latihan> hasil;
        if (q != null && !q.isEmpty()) {
            hasil = repo.findByJudulContainingIgnoreCase(q);
        } else if (status != null && !status.isEmpty()) {
            hasil = repo.findByStatus(status);
        } else {
            hasil = repo.findAll();
        }
        model.addAttribute("latihanList", hasil);
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        return "latihan";
    }

    @GetMapping("/riwayat")
    public String riwayat(Model model) {
        model.addAttribute("latihanList", repo.findAll());
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