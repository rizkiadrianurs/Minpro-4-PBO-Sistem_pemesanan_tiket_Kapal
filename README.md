# Minpro 4 PBO — Sistem Pemesanan Tiket Kapal

**Nama**  : Rizki Adrianur Saputra

**NIM**   : 2509116049

**Kelas** : B

---

## Deskripsi Singkat Program

Program ini adalah Sistem Pemesanan Tiket Kapal yang digunakan untuk mencatat dan mengelola data pemesanan tiket kapal penumpang. Fokus program adalah menyimpan siapa penumpangnya (nama, NIK, umur), kapal apa yang dipilih (nama kapal, tujuan, jenis VIP atau Ekonomi), berapa tiket yang dipesan, serta total harga yang dihitung otomatis dari harga tiket dikali jumlah tiket. Program dijalankan melalui console/CLI dan merupakan pengembangan dari Minpro 3 dengan menambahkan **koneksi database MySQL menggunakan JDBC (Prepared Statement)** dan **pola DAO (Data Access Object)**, tanpa meninggalkan konsep **Encapsulation**, **Inheritance**, **Polymorphism**, **Abstraction**, **validasi input**, dan **struktur MVC** dari Minpro sebelumnya.

Pengguna dapat melakukan beberapa proses, yaitu:

- Menambahkan data pemesanan tiket.
- Menampilkan seluruh data pemesanan.
- Mengubah data pemesanan.
- Menghapus data pemesanan.
- Mengakhiri program melalui menu keluar.

Berbeda dengan Minpro 3 yang menyimpan data sementara di `ArrayList` (data hilang saat program ditutup), pada Minpro 4 seluruh data **disimpan permanen di database MySQL** `db_pemesanan_kapal`. Data kapal dan data pemesanan awal (**dummy data**) diisi melalui file **`db_pemesanan_kapal.sql`**, bukan melalui kode program, sehingga ketika program pertama kali dijalankan menu Tampilkan Pemesanan langsung menampilkan data tanpa perlu input dari awal.

---

## Cara Menjalankan Program

1. Jalankan MySQL (misalnya melalui XAMPP).
2. Import file `db_pemesanan_kapal.sql` ke MySQL (lewat phpMyAdmin atau perintah `mysql -u root -p < db_pemesanan_kapal.sql`). File ini membuat database, tabel, sekaligus dummy data.
3. Sesuaikan `URL`, `USER`, dan `PASSWORD` pada `database/Koneksi.java` jika berbeda (bawaan: `localhost:3306`, user `root`, password kosong).
4. Jalankan `database/TestKoneksi.java` untuk memastikan koneksi berhasil (muncul "Koneksi database berhasil!").
5. Jalankan `main/Sistem_pemesanan_tiket_Kapal.java`. Dependensi `mysql-connector-j 8.3.0` sudah terdaftar di `pom.xml` (Maven).

---

## Struktur Folder

Struktur package pada project (mengikuti struktur MVC ditambah lapisan DAO dan database) adalah sebagai berikut:

```
Minpro-4-PBO-SistemPemesananTiketKapal
├── db_pemesanan_kapal.sql
├── README.md
└── Sistem_pemesanan_tiket_Kapal
    ├── pom.xml
    └── src
        └── main
            └── java
                ├── DAO
                │   ├── KapalDAO.java
                │   └── PemesananDAO.java
                ├── controller
                │   └── PemesananController.java
                ├── database
                │   ├── Koneksi.java
                │   └── TestKoneksi.java
                ├── main
                │   └── Sistem_pemesanan_tiket_Kapal.java
                ├── model
                │   ├── InformasiKapal.java
                │   ├── Kapal.java
                │   ├── KapalEkonomi.java
                │   ├── KapalVIP.java
                │   ├── Pemesanan.java
                │   └── Penumpang.java
                └── view
                    └── PemesananView.java
```
---

## Struktur Kelas

| Kelas | Jenis | Tanggung Jawab |
|---|---|---|
| `InformasiKapal` | **Interface** | Kontrak untuk kapal yang dapat menampilkan informasi: `tampilkanInformasiDasar()` dan `tampilkanFasilitas()`. |
| `Kapal` | **Abstract class (superclass)** | Menyimpan data umum kapal (nama kapal, tujuan, harga tiket) dan mendeklarasikan abstract method `getJenisKapal()`. |
| `KapalEkonomi` | **Subclass** dari `Kapal`, implementasi `InformasiKapal` | Menambahkan atribut `fasilitasEkonomi` dan mengisi semua method abstrak/interface. |
| `KapalVIP` | **Subclass** dari `Kapal`, implementasi `InformasiKapal` | Menambahkan atribut `fasilitasVIP` dan mengisi semua method abstrak/interface. |
| `Penumpang` | Class biasa | Menyimpan data penumpang berupa nama, NIK, dan umur (dengan validasi pada setter). |
| `Pemesanan` | Class biasa | Menyimpan data satu pemesanan (id, penumpang, kapal, jumlah tiket, harga tiket) dan menghitung total harga. Memiliki constructor overloading. |
| `Koneksi` | Database | Membuat koneksi JDBC ke database MySQL melalui `getConnection()`. |
| `TestKoneksi` | Database | Class kecil untuk menguji apakah koneksi ke database berhasil. |
| `KapalDAO` | **DAO** | Mengambil data kapal dari tabel `kapal` (`getAll()`) dan mengubah setiap baris menjadi objek `KapalVIP` atau `KapalEkonomi`. |
| `PemesananDAO` | **DAO** | Mengelola operasi CRUD pada tabel `pemesanan` menggunakan Prepared Statement: `getAll()`, `tambah()`, `ubah()`, `hapus()`. |
| `PemesananView` | View | Menampilkan menu, menerima input, dan memvalidasi input pengguna. |
| `PemesananController` | Controller | Menjalankan alur program, menghubungkan View dengan DAO, dan mengatur proses CRUD data pemesanan. |
| `Sistem_pemesanan_tiket_Kapal` | Main | Class utama yang berisi method `main`. |

