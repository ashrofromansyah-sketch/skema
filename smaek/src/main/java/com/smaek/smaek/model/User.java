package com.smaek.smaek.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ADMIN, PEMBINA, USER

    private String namaLengkap;
    private boolean aktif = true;

    public User() {}

    public Long getId()                      { return id; }
    public void setId(Long id)               { this.id = id; }
    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword()              { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole()                  { return role; }
    public void setRole(String role)         { this.role = role; }
    public String getNamaLengkap()           { return namaLengkap; }
    public void setNamaLengkap(String nama)  { this.namaLengkap = nama; }
    public boolean isAktif()                 { return aktif; }
    public void setAktif(boolean aktif)      { this.aktif = aktif; }
}
