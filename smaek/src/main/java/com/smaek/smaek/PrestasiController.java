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
import com.smaek.smaek.model.Prestasi;

@Controller
@RequestMapping("/prestasi")
public class PrestasiController {

    static List<Prestasi> database = new ArrayList<>();
    private static AtomicLong idCounter = new AtomicLong(1);

    // Referensi ke data anggota (shared dengan AnggotaController)
    private List<Anggota> getAnggotaList() {
        return AnggotaController.database;
    }

    // ── Tampilkan halaman prestasi ──
    @GetMapping
    public String index(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String tingkat,
            Model model) {

        List<Prestasi> hasil = new ArrayList<>(database);

        if (q != null && !q.isEmpty()) {
            String keyword = q.toLowerCase();
            hasil.removeIf(p ->
                !p.getJudul().toLowerCase().contains(keyword) &&
                !p.getNama().toLowerCase().contains(keyword)
            );
        }
        if (tingkat != null && !tingkat.isEmpty()) {
            hasil.removeIf(p -> !p.getTingkat().equals(tingkat));
        }

        // Hitung total poin
        int totalPoin = database.stream().mapToInt(Prestasi::getPoin).sum();

        model.addAttribute("prestasiList", hasil);
        model.addAttribute("anggotaList", getAnggotaList());
        model.addAttribute("totalPoin", totalPoin);
        model.addAttribute("q", q);
        model.addAttribute("tingkat", tingkat);
        return "prestasi";
    }

    // ── Simpan (tambah atau edit) ──
    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Prestasi prestasi,
                         RedirectAttributes ra) {
        // Ambil nama anggota dari ID
        getAnggotaList().stream()
            .filter(a -> a.getId().equals(prestasi.getAnggotaId()))
            .findFirst()
            .ifPresent(a -> prestasi.setNama(a.getNama()));

        if (prestasi.getId() != null) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).getId().equals(prestasi.getId())) {
                    database.set(i, prestasi);
                    break;
                }
            }
            ra.addFlashAttribute("successMsg", "Prestasi berhasil diperbarui!");
        } else {
            prestasi.setId(idCounter.getAndIncrement());
            database.add(prestasi);
            ra.addFlashAttribute("successMsg", "Prestasi berhasil ditambahkan!");
        }
        return "redirect:/prestasi";
    }

    // ── Hapus ──
    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        database.removeIf(p -> p.getId().equals(id));
        ra.addFlashAttribute("successMsg", "Prestasi berhasil dihapus!");
        return "redirect:/prestasi";
    }
}