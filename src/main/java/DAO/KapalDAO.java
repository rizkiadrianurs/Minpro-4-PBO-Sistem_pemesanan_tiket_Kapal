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
import model.Kapal;
import model.KapalVIP;
import model.KapalEkonomi;
/**
 *
 * @author ACER
 */
public class KapalDAO {

    public List<Kapal> getAll() {
        List<Kapal> daftarKapal = new ArrayList<>();

        String sql = "SELECT * FROM kapal";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nama = rs.getString("nama_kapal");
                String tujuan = rs.getString("tujuan");
                int harga = rs.getInt("harga_tiket");
                String jenis = rs.getString("jenis_kapal");
                String fasilitas = rs.getString("fasilitas");

                if ("Kapal VIP".equalsIgnoreCase(jenis)
                        || "VIP".equalsIgnoreCase(jenis)) {

                    daftarKapal.add(
                            new KapalVIP(
                                    nama, tujuan, harga, fasilitas
                            )
                    );

                } else if ("Kapal Ekonomi".equalsIgnoreCase(jenis)
                        || "Ekonomi".equalsIgnoreCase(jenis)) {

                    daftarKapal.add(
                            new KapalEkonomi(
                                    nama, tujuan, harga, fasilitas
                            )
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Gagal mengambil data kapal: " + e.getMessage()
            );
        }

        return daftarKapal;
    }
}
