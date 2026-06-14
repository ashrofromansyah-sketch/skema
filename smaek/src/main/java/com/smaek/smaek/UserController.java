package com.smaek.smaek;

import com.smaek.smaek.model.User;
import com.smaek.smaek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userList", userRepo.findAll());
        return "users";
    }

    @PostMapping("/simpan")
    public String simpan(@ModelAttribute User user,
                         @RequestParam String passwordBaru,
                         RedirectAttributes ra) {
        if (user.getId() != null) {
            User existing = userRepo.findById(user.getId()).orElse(null);
            if (existing != null) {
                existing.setNamaLengkap(user.getNamaLengkap());
                existing.setRole(user.getRole());
                existing.setAktif(user.isAktif());
                if (!passwordBaru.isEmpty()) {
                    existing.setPassword(passwordEncoder.encode(passwordBaru));
                }
                userRepo.save(existing);
            }
        } else {
            if (userRepo.existsByUsername(user.getUsername())) {
                ra.addFlashAttribute("errorMsg", "Username sudah dipakai!");
                return "redirect:/admin/users";
            }
            user.setPassword(passwordEncoder.encode(passwordBaru));
            userRepo.save(user);
        }
        ra.addFlashAttribute("successMsg", "User berhasil disimpan!");
        return "redirect:/admin/users";
    }

    @PostMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes ra) {
        userRepo.deleteById(id);
        ra.addFlashAttribute("successMsg", "User berhasil dihapus!");
        return "redirect:/admin/users";
    }
}
