package com.smaek.smaek.repository;

import com.smaek.smaek.model.Prestasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestasiRepository extends JpaRepository<Prestasi, Long> {
    List<Prestasi> findByJudulContainingIgnoreCaseOrNamaContainingIgnoreCase(String judul, String nama);
    List<Prestasi> findByTingkat(String tingkat);
}