---

## Struktur Database

Database yang digunakan bernama **`db_pemesanan_kapal`** dengan dua tabel yang dibuat melalui file `db_pemesanan_kapal.sql`.

<img width="400" height="207" alt="image" src="https://github.com/user-attachments/assets/4c28470e-7edb-420f-92b2-9b7f5709fa12" />

### **1. Tabel `kapal`**

<img width="830" height="258" alt="image" src="https://github.com/user-attachments/assets/692254de-ddd6-4c0d-bcb4-7e934925c06c" />

**Penjelasan:**

| Kolom | Tipe | Keterangan |
|---|---|---|
| `id_kapal` | INT, AUTO_INCREMENT | Primary key. |
| `nama_kapal` | VARCHAR(100), UNIQUE | Nama kapal. |
| `tujuan` | VARCHAR(100) | Kota tujuan. |
| `harga_tiket` | INT | Harga satu tiket. |
| `jenis_kapal` | VARCHAR(20) | `VIP` atau `Ekonomi`, menentukan objek `KapalVIP` atau `KapalEkonomi` pada `KapalDAO`. |
| `fasilitas` | VARCHAR(255) | Fasilitas kapal. |

### **2. Tabel `pemesanan`**

<img width="841" height="316" alt="image" src="https://github.com/user-attachments/assets/21f5b05c-bca3-4e25-989e-e8143e1ad8df" />

**Penjelasan:**

| Kolom | Tipe | Keterangan |
|---|---|---|
| `id_pemesanan` | INT, AUTO_INCREMENT | Primary key, dibuat otomatis oleh database. |
| `nama_penumpang` | VARCHAR(100) | Nama penumpang. |
| `nik` | CHAR(16) | NIK penumpang (16 digit). |
| `umur` | INT | Umur penumpang. |
| `id_kapal` | INT | Foreign key ke `kapal.id_kapal`. |
| `jumlah_tiket` | INT | Jumlah tiket yang dipesan. |
| `total_harga` | INT | Harga tiket × jumlah tiket. |

---

## Alur dan Gambaran Output Program

### Program Dimulai dan Dummy Data dari Database

Program dijalankan melalui method `main` pada kelas `Sistem_pemesanan_tiket_Kapal`. Method tersebut membuat objek `PemesananController` lalu memanggil `jalankanProgram()`. Saat objek `PemesananController` dibuat, controller membuat objek `PemesananDAO`, `KapalDAO`, dan `PemesananView`. **Tidak ada pengisian data awal di dalam kode**; seluruh data awal berasal dari file `db_pemesanan_kapal.sql`.

Dummy data kapal (tabel `kapal`):

| ID | Kapal | Tujuan | Jenis | Harga | Fasilitas |
|---|---|---|---|---|---|
| 1 | KM Bukit Siguntang | Balikpapan | VIP | Rp150000 | Kabin pribadi |
| 2 | KM Lambelu | Makassar | Ekonomi | Rp200000 | Kursi penumpang |
| 3 | KM Dorolonda | Parepare | Ekonomi | Rp175000 | Kursi penumpang |

Dummy data pemesanan (tabel `pemesanan`):

| ID | Penumpang | Kapal | Tujuan | Jenis | Jumlah Tiket | Total Harga |
|---|---|---|---|---|---|---|
| 001 | Andi | KM Bukit Siguntang | Balikpapan | VIP | 2 | Rp300000 |
| 002 | Budi | KM Lambelu | Makassar | Ekonomi | 1 | Rp200000 |

Data pemesanan disimpan di database sehingga tetap ada walaupun program ditutup, dan dapat ditambah, ditampilkan, diubah, atau dihapus lewat menu yang tersedia.

### Menu Utama

Program menampilkan menu utama secara berulang menggunakan perulangan `do-while`. Perulangan akan terus berjalan sampai pengguna memilih menu Keluar.

| No. | Menu | Fungsi |
|---|---|---|
| 1 | **Tambah Pemesanan** | Menambahkan data pemesanan tiket kapal ke database. |
| 2 | **Tampilkan Pemesanan** | Menampilkan seluruh data pemesanan yang tersimpan di database. |
| 3 | **Ubah Pemesanan** | Mengubah data pemesanan yang telah tersimpan. |
| 4 | **Hapus Pemesanan** | Menghapus data pemesanan yang dipilih dari database. |
| 5 | **Keluar** | Mengakhiri program. |

