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

    public List<Pemesanan> getAll() {
        List<Pemesanan> daftar = new ArrayList<>();

        String sql = "SELECT p.id_pemesanan, p.nama_penumpang, "
                + "p.nik, p.umur, p.jumlah_tiket, "
                + "k.nama_kapal, k.harga_tiket "
                + "FROM pemesanan p "
                + "JOIN kapal k ON p.id_kapal = k.id_kapal "
                + "ORDER BY p.id_pemesanan";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }

        return daftar;
    }

    private int cariIdKapal(Connection conn, String namaKapal)
            throws SQLException {

        String sql = "SELECT id_kapal FROM kapal WHERE nama_kapal = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKapal);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_kapal");
                }
            }
        }

        throw new SQLException("Kapal tidak ditemukan.");
    }

    public int tambah(Pemesanan p) {
        String sql = "INSERT INTO pemesanan "
                + "(nama_penumpang, nik, umur, id_kapal, "
                + "jumlah_tiket, total_harga) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {
            int idKapal = cariIdKapal(conn, p.getNamaKapal());

            try (PreparedStatement ps = conn.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, p.getNamaPenumpang());
                ps.setString(2, p.getNik());
                ps.setInt(3, p.getUmur());
                ps.setInt(4, idKapal);
                ps.setInt(5, p.getJumlahTiket());
                ps.setInt(6, p.getTotalHarga());

                if (ps.executeUpdate() > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal menambah data: " + e.getMessage());
        }

        return 0;
    }

    public boolean ubah(Pemesanan p) {
        String sql = "UPDATE pemesanan SET nama_penumpang = ?, "
                + "nik = ?, umur = ?, id_kapal = ?, "
                + "jumlah_tiket = ?, total_harga = ? "
                + "WHERE id_pemesanan = ?";

        try (Connection conn = Koneksi.getConnection()) {
            int idKapal = cariIdKapal(conn, p.getNamaKapal());

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getNamaPenumpang());
                ps.setString(2, p.getNik());
                ps.setInt(3, p.getUmur());
                ps.setInt(4, idKapal);
                ps.setInt(5, p.getJumlahTiket());
                ps.setInt(6, p.getTotalHarga());
                ps.setInt(7, p.getIdPemesanan());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(int idPemesanan) {
        String sql = "DELETE FROM pemesanan WHERE id_pemesanan = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPemesanan);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus data: " + e.getMessage());
            return false;
        }
    }
}
