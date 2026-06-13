package com.smaek.smaek;

import com.smaek.smaek.model.Latihan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class LatihanController {

    static List<Latihan> database = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong(1);

    @GetMapping("/latihan")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String status,
                        Model model) {
        List<Latihan> hasil = new ArrayList<>(database);
        if (q != null && !q.isEmpty()) {
            String kw = q.toLowerCase();
            hasil.removeIf(l -> !l.getJudul().toLowerCase().contains(kw));
        }
        if (status != null && !status.isEmpty()) {
            hasil.removeIf(l -> !l.getStatus().equals(status));
        }
        model.addAttribute("latihanList", hasil);
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        return "latihan";
    }

    @GetMapping("/riwayat")
    public String riwayat(Model model) {
        model.addAttribute("latihanList", database);
        return "riwayat";
    }

    @PostMapping("/latihan/simpan")
    public String simpan(@ModelAttribute Latihan latihan, RedirectAttributes ra) {
        if (latihan.getId() != null) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).getId().equals(latihan.getId())) {
                    database.set(i, latihan); break;
                }
            }
            ra.addFlashAttribute("successMsg", "Catatan latihan berhasil diperbarui!");
        } else {
            latihan.setId(idCounter.getAndIncrement());
            database.add(latihan);
            ra.addFlashAttribute("successMsg", "Catatan latihan berhasil ditambahkan!");
        }
        return "redirect:/latihan";
    }

    @PostMapping("/latihan/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        database.removeIf(l -> l.getId().equals(id));
        ra.addFlashAttribute("successMsg", "Catatan latihan berhasil dihapus!");
        return "redirect:/latihan";
    }
}
