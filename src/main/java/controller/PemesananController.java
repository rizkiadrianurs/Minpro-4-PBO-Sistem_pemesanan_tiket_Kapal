/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.KapalDAO;
import DAO.PemesananDAO;
import java.util.List;
import model.Kapal;
import model.Pemesanan;
import view.PemesananView;

/**
 *
 * @author ACER
 */
public class PemesananController {

    private final PemesananDAO pemesananDAO = new PemesananDAO();
    private final KapalDAO kapalDAO = new KapalDAO();
    private final PemesananView view = new PemesananView();

    public void jalankanProgram() {
        int pilihan;

        do {
            view.tampilkanMenu();
            pilihan = view.inputPilihanMenu();

            switch (pilihan) {
                case 1:
                    tambahPemesanan();
                    break;
                case 2:
                    tampilkanPemesanan();
                    break;
                case 3:
                    ubahPemesanan();
                    break;
                case 4:
                    hapusPemesanan();
                    break;
                case 5:
                    view.tampilkanPesan("Program selesai. Terima kasih.");
                    break;
            }
        } while (pilihan != 5);
    }

    private void tampilkanPilihanKapal(List<Kapal> daftar) {
        System.out.println("\nPilihan Kapal:");

        for (int i = 0; i < daftar.size(); i++) {
            Kapal k = daftar.get(i);

            System.out.println((i + 1) + ". "
                    + k.getNamaKapal() + " - "
                    + k.getTujuan() + " - "
                    + k.getJenisKapal() + " - Rp"
                    + k.getHargaTiket());
        }
    }

    private void tambahPemesanan() {
        System.out.println("\n===== TAMBAH PEMESANAN =====");

        String nama = view.inputNama();
        String nik = view.inputNik();
        int umur = view.inputUmur();

        List<Kapal> daftarKapal = kapalDAO.getAll();

        if (daftarKapal.isEmpty()) {
            view.tampilkanPesan("Data kapal belum tersedia.");
            return;
        }

        tampilkanPilihanKapal(daftarKapal);

        int pilihan = view.inputPilihanKapal(daftarKapal.size());
        Kapal kapal = daftarKapal.get(pilihan - 1);

        int jumlah = view.inputJumlahTiket();

        Pemesanan p = new Pemesanan(
                nama,
                nik,
                umur,
                kapal.getNamaKapal(),
                jumlah,
                kapal.getHargaTiket()
        );

        int id = pemesananDAO.tambah(p);

        if (id > 0) {
            view.tampilkanPesan("Pemesanan berhasil ditambahkan.");
            view.tampilkanPesan(
                    "ID Pemesanan: " + String.format("%03d", id));
            view.tampilkanPesan("Total Harga: Rp" + p.getTotalHarga());
        } else {
            view.tampilkanPesan("Pemesanan gagal ditambahkan.");
        }
    }

    private void tampilkanPemesanan() {
        List<Pemesanan> daftar = pemesananDAO.getAll();
        view.tampilkanDaftarPemesanan(daftar);
    }

    private void ubahPemesanan() {
        System.out.println("\n===== UBAH PEMESANAN =====");

        int id = view.inputIdCari();
        List<Pemesanan> daftar = pemesananDAO.getAll();

        for (Pemesanan p : daftar) {
            if (p.getIdPemesanan() == id) {
                String nama = view.inputNama();
                String nik = view.inputNik();
                int umur = view.inputUmur();

                List<Kapal> daftarKapal = kapalDAO.getAll();

                if (daftarKapal.isEmpty()) {
                    view.tampilkanPesan("Data kapal belum tersedia.");
                    return;
                }

                tampilkanPilihanKapal(daftarKapal);

                int pilihan = view.inputPilihanKapalBaru(
                        daftarKapal.size());

                Kapal kapal = daftarKapal.get(pilihan - 1);

                int jumlah = view.inputJumlahTiketBaru();

                p.setNamaPenumpang(nama);
                p.setNik(nik);
                p.setUmur(umur);
                p.setNamaKapal(kapal.getNamaKapal());
                p.setHargaTiket(kapal.getHargaTiket());
                p.setJumlahTiket(jumlah);

                if (pemesananDAO.ubah(p)) {
                    view.tampilkanPesan("Data berhasil diubah.");
                } else {
                    view.tampilkanPesan("Data gagal diubah.");
                }

                return;
            }
        }

        view.tampilkanPesan("ID Pemesanan tidak ditemukan.");
    }

    private void hapusPemesanan() {
        System.out.println("\n===== HAPUS PEMESANAN =====");

        int id = view.inputIdCari();
        List<Pemesanan> daftar = pemesananDAO.getAll();

        for (Pemesanan p : daftar) {
            if (p.getIdPemesanan() == id) {
                view.tampilkanPesan(
                        "Nama Penumpang: " + p.getNamaPenumpang());

                int konfirmasi = view.inputKonfirmasiHapus();

                if (konfirmasi == 1) {
                    if (pemesananDAO.hapus(id)) {
                        view.tampilkanPesan("Data berhasil dihapus.");
                    } else {
                        view.tampilkanPesan("Data gagal dihapus.");
                    }
                } else {
                    view.tampilkanPesan("Penghapusan dibatalkan.");
                }

                return;
            }
        }

        view.tampilkanPesan("ID Pemesanan tidak ditemukan.");
    }
}
