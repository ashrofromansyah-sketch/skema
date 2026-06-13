package com.smaek.smaek;

import com.smaek.smaek.model.Disiplin;
import com.smaek.smaek.model.Anggota;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/disiplin")
public class DisiplinController {

    static List<Disiplin> database = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong(1);

    private List<Anggota> getAnggotaList() {
        return AnggotaController.database;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String status,
                        Model model) {
        List<Disiplin> hasil = new ArrayList<>(database);
        if (q != null && !q.isEmpty()) {
            String kw = q.toLowerCase();
            hasil.removeIf(d -> !d.getNama().toLowerCase().contains(kw));
        }
        if (status != null && !status.isEmpty()) {
            hasil.removeIf(d -> !d.getStatus().equals(status));
        }
        model.addAttribute("disiplinList", hasil);
        model.addAttribute("anggotaList", getAnggotaList());
        model.addAttribute("q", q);
        model.addAttribute("status", status);
        return "disiplin";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Disiplin disiplin, RedirectAttributes ra) {
        getAnggotaList().stream()
            .filter(a -> a.getId().equals(disiplin.getAnggotaId()))
            .findFirst()
            .ifPresent(a -> { disiplin.setNama(a.getNama()); disiplin.setKelas(a.getKelas()); });

        if (disiplin.getId() != null) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).getId().equals(disiplin.getId())) {
                    database.set(i, disiplin); break;
                }
            }
            ra.addFlashAttribute("successMsg", "Data disiplin berhasil diperbarui!");
        } else {
            disiplin.setId(idCounter.getAndIncrement());
            database.add(disiplin);
            ra.addFlashAttribute("successMsg", "Data disiplin berhasil ditambahkan!");
        }
        return "redirect:/disiplin";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        database.removeIf(d -> d.getId().equals(id));
        ra.addFlashAttribute("successMsg", "Data disiplin berhasil dihapus!");
        return "redirect:/disiplin";
    }
}
