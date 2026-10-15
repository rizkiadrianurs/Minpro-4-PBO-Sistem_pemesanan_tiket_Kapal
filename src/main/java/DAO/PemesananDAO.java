/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pemesanan;
/**
 *
 * @author ACER
 */
public class PemesananDAO {
    public int tambah(Pemesanan p) {
        String sql = "INSERT INTO pemesanan "
                + "(nama_penumpang, nik, umur, id_kapal, "
                + "jumlah_tiket, total_harga) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {
            int idKapal = cariIdKapal(conn, p.getNamaKapal());

            try (PreparedStatement pstmt = conn.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, p.getNamaPenumpang());
                pstmt.setString(2, p.getNik());
                pstmt.setInt(3, p.getUmur());
                pstmt.setInt(4, idKapal);
                pstmt.setInt(5, p.getJumlahTiket());
                pstmt.setInt(6, p.getTotalHarga());

                int barisBerubah = pstmt.executeUpdate();

                if (barisBerubah > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            System.out.println(
                                    "Pemesanan berhasil ditambahkan!");
                            return rs.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(
                    "Gagal menambah pemesanan - " + e.getMessage());
        }

        return 0;
    }

    public Pemesanan cariPemesananById(int idPemesanan) {
        String sql = "SELECT p.id_pemesanan, p.nama_penumpang, "
                + "p.nik, p.umur, p.jumlah_tiket, "
                + "k.nama_kapal, k.harga_tiket "
                + "FROM pemesanan p "
                + "JOIN kapal k ON p.id_kapal = k.id_kapal "
                + "WHERE p.id_pemesanan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPemesanan);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pemesanan(
                            rs.getInt("id_pemesanan"),
                            rs.getString("nama_penumpang"),
                            rs.getString("nik"),
                            rs.getInt("umur"),
                            rs.getString("nama_kapal"),
                            rs.getInt("jumlah_tiket"),
                            rs.getInt("harga_tiket")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(
                    "Gagal mencari pemesanan - " + e.getMessage());
        }

        return null;
    }

    public List<Pemesanan> getAll() {
        List<Pemesanan> daftar = new ArrayList<>();

        String sql = "SELECT p.id_pemesanan, p.nama_penumpang, "
                + "p.nik, p.umur, p.jumlah_tiket, "
                + "k.nama_kapal, k.harga_tiket "
                + "FROM pemesanan p "
                + "JOIN kapal k ON p.id_kapal = k.id_kapal "
                + "ORDER BY p.id_pemesanan";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Pemesanan p = new Pemesanan(
                        rs.getInt("id_pemesanan"),
                        rs.getString("nama_penumpang"),
                        rs.getString("nik"),
                        rs.getInt("umur"),
                        rs.getString("nama_kapal"),
                        rs.getInt("jumlah_tiket"),
                        rs.getInt("harga_tiket")
                );

                daftar.add(p);
            }
        } catch (SQLException e) {
            System.out.println(
                    "Gagal mengambil data pemesanan - " + e.getMessage());
        }

        return daftar;
    }

    public boolean ubah(Pemesanan p) {
        String sql = "UPDATE pemesanan SET nama_penumpang = ?, "
                + "nik = ?, umur = ?, id_kapal = ?, "
                + "jumlah_tiket = ?, total_harga = ? "
                + "WHERE id_pemesanan = ?";

        try (Connection conn = Koneksi.getConnection()) {
            int idKapal = cariIdKapal(conn, p.getNamaKapal());

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, p.getNamaPenumpang());
                pstmt.setString(2, p.getNik());
                pstmt.setInt(3, p.getUmur());
                pstmt.setInt(4, idKapal);
                pstmt.setInt(5, p.getJumlahTiket());
                pstmt.setInt(6, p.getTotalHarga());
                pstmt.setInt(7, p.getIdPemesanan());

                int barisBerubah = pstmt.executeUpdate();

                if (barisBerubah > 0) {
                    System.out.println(
                            "Data pemesanan berhasil diubah!");
                    return true;
                } else {
                    System.out.println(
                            "ID pemesanan tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println(
                    "Gagal mengubah pemesanan - " + e.getMessage());
        }

        return false;
    }

    public boolean hapus(int idPemesanan) {
        String sql = "DELETE FROM pemesanan WHERE id_pemesanan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPemesanan);

            int barisBerubah = pstmt.executeUpdate();

            if (barisBerubah > 0) {
                System.out.println(
                        "Pemesanan berhasil dihapus!");
                return true;
            } else {
                System.out.println(
                        "ID pemesanan tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println(
                    "Gagal menghapus pemesanan - " + e.getMessage());
        }

        return false;
    }

    private int cariIdKapal(Connection conn, String namaKapal)
            throws SQLException {

        String sql = "SELECT id_kapal FROM kapal WHERE nama_kapal = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaKapal);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_kapal");
                }
            }
        }

        throw new SQLException("Kapal tidak ditemukan.");
    }
}
