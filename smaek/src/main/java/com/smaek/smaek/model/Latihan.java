package com.smaek.smaek.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "latihan")
public class Latihan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String judul;
    private LocalDate tanggal;
    private String lokasi;
    private int durasi;
    private String catatan;
    private String status;

    public Latihan() {}

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }
    public String getJudul()             { return judul; }
    public void setJudul(String judul)   { this.judul = judul; }
    public LocalDate getTanggal()        { return tanggal; }
    public void setTanggal(LocalDate t)  { this.tanggal = t; }
    public String getLokasi()            { return lokasi; }
    public void setLokasi(String l)      { this.lokasi = l; }
    public int getDurasi()               { return durasi; }
    public void setDurasi(int d)         { this.durasi = d; }
    public String getCatatan()           { return catatan; }
    public void setCatatan(String c)     { this.catatan = c; }
    public String getStatus()            { return status; }
    public void setStatus(String s)      { this.status = s; }
}