<img width="403" height="238" alt="Screenshot menu utama" src="https://github.com/user-attachments/assets/0bda7e13-9d02-411f-a5cd-a475a39341f4" />

Pada bagian ini, sistem menampilkan 5 menu utama, yaitu Tambah Pemesanan, Tampilkan Pemesanan, Ubah Pemesanan, Hapus Pemesanan, dan Keluar.

### 1. Tambah Pemesanan (Menu 1)

**Alur proses:**

- ID pemesanan dibuat otomatis oleh database (`AUTO_INCREMENT`), pengguna tidak perlu memasukkannya. Karena `001` dan `002` sudah dipakai dummy data, pemesanan baru yang pertama akan mendapat ID `003`. ID ditampilkan dengan format 3 digit menggunakan `String.format("%03d", id)`.
- Pengguna memasukkan nama penumpang, NIK, dan umur.
- Controller memanggil `KapalDAO.getAll()` untuk mengambil daftar kapal dari tabel `kapal`, lalu menampilkannya beserta tujuan, jenis, dan harga tiket. Jika tabel kapal kosong, sistem menampilkan "Data kapal belum tersedia." Pengguna memilih salah satu kapal.
- Pengguna memasukkan jumlah tiket.
- Controller membuat objek `Pemesanan` (harga tiket diambil dari kapal yang dipilih), lalu memanggil `PemesananDAO.tambah()`.
- `tambah()` mencari `id_kapal` berdasarkan nama kapal, kemudian menjalankan `INSERT` dengan Prepared Statement dan mengambil ID yang dibuat database melalui `getGeneratedKeys()`.
- Jika berhasil, sistem menampilkan pesan "Pemesanan berhasil ditambahkan." beserta **ID Pemesanan** dan total harga dari `getTotalHarga()`. ID ini dicatat pengguna untuk dipakai pada menu Ubah dan Hapus. Jika gagal, sistem menampilkan "Pemesanan gagal ditambahkan."

**Output Program:**

<img width="551" height="517" alt="image" src="https://github.com/user-attachments/assets/febd4af0-40c3-4903-854c-a623e691409c" />

Pada bagian ini, pengguna memasukkan data penumpang, memilih kapal, dan menentukan jumlah tiket. Data langsung tersimpan ke database, ID dibuat otomatis oleh database, dan total harga dihitung otomatis.

**Data di database setelah penambahan:**

<img width="1166" height="228" alt="image" src="https://github.com/user-attachments/assets/37805161-5f2a-4fae-b1cc-ca221d0d632b" />

### 2. Tampilkan Pemesanan (Menu 2

**Alur proses:**

- Controller memanggil `PemesananDAO.getAll()` yang menjalankan query `SELECT ... FROM pemesanan JOIN kapal ... ORDER BY id_pemesanan`.
- Setiap baris hasil query diubah menjadi objek `Pemesanan` lalu dimasukkan ke `List<Pemesanan>`.
- Jika belum terdapat data, sistem menampilkan pesan "Belum ada data pemesanan."
- Jika terdapat data, `PemesananView.tampilkanDaftarPemesanan()` menggunakan perulangan `for` untuk menampilkan ID pemesanan, nama penumpang, NIK, umur, nama kapal, jumlah tiket, harga tiket, dan total harga.

**Output Program:**

<img width="393" height="1006" alt="image" src="https://github.com/user-attachments/assets/24d4375b-3c71-4df9-89f2-814ec0981286" />

Pada bagian ini, data dummy dari file `.sql` langsung tampil tanpa perlu menambah data terlebih dahulu.

### 3. Ubah Pemesanan (Menu 3)

**Alur proses:**

- Pengguna memasukkan ID pemesanan yang ingin diubah (ID dapat dilihat pada menu Tampilkan Pemesanan).
- Controller mengambil seluruh data dari `PemesananDAO.getAll()` lalu mencari data dengan `idPemesanan` yang sesuai.
- Jika ID ditemukan, pengguna memasukkan data baru berupa nama penumpang, NIK, umur, pilihan kapal (dari daftar kapal database), dan jumlah tiket. Semua input baru divalidasi.
- Data pada objek `Pemesanan` diperbarui menggunakan setter (`setNamaPenumpang()`, `setNik()`, `setUmur()`, `setNamaKapal()`, `setHargaTiket()`, `setJumlahTiket()`).
- Controller memanggil `PemesananDAO.ubah()` yang menjalankan `UPDATE ... WHERE id_pemesanan = ?` dengan Prepared Statement.
- Jika berhasil, sistem menampilkan "Data berhasil diubah.", jika gagal menampilkan "Data gagal diubah."
- Jika ID tidak ditemukan, sistem menampilkan pesan "ID Pemesanan tidak ditemukan."

**Output Program:**

<img width="548" height="507" alt="image" src="https://github.com/user-attachments/assets/7c2365d9-6ea9-4173-9bf0-7be5824474b7" />

