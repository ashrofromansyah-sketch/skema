package com.smaek.smaek.repository;

import com.smaek.smaek.model.Pelanggaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PelanggaranRepository extends JpaRepository<Pelanggaran, Long> {
    List<Pelanggaran> findByNamaContainingIgnoreCase(String nama);
    List<Pelanggaran> findByJenis(String jenis);
}
