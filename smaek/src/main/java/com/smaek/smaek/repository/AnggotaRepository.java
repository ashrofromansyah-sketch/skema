package com.smaek.smaek.repository;

import com.smaek.smaek.model.Anggota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, Long> {
    List<Anggota> findByNamaContainingIgnoreCaseOrKelasContainingIgnoreCase(String nama, String kelas);
    List<Anggota> findByJenis(String jenis);
    List<Anggota> findByDisiplin(String disiplin);
    List<Anggota> findByJenisAndDisiplin(String jenis, String disiplin);
}