Pada bagian ini, pengguna memasukkan ID pemesanan yang ingin diperbarui. Setelah data baru dimasukkan, perubahan langsung disimpan ke database dan sistem menampilkan pesan "Data berhasil diubah."

**Output setelah perubahan:**

<img width="1141" height="225" alt="image" src="https://github.com/user-attachments/assets/ed259ac8-786b-4fee-be7c-c605625d69ed" />

### 4. Hapus Pemesanan (Menu 4)

**Alur proses:**

- Pengguna memasukkan ID pemesanan yang ingin dihapus.
- Controller mencari data berdasarkan `idPemesanan`.
- Jika ID ditemukan, sistem menampilkan nama penumpang lalu meminta konfirmasi (`1` = Ya, `2` = Tidak).
- Jika pengguna memilih `1`, controller memanggil `PemesananDAO.hapus()` yang menjalankan `DELETE FROM pemesanan WHERE id_pemesanan = ?`, lalu sistem menampilkan "Data berhasil dihapus." (atau "Data gagal dihapus." jika proses gagal).
- Jika pengguna memilih `2`, sistem menampilkan "Penghapusan dibatalkan."
- Jika ID tidak ditemukan, sistem menampilkan pesan "ID Pemesanan tidak ditemukan."

**Output Program:**

<img width="411" height="402" alt="image" src="https://github.com/user-attachments/assets/d217788e-98dd-4719-a1d9-ead1a60ac73c" />

Pada bagian ini, pengguna memasukkan ID pemesanan dan mengonfirmasi penghapusan. Jika pengguna memilih "Ya", data dihapus dari database dan sistem menampilkan pesan "Data berhasil dihapus."

**Output setelah perubahan:**

<img width="1145" height="217" alt="image" src="https://github.com/user-attachments/assets/90c50f91-de9a-46ce-bafc-a956002c3104" />

### 5. Keluar (Menu 5)

**Alur proses:**

- Pengguna memilih menu **5. Keluar**.
- Sistem menampilkan pesan "Program selesai. Terima kasih."
- Nilai `pilihan` menjadi `5`, sehingga kondisi pada perulangan `do-while` tidak terpenuhi dan program berhenti.

**Output Program:**

<img width="678" height="410" alt="image" src="https://github.com/user-attachments/assets/e96bbc89-6a94-4b55-924d-9572432a0415" />

Pada bagian ini, pengguna memilih menu Keluar untuk mengakhiri program. Sistem menghentikan perulangan dan menampilkan pesan "Program selesai. Terima kasih."

---

## Penerapan Konsep Wajib (4 Pilar OOP)

### 1. Encapsulation

Encapsulation diterapkan dengan menggunakan access modifier `private` pada atribut yang terdapat di dalam class.

Contoh pada `model/Kapal.java`:

<img width="360" height="125" alt="image" src="https://github.com/user-attachments/assets/d8868de2-a462-4db4-9e51-5f5f921cbecc" />

Atribut tersebut tidak dapat diakses secara langsung dari luar class. Untuk mengambil dan mengubah data digunakan method getter dan setter yang di dalamnya juga terdapat validasi.

Contohnya:

**KapalVIP**

<img width="705" height="197" alt="image" src="https://github.com/user-attachments/assets/3833dadd-c846-4ba1-9b11-e5971c454637" />

**KapalEkonomi**

<img width="773" height="192" alt="image" src="https://github.com/user-attachments/assets/537040b6-d201-40a3-805e-ead19f1467cb" />

Penerapan encapsulation juga terdapat pada class `Kapal`, `Penumpang`, dan `Pemesanan`. Pada `Pemesanan`, seluruh atribut (`idPemesanan`, `namaPenumpang`, `nik`, `umur`, `namaKapal`, `jumlahTiket`, `hargaTiket`) bersifat `private` dan hanya dapat diakses melalui getter dan setter. Nilai `idPemesanan` diisi dari database melalui constructor atau `setIdPemesanan()`, sedangkan total harga tidak disimpan sebagai atribut melainkan dihitung oleh `getTotalHarga()` (`hargaTiket * jumlahTiket`).

### 2. Inheritance

Program memiliki **1 superclass** dan **2 subclass**.

<img width="197" height="72" alt="image" src="https://github.com/user-attachments/assets/039f218a-e3fe-4dca-a6aa-0663299e44af" />

- `KapalEkonomi` dan `KapalVIP` mewarisi atribut dan method dari `Kapal` menggunakan kata kunci `extends`.
- Constructor subclass memanggil constructor superclass menggunakan `super(namaKapal, tujuan, hargaTiket)`.
- Setiap subclass menambahkan atribut khusus, yaitu `fasilitasEkonomi` pada `KapalEkonomi` dan `fasilitasVIP` pada `KapalVIP`.
- Subclass mengambil data dari superclass melalui getter (`getNamaKapal()`, `getTujuan()`, `getHargaTiket()`) karena atribut superclass bersifat `private`.
- Objek `KapalVIP` dan `KapalEkonomi` dibuat oleh `KapalDAO` dari data tabel `kapal`, berdasarkan kolom `jenis_kapal` dan `fasilitas`.

### 3. Polymorphism

**3.1 Overriding**

