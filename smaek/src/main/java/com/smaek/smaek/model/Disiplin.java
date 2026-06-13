package com.smaek.smaek.model;

import java.time.LocalDate;

public class Disiplin {
    private Long id;
    private Long anggotaId;
    private String nama;
    private String kelas;
    private String status;   // Hadir, Izin, Sakit, Alpa
    private LocalDate tanggal;
    private String keterangan;

    public Disiplin() {}

    public Long getId()                    { return id; }
    public void setId(Long id)             { this.id = id; }
    public Long getAnggotaId()             { return anggotaId; }
    public void setAnggotaId(Long a)       { this.anggotaId = a; }
    public String getNama()                { return nama; }
    public void setNama(String nama)       { this.nama = nama; }
    public String getKelas()               { return kelas; }
    public void setKelas(String kelas)     { this.kelas = kelas; }
    public String getStatus()              { return status; }
    public void setStatus(String status)   { this.status = status; }
    public LocalDate getTanggal()          { return tanggal; }
    public void setTanggal(LocalDate t)    { this.tanggal = t; }
    public String getKeterangan()          { return keterangan; }
    public void setKeterangan(String k)    { this.keterangan = k; }
}
