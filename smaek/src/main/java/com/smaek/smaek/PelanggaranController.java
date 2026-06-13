package com.smaek.smaek;

import com.smaek.smaek.model.Pelanggaran;
import com.smaek.smaek.model.Anggota;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/pelanggaran")
public class PelanggaranController {

    static List<Pelanggaran> database = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong(1);

    private List<Anggota> getAnggotaList() {
        return AnggotaController.database;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) String jenis,
                        Model model) {
        List<Pelanggaran> hasil = new ArrayList<>(database);
        if (q != null && !q.isEmpty()) {
            String kw = q.toLowerCase();
            hasil.removeIf(p -> !p.getNama().toLowerCase().contains(kw));
        }
        if (jenis != null && !jenis.isEmpty()) {
            hasil.removeIf(p -> !p.getJenis().equals(jenis));
        }
        model.addAttribute("pelanggaranList", hasil);
        model.addAttribute("anggotaList", getAnggotaList());
        model.addAttribute("q", q);
        model.addAttribute("jenis", jenis);
        return "pelanggaran";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Pelanggaran pelanggaran, RedirectAttributes ra) {
        getAnggotaList().stream()
            .filter(a -> a.getId().equals(pelanggaran.getAnggotaId()))
            .findFirst()
            .ifPresent(a -> { pelanggaran.setNama(a.getNama()); pelanggaran.setKelas(a.getKelas()); });

        if (pelanggaran.getId() != null) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).getId().equals(pelanggaran.getId())) {
                    database.set(i, pelanggaran); break;
                }
            }
            ra.addFlashAttribute("successMsg", "Pelanggaran berhasil diperbarui!");
        } else {
            pelanggaran.setId(idCounter.getAndIncrement());
            database.add(pelanggaran);
            ra.addFlashAttribute("successMsg", "Pelanggaran berhasil ditambahkan!");
        }
        return "redirect:/pelanggaran";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        database.removeIf(p -> p.getId().equals(id));
        ra.addFlashAttribute("successMsg", "Pelanggaran berhasil dihapus!");
        return "redirect:/pelanggaran";
    }
}
