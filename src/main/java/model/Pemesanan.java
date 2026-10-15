/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
/**
 *
 * @author ACER
 */
public class Pemesanan {

    private int idPemesanan;
    private String namaPenumpang;
    private String nik;
    private int umur;
    private String namaKapal;
    private int jumlahTiket;
    private int hargaTiket;

    public Pemesanan(String namaPenumpang, String nik, int umur,
            String namaKapal, int jumlahTiket, int hargaTiket) {

        this.namaPenumpang = namaPenumpang;
        this.nik = nik;
        this.umur = umur;
        this.namaKapal = namaKapal;
        this.jumlahTiket = jumlahTiket;
        this.hargaTiket = hargaTiket;
    }

    public Pemesanan(int idPemesanan, String namaPenumpang,
            String nik, int umur, String namaKapal,
            int jumlahTiket, int hargaTiket) {

        this.idPemesanan = idPemesanan;
        this.namaPenumpang = namaPenumpang;
        this.nik = nik;
        this.umur = umur;
        this.namaKapal = namaKapal;
        this.jumlahTiket = jumlahTiket;
        this.hargaTiket = hargaTiket;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getNamaPenumpang() {
        return namaPenumpang;
    }

    public void setNamaPenumpang(String namaPenumpang) {
        this.namaPenumpang = namaPenumpang;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getNamaKapal() {
        return namaKapal;
    }

    public void setNamaKapal(String namaKapal) {
        this.namaKapal = namaKapal;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public int getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(int hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public int getTotalHarga() {
        return hargaTiket * jumlahTiket;
    }
}