Overriding terjadi ketika subclass menulis ulang method yang berasal dari superclass atau interface menggunakan anotasi `@Override`, sehingga setiap jenis kapal memiliki perilaku sendiri. Pada program ini, method yang di-override adalah sebagai berikut:

| Method | Dideklarasikan di | Di-override di | Hasil Tampilan |
|---|---|---|---|
| `getJenisKapal()` | `Kapal` (abstract) | `KapalVIP`, `KapalEkonomi` | Mengembalikan "Kapal VIP" atau "Kapal Ekonomi". |
| `tampilkanInformasiDasar()` | `InformasiKapal` (interface) | `KapalVIP`, `KapalEkonomi` | Nama kapal, tujuan, harga tiket, dan jenis kapal (jenis berbeda sesuai `getJenisKapal()`). |
| `tampilkanFasilitas()` | `InformasiKapal` (interface) | `KapalVIP`, `KapalEkonomi` | `KapalVIP` menampilkan **Fasilitas VIP**, `KapalEkonomi` menampilkan **Fasilitas Ekonomi**. |

Contoh gambar pada `Fasilitas VIP`:

<img width="673" height="142" alt="image" src="https://github.com/user-attachments/assets/b2e9af3d-045a-47e7-bc7f-7f78574d1269" />

Contoh gambar pada `Fasilitas Ekonomi`:

<img width="702" height="141" alt="image" src="https://github.com/user-attachments/assets/bb86fdb9-0372-43e7-91f9-8f67b38e7714" />

**Letak pemanggilan polymorphism:** pada `KapalDAO.getAll()`, data dari tabel `kapal` diubah menjadi objek `KapalVIP` atau `KapalEkonomi`, lalu semuanya disimpan dalam satu `List<Kapal>` (upcasting ke tipe `Kapal`). Selanjutnya pada `PemesananController.tampilkanPilihanKapal()`, setiap elemen list dipanggil dengan `k.getJenisKapal()`. Tipe variabelnya adalah `Kapal`, tetapi objek yang sebenarnya bisa berupa `KapalVIP` atau `KapalEkonomi`. Java secara otomatis memilih versi method yang sesuai dengan jenis objek pada saat program berjalan, sehingga menampilkan "Kapal VIP" atau "Kapal Ekonomi" sesuai data di database.

**3.2 Overloading**

Overloading diterapkan pada **constructor class `Pemesanan`** yang terdapat pada `model/Pemesanan.java`. Overloading terjadi ketika dalam satu class terdapat beberapa method atau constructor dengan **nama yang sama tetapi memiliki daftar parameter yang berbeda**.

Pada class `Pemesanan`, terdapat dua constructor dengan nama yang sama, yaitu `Pemesanan`, tetapi jumlah parameternya berbeda.

**Contoh Constructor dengan 6 parameter**

<img width="640" height="240" alt="Screenshot 2026-10-15 220632" src="https://github.com/user-attachments/assets/f6faace3-969b-4e6e-95af-cd80ae1ec2c1" />

**Contoh Constructor dengan 7 parameter**

<img width="588" height="253" alt="image" src="https://github.com/user-attachments/assets/2c1aa15d-2ff2-4d81-a1e2-43508daf8108" />


| Constructor | Parameter | Keterangan |
|---|---|---|
| `Pemesanan(String, String, int, String, int, int)` | 6 parameter (tanpa ID) | Digunakan ketika pemesanan baru dibuat oleh pengguna. ID belum ada karena akan dibuat oleh database saat `INSERT`. |
| `Pemesanan(int, String, String, int, String, int, int)` | 7 parameter (dengan ID) | Digunakan oleh `PemesananDAO.getAll()` ketika membentuk objek dari data yang sudah tersimpan di database (ID sudah ada). |

Constructor pertama menerima `namaPenumpang`, `nik`, `umur`, `namaKapal`, `jumlahTiket`, dan `hargaTiket`. Constructor kedua menerima parameter yang sama dengan tambahan `idPemesanan` di awal.

### 4. Abstraction

Abstraction diterapkan melalui **abstract class** `Kapal` dan **abstract method** `getJenisKapal()` yang terdapat pada `model/Kapal.java`. Abstraction digunakan untuk menentukan struktur umum yang dimiliki oleh setiap kapal tanpa menentukan secara langsung jenis kapal yang digunakan.

<img width="500" height="122" alt="Abstract class Kapal" src="https://github.com/user-attachments/assets/83eea5b1-1293-4652-9c16-c182d34d9ad4" />

<img width="508" height="58" alt="Abstract method getJenisKapal" src="https://github.com/user-attachments/assets/7d193040-76fb-4748-bff1-9c71cdfc7550" />

Penerapan abstraction pada class `Kapal` dapat dijelaskan sebagai berikut:

