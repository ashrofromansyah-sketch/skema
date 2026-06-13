package com.smaek.smaek;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smaek.smaek.model.Anggota;

@Controller
@RequestMapping("/anggota")
public class AnggotaController {

    static List<Anggota> database = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong(1);

    @GetMapping
    public String index(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String jenis,
            @RequestParam(required = false) String disiplin,
            Model model) {

        List<Anggota> hasil = new ArrayList<>(database);

        if (q != null && !q.isEmpty()) {
            String keyword = q.toLowerCase();
            hasil.removeIf(a ->
                !a.getNama().toLowerCase().contains(keyword) &&
                !a.getKelas().toLowerCase().contains(keyword)
            );
        }
        if (jenis != null && !jenis.isEmpty()) {
            hasil.removeIf(a -> !a.getJenis().equals(jenis));
        }
        if (disiplin != null && !disiplin.isEmpty()) {
            hasil.removeIf(a -> !a.getDisiplin().equals(disiplin));
        }

        model.addAttribute("anggotaList", hasil);
        model.addAttribute("q", q);
        model.addAttribute("jenis", jenis);
        model.addAttribute("disiplin", disiplin);
        return "anggota";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Anggota anggota,
                         RedirectAttributes ra) {
        if (anggota.getId() != null) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).getId().equals(anggota.getId())) {
                    database.set(i, anggota);
                    break;
                }
            }
            ra.addFlashAttribute("successMsg", "Anggota berhasil diperbarui!");
        } else {
            anggota.setId(idCounter.getAndIncrement());
            database.add(anggota);
            ra.addFlashAttribute("successMsg", "Anggota berhasil ditambahkan!");
        }
        return "redirect:/anggota";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        database.removeIf(a -> a.getId().equals(id));
        ra.addFlashAttribute("successMsg", "Anggota berhasil dihapus!");
        return "redirect:/anggota";
    }
}