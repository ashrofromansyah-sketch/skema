package com.smaek.smaek.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "anggota")
public class Anggota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String kelas;

    @Column(nullable = false)
    private String jenis;

    @Column(nullable = false)
    private String disiplin;

    @Column(nullable = false)
    private String bonus;

    @Column(name = "bergabung")
    private LocalDate bergabung;

    public Anggota() {}

    public Long getId()                   { return id; }
    public void setId(Long id)            { this.id = id; }
    public String getNama()               { return nama; }
    public void setNama(String nama)      { this.nama = nama; }
    public String getKelas()              { return kelas; }
    public void setKelas(String kelas)    { this.kelas = kelas; }
    public String getJenis()              { return jenis; }
    public void setJenis(String jenis)    { this.jenis = jenis; }
    public String getDisiplin()           { return disiplin; }
    public void setDisiplin(String d)     { this.disiplin = d; }
    public String getBonus()              { return bonus; }
    public void setBonus(String bonus)    { this.bonus = bonus; }
    public LocalDate getBergabung()       { return bergabung; }
    public void setBergabung(LocalDate b) { this.bergabung = b; }
}