- `Kapal` dideklarasikan sebagai **abstract class**, sehingga class tersebut tidak dapat dibuat menjadi objek secara langsung menggunakan `new Kapal(...)`.
- `Kapal` digunakan sebagai **class dasar** yang menyimpan data umum kapal, yaitu `namaKapal`, `tujuan`, dan `hargaTiket`.
- `getJenisKapal()` dideklarasikan sebagai **abstract method**, sehingga method tersebut tidak memiliki isi pada class `Kapal`.
- Setiap subclass yang mewarisi `Kapal` wajib memberikan implementasi `getJenisKapal()` sesuai dengan jenis kapalnya.
- Dengan demikian, class `Kapal` hanya menentukan bahwa setiap kapal harus memiliki informasi mengenai jenis kapal, sedangkan detail jenisnya ditentukan oleh subclass. Karena itu `KapalDAO` hanya dapat membuat objek `KapalVIP` atau `KapalEkonomi`, bukan `Kapal` secara langsung.

Implementasi `getJenisKapal()` pada `KapalVIP`:

<img width="363" height="92" alt="image" src="https://github.com/user-attachments/assets/9df2934a-773c-4550-bf21-45ef517e8e3d" />

Implementasi `getJenisKapal()` pada `KapalEkonomi`:

<img width="385" height="100" alt="image" src="https://github.com/user-attachments/assets/a012f8ab-ea07-465d-9485-7deacc18407a" />

| Kelas | Implementasi `getJenisKapal()` | Keterangan |
|---|---|---|
| `KapalVIP` | `return "Kapal VIP";` | Menentukan jenis objek sebagai kapal VIP. |
| `KapalEkonomi` | `return "Kapal Ekonomi";` | Menentukan jenis objek sebagai kapal ekonomi. |

---

## Penerapan JDBC dan DAO

Pada Minpro 4, program terhubung ke database MySQL menggunakan **JDBC** dengan driver `mysql-connector-j` (terdaftar pada `pom.xml`). Seluruh query menggunakan **Prepared Statement**, dan akses database dipisahkan ke dalam class **DAO** sehingga controller dan view tidak berisi kode SQL.

### 1. Koneksi Database (`database/Koneksi.java`)

<img width="716" height="475" alt="image" src="https://github.com/user-attachments/assets/94dc7c5a-f7dc-45d5-98c9-7253c07e7d97" />

Class `Koneksi` menyimpan `URL` (`jdbc:mysql://localhost:3306/db_pemesanan_kapal`), `USER`, dan `PASSWORD`, lalu menyediakan method `getConnection()` yang mengembalikan objek `Connection` melalui `DriverManager.getConnection()`. Class `TestKoneksi` dipakai untuk menguji koneksi dan menampilkan "Koneksi database berhasil!" jika berhasil.

<img width="685" height="192" alt="image" src="https://github.com/user-attachments/assets/4b1b1b27-db3b-4fb6-90f6-5f6130316909" />

### 2. Prepared Statement

Prepared Statement dipakai dengan tanda `?` sebagai placeholder, lalu nilainya diisi menggunakan `setString()` dan `setInt()`. Dengan cara ini nilai input tidak digabung langsung ke string SQL, sehingga lebih aman dari SQL Injection dan lebih rapi dibanding `Statement` biasa. Setiap `Connection`, `PreparedStatement`, dan `ResultSet` dibuka menggunakan **try-with-resources** sehingga otomatis ditutup, dan error SQL ditangani menggunakan `try-catch (SQLException)`.

### 3. DAO (Data Access Object)

| DAO | Method | Jenis Query | SQL | Dipakai Pada |
|---|---|---|---|---|
| `KapalDAO` | `getAll()` | Read | `SELECT * FROM kapal` | Daftar pilihan kapal pada menu Tambah dan Ubah. |
| `PemesananDAO` | `getAll()` | Read | `SELECT ... FROM pemesanan p JOIN kapal k ON p.id_kapal = k.id_kapal ORDER BY p.id_pemesanan` | Menu Tampilkan, serta pencarian data pada menu Ubah dan Hapus. |
| `PemesananDAO` | `tambah(Pemesanan)` | Create | `INSERT INTO pemesanan (...) VALUES (?, ?, ?, ?, ?, ?)` | Menu Tambah Pemesanan. |
| `PemesananDAO` | `ubah(Pemesanan)` | Update | `UPDATE pemesanan SET ... WHERE id_pemesanan = ?` | Menu Ubah Pemesanan. |
| `PemesananDAO` | `hapus(int)` | Delete | `DELETE FROM pemesanan WHERE id_pemesanan = ?` | Menu Hapus Pemesanan. |
| `PemesananDAO` | `cariIdKapal()` (private) | Read | `SELECT id_kapal FROM kapal WHERE nama_kapal = ?` | Mencari `id_kapal` sebelum `INSERT` dan `UPDATE`. |

### **1. Create**
<img width="817" height="667" alt="image" src="https://github.com/user-attachments/assets/51491998-d417-4da7-887e-81671cc18787" />

### **2. Read**
<img width="892" height="781" alt="image" src="https://github.com/user-attachments/assets/6f6fb945-c4f3-4b04-91fd-689f44e1e5b4" />

<img width="777" height="758" alt="image" src="https://github.com/user-attachments/assets/a4ec97eb-64af-4d48-a39c-0ef7108421cc" />

<img width="775" height="462" alt="image" src="https://github.com/user-attachments/assets/ffb22734-b268-45b9-9cc5-ad4eab07edcc" />


