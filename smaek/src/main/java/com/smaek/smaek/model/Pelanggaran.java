package com.smaek.smaek.model;

import java.time.LocalDate;

public class Pelanggaran {
    private Long id;
    private Long anggotaId;
    private String nama;
    private String kelas;
    private String jenis;      // Ringan, Sedang, Berat
    private String deskripsi;
    private LocalDate tanggal;
    private String sanksi;

    public Pelanggaran() {}

    public Long getId()                   { return id; }
    public void setId(Long id)            { this.id = id; }
    public Long getAnggotaId()            { return anggotaId; }
    public void setAnggotaId(Long a)      { this.anggotaId = a; }
    public String getNama()               { return nama; }
    public void setNama(String nama)      { this.nama = nama; }
    public String getKelas()              { return kelas; }
    public void setKelas(String kelas)    { this.kelas = kelas; }
    public String getJenis()              { return jenis; }
    public void setJenis(String jenis)    { this.jenis = jenis; }
    public String getDeskripsi()          { return deskripsi; }
    public void setDeskripsi(String d)    { this.deskripsi = d; }
    public LocalDate getTanggal()         { return tanggal; }
    public void setTanggal(LocalDate t)   { this.tanggal = t; }
    public String getSanksi()             { return sanksi; }
    public void setSanksi(String s)       { this.sanksi = s; }
}
