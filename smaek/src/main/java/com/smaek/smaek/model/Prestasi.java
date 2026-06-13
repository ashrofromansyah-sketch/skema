package com.smaek.smaek.model;

import java.time.LocalDate;

public class Prestasi {

    private Long id;
    private Long anggotaId;
    private String nama;
    private String judul;
    private LocalDate tanggal;
    private String tingkat;
    private int poin;
    private String keterangan;

    public Prestasi() {}

    public Long getId()                      { return id; }
    public void setId(Long id)               { this.id = id; }
    public Long getAnggotaId()               { return anggotaId; }
    public void setAnggotaId(Long anggotaId) { this.anggotaId = anggotaId; }
    public String getNama()                  { return nama; }
    public void setNama(String nama)         { this.nama = nama; }
    public String getJudul()                 { return judul; }
    public void setJudul(String judul)       { this.judul = judul; }
    public LocalDate getTanggal()            { return tanggal; }
    public void setTanggal(LocalDate t)      { this.tanggal = t; }
    public String getTingkat()               { return tingkat; }
    public void setTingkat(String tingkat)   { this.tingkat = tingkat; }
    public int getPoin()                     { return poin; }
    public void setPoin(int poin)            { this.poin = poin; }
    public String getKeterangan()            { return keterangan; }
    public void setKeterangan(String k)      { this.keterangan = k; }
}