### **3. Update**
<img width="802" height="823" alt="image" src="https://github.com/user-attachments/assets/bc738624-6fd5-40e2-b49c-48d4ff4ce88b" />

### **4. Delete**
<img width="763" height="561" alt="image" src="https://github.com/user-attachments/assets/db8bd35c-c412-43a6-a994-99434af029c8" />


Penjelasan singkat:

- **Create**: `tambah()` memakai `executeUpdate()` dan `RETURN_GENERATED_KEYS` agar ID yang dibuat `AUTO_INCREMENT` dapat dikembalikan dan ditampilkan ke pengguna.
- **Read**: `getAll()` memakai `executeQuery()` dan `ResultSet`. Pada `PemesananDAO`, tabel `pemesanan` digabung dengan tabel `kapal` menggunakan `JOIN` untuk mendapatkan nama kapal dan harga tiket.
- **Update**: `ubah()` memakai `executeUpdate()` dengan kondisi `WHERE id_pemesanan = ?` dan mengembalikan `true` jika ada baris yang berubah.
- **Delete**: `hapus()` memakai `executeUpdate()` dengan kondisi `WHERE id_pemesanan = ?` dan mengembalikan `true` jika ada baris yang terhapus.
- Tabel `pemesanan` berelasi dengan tabel `kapal` melalui foreign key `id_kapal`.

### 4. Dummy Data dari File `.sql`

Dummy data **tidak diisi lewat kode**, melainkan lewat file [`db_pemesanan_kapal.sql`](db_pemesanan_kapal.sql) yang berisi perintah `CREATE TABLE` dan `INSERT INTO` (3 data kapal dan 2 data pemesanan). Setelah file di-import, menu Tampilkan Pemesanan langsung menampilkan data tanpa input dari awal.

<img width="1145" height="217" alt="image" src="https://github.com/user-attachments/assets/90c50f91-de9a-46ce-bafc-a956002c3104" />

---

## Penerapan Fitur Pendukung

### 5. Validasi Input

Validasi input diterapkan di dua lapisan: pada **view** (saat pengguna mengetik) dan pada **setter model** (`Kapal`, `KapalVIP`, `KapalEkonomi`, dan `Penumpang`, sebagai lapisan pengaman data). Jika input tidak valid, sistem menampilkan pesan kesalahan dan meminta pengguna memasukkan ulang sampai benar.

**5.1 ID Pemesanan**

<img width="382" height="128" alt="image" src="https://github.com/user-attachments/assets/557744d3-42f1-402b-916f-6cb00b325ef1" />

ID pemesanan **dibuat otomatis oleh database**, sehingga tidak perlu divalidasi saat menambah data dan tidak mungkin sama dengan ID lain. Validasi ID hanya dilakukan ketika pengguna memasukkan ID untuk **mencari data** pada menu Ubah dan Hapus: ID harus berupa angka dan lebih dari 0. Jika tidak sesuai, sistem menampilkan pesan "ID harus berupa angka." atau "ID harus lebih dari 0." dan meminta input ulang.

**5.2 Nama Penumpang**

<img width="425" height="130" alt="image" src="https://github.com/user-attachments/assets/9f1ce51c-e3ed-4ee3-8d3b-95a7498e3c4c" />

Nama tidak boleh kosong.

**5.3 NIK**

<img width="437" height="125" alt="image" src="https://github.com/user-attachments/assets/0e8a59cb-1f21-4885-8267-cf13de718d9c" />

NIK harus terdiri dari tepat 16 digit dan hanya dapat berisi angka.

**5.4 Umur**

<img width="307" height="175" alt="image" src="https://github.com/user-attachments/assets/8afa5d18-335e-40bf-b7d6-44b640767a14" />

Umur harus berupa angka dan nilainya harus antara 1 sampai 120.

**5.5 Pilihan Kapal**

<img width="538" height="435" alt="image" src="https://github.com/user-attachments/assets/05d93148-8d87-4345-ad64-eb514f014339" />

Pengguna hanya dapat memilih kapal sesuai nomor yang tersedia pada daftar. Batas pilihan mengikuti jumlah data pada tabel `kapal` (pada dummy data: 1 sampai 3).

**5.6 Jumlah Tiket**

<img width="355" height="48" alt="image" src="https://github.com/user-attachments/assets/a35157f7-bd26-4ba7-9981-7f559be4c448" />

Jumlah tiket harus berupa angka dan nilainya harus lebih dari 0.

**5.7 Pilihan Menu dan Konfirmasi Hapus**

Pilihan menu harus berupa angka 1 sampai 5, dan konfirmasi hapus hanya dapat dijawab dengan 1 atau 2.

**1. Pilihan Menu**

<img width="357" height="283" alt="image" src="https://github.com/user-attachments/assets/14bb9dcc-8a23-4716-a366-1c1475c29906" />

**2. Konfirmasi Hapus**

<img width="278" height="191" alt="image" src="https://github.com/user-attachments/assets/81cbd61d-a3cb-43d4-ba42-bc6274347829" />


Selain validasi tersebut, program menggunakan `try-catch` untuk menangani `NumberFormatException` sehingga program tetap berjalan ketika pengguna memasukkan input yang tidak sesuai, seperti huruf pada input yang harus berupa angka.

---

### 6. Interface

Nilai tambah yang diterapkan pada proyek ini adalah **Interface** melalui `InformasiKapal` yang terdapat pada `model/InformasiKapal.java`. Interface digunakan sebagai **kontrak** yang menentukan method yang harus dimiliki oleh class yang mengimplementasikannya.

<img width="392" height="147" alt="Interface InformasiKapal" src="https://github.com/user-attachments/assets/caab9163-db51-4863-a850-cb9e0019e80d" />

Interface `InformasiKapal` memiliki dua method, yaitu:

- `tampilkanInformasiDasar()` untuk menampilkan informasi dasar kapal seperti nama kapal, tujuan, harga tiket, dan jenis kapal.
- `tampilkanFasilitas()` untuk menampilkan fasilitas yang tersedia pada masing-masing jenis kapal.

Penerapan interface pada proyek ini dapat dilihat pada tabel berikut:

| Aspek | Penjelasan |
|---|---|
| **Nama Interface** | `InformasiKapal` |
| **Letak deklarasi** | `model/InformasiKapal.java` |
| **Class yang mengimplementasikan** | `KapalVIP` dan `KapalEkonomi` |
| **Bentuk implementasi** | `implements InformasiKapal` |
| **Method yang wajib diimplementasikan** | `tampilkanInformasiDasar()` dan `tampilkanFasilitas()` |
| **Objek dibuat oleh** | `KapalDAO.getAll()` (dari data tabel `kapal`) |
| **Fungsi** | Menjadi kontrak untuk menyediakan method yang digunakan dalam menampilkan informasi kapal. |

Class `KapalVIP` dan `KapalEkonomi` sama-sama mengimplementasikan `InformasiKapal`, tetapi masing-masing memberikan implementasi method sesuai dengan jenis kapalnya. Dengan demikian, interface menyediakan struktur method yang sama, sedangkan isi dari method dapat berbeda pada setiap class.

Penerapan pada `KapalVIP`:

<img width="665" height="48" alt="Implementasi InformasiKapal pada KapalVIP" src="https://github.com/user-attachments/assets/4c4341e0-f946-47eb-a233-92251a6454de" />

<img width="680" height="261" alt="image" src="https://github.com/user-attachments/assets/a78b8e09-3cd1-4468-aab5-647983dcd47b" />

Pada `KapalVIP`, class menggunakan `implements InformasiKapal` dan mengimplementasikan method `tampilkanInformasiDasar()` serta `tampilkanFasilitas()`. Method tersebut digunakan untuk menampilkan informasi kapal VIP dan fasilitas seperti kabin pribadi.

Penerapan pada `KapalEkonomi`:

<img width="702" height="37" alt="Implementasi InformasiKapal pada KapalEkonomi" src="https://github.com/user-attachments/assets/cc7ab074-d1e1-410d-a69a-11eebe35d69b" />

<img width="702" height="265" alt="image" src="https://github.com/user-attachments/assets/87bf2ec4-8e15-45f7-8162-0fa9a7010645" />

Pada `KapalEkonomi`, class juga menggunakan `implements InformasiKapal` dan mengimplementasikan kedua method yang ditentukan oleh interface. Informasi yang ditampilkan disesuaikan dengan kapal ekonomi, termasuk fasilitas yang tersedia.

Pada Minpro 4, objek kapal yang mengimplementasikan interface ini dibentuk dari data database oleh `KapalDAO`, sehingga class yang ingin menyediakan informasi dasar dan fasilitas tetap harus mengikuti kontrak yang telah ditentukan oleh `InformasiKapal`.

### 7. Struktur MVC + DAO

MVC diterapkan dengan memisahkan program ke dalam package `model`, `view`, `controller`, dan `main`. Pada Minpro 4 ditambahkan package `DAO` dan `database` agar kode akses database terpisah dari controller dan view (lihat bagian **Struktur Folder** di atas untuk susunan lengkapnya).

| Package | Kelas | Peran |
|---|---|---|
| `main` | `Sistem_pemesanan_tiket_Kapal` | Titik awal program. Hanya membuat objek controller dan menjalankannya. |
| `model` | `InformasiKapal`, `Kapal`, `KapalEkonomi`, `KapalVIP`, `Penumpang`, `Pemesanan` | Menyimpan data serta aturan data (atribut, getter/setter, perhitungan total harga). |
| `view` | `PemesananView` | Mengurus tampilan menu dan seluruh input dari pengguna beserta validasinya. |
| `controller` | `PemesananController` | Mengatur alur program, menghubungkan view dengan DAO, dan mengelola proses CRUD. |
| `DAO` | `KapalDAO`, `PemesananDAO` | Menjalankan seluruh query SQL ke database menggunakan Prepared Statement. |
| `database` | `Koneksi`, `TestKoneksi` | Mengatur koneksi JDBC ke MySQL. |

Gambar MVC:

<img width="397" height="477" alt="image" src="https://github.com/user-attachments/assets/205c3378-2d5b-4793-aabb-31edfcfdef0b" />

---


