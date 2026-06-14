package com.smaek.smaek.repository;

import com.smaek.smaek.model.Latihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LatihanRepository extends JpaRepository<Latihan, Long> {
    List<Latihan> findByJudulContainingIgnoreCase(String judul);
    List<Latihan> findByStatus(String status);
}
