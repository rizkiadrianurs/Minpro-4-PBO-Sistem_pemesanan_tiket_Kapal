/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import java.util.Scanner;
import model.Pemesanan;
/**
 *
 * @author ACER
 */
public class PemesananView {

    private final Scanner input = new Scanner(System.in);

    public void tampilkanMenu() {
        System.out.println("\n===================================");
        System.out.println("    SISTEM PEMESANAN TIKET KAPAL");
        System.out.println("===================================");
        System.out.println("1. Tambah Pemesanan");
        System.out.println("2. Tampilkan Pemesanan");
        System.out.println("3. Ubah Pemesanan");
        System.out.println("4. Hapus Pemesanan");
        System.out.println("5. Keluar");
        System.out.println("===================================");
    }

    public int inputPilihanMenu() {
        while (true) {
            System.out.print("Pilih menu: ");

            try {
                int pilihan = Integer.parseInt(input.nextLine());

                if (pilihan >= 1 && pilihan <= 5) {
                    return pilihan;
                }

                System.out.println("Pilihan harus 1 sampai 5.");

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public String inputNama() {
        while (true) {
            System.out.print("Nama Penumpang: ");
            String nama = input.nextLine().trim();

            if (!nama.isEmpty()) {
                return nama;
            }

            System.out.println("Nama tidak boleh kosong.");
        }
    }

    public String inputNik() {
        while (true) {
            System.out.print("NIK: ");
            String nik = input.nextLine().trim();

            if (nik.length() == 16) {
                boolean angka = true;

                for (int i = 0; i < nik.length(); i++) {
                    if (!Character.isDigit(nik.charAt(i))) {
                        angka = false;
                        break;
                    }
                }

                if (angka) {
                    return nik;
                }
            }

            System.out.println("NIK harus terdiri dari 16 digit.");
        }
    }

    public int inputUmur() {
        while (true) {
            System.out.print("Umur: ");

            try {
                int umur = Integer.parseInt(input.nextLine());

                if (umur > 0 && umur <= 120) {
                    return umur;
                }

                System.out.println("Umur harus 1 sampai 120.");

            } catch (NumberFormatException e) {
                System.out.println("Umur harus berupa angka.");
            }
        }
    }

    public int inputPilihanKapal(int jumlahKapal) {
        while (true) {
            System.out.print("Pilih kapal (1-" + jumlahKapal + "): ");

            try {
                int pilihan = Integer.parseInt(input.nextLine());

                if (pilihan >= 1 && pilihan <= jumlahKapal) {
                    return pilihan;
                }

                System.out.println("Pilihan kapal tidak tersedia.");

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public int inputPilihanKapalBaru(int jumlahKapal) {
        return inputPilihanKapal(jumlahKapal);
    }

    public int inputJumlahTiket() {
        while (true) {
            System.out.print("Jumlah Tiket: ");

            try {
                int jumlah = Integer.parseInt(input.nextLine());

                if (jumlah > 0) {
                    return jumlah;
                }

                System.out.println("Jumlah tiket harus lebih dari 0.");

            } catch (NumberFormatException e) {
                System.out.println("Jumlah tiket harus berupa angka.");
            }
        }
    }

    public int inputJumlahTiketBaru() {
        return inputJumlahTiket();
    }

    public int inputIdCari() {
        while (true) {
            System.out.print("Masukkan ID Pemesanan: ");

            try {
                int id = Integer.parseInt(input.nextLine());

                if (id > 0) {
                    return id;
                }

                System.out.println("ID harus lebih dari 0.");

            } catch (NumberFormatException e) {
                System.out.println("ID harus berupa angka.");
            }
        }
    }

    public int inputKonfirmasiHapus() {
        while (true) {
            System.out.println("1. Ya, hapus data");
            System.out.println("2. Tidak, batalkan");
            System.out.print("Pilih: ");

            try {
                int pilihan = Integer.parseInt(input.nextLine());

                if (pilihan == 1 || pilihan == 2) {
                    return pilihan;
                }

                System.out.println("Pilih 1 atau 2.");

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }

    public void tampilkanDaftarPemesanan(List<Pemesanan> daftar) {
        System.out.println("\n===== DAFTAR PEMESANAN =====");

        if (daftar.isEmpty()) {
            System.out.println("Belum ada data pemesanan.");
            return;
        }

        for (int i = 0; i < daftar.size(); i++) {
            Pemesanan p = daftar.get(i);

            System.out.println("\nData ke-" + (i + 1));
            System.out.println("-----------------------------------");
            System.out.printf("ID Pemesanan   : %03d%n",
                    p.getIdPemesanan());
            System.out.println("Nama Penumpang : "
                    + p.getNamaPenumpang());
            System.out.println("NIK            : " + p.getNik());
            System.out.println("Umur           : " + p.getUmur());
            System.out.println("Nama Kapal     : " + p.getNamaKapal());
            System.out.println("Jumlah Tiket   : " + p.getJumlahTiket());
            System.out.println("Harga Tiket    : Rp" + p.getHargaTiket());
            System.out.println("Total Harga    : Rp" + p.getTotalHarga());
            System.out.println("-----------------------------------");
        }
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}
