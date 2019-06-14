package com.arrafi.laboratoriumar_rafi;

public class Config {
	//Address of our scripts of the CRUD
	public static final String URL_GET_GURU = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNamaGuru.php";
	public static final String URL_GET_PEGAWAI = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNamaPegawai.php";
	public static final String URL_GET_NAMA_BARANG = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNamaBarang.php";
    public static final String URL_ADD = "http://104.152.168.28/~arrafico/arrafi/laboratorium/addJadwal.php";
    public static final String URL_GET_ALL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getData.php";
    public static final String URL_GET_DATA = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getSatuData.php?no=";
    public static final String URL_UPDATE_JWL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateJadwal.php";
    public static final String URL_DELETE_JWL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/deleteJadwal.php?no=";
    public static final String URL_APPR_DATA = "http://104.152.168.28/~arrafico/arrafi/laboratorium/ApprData.php";
    public static final String URL_BLM_APPR_DT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/BlmApprData.php";
    public static final String URL_GET_PROFILGR = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getProfilGR.php?id_guru=";
    public static final String URL_GET_PROFLB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getProfilLB.php?nama_pegawai=";
    public static final String URL_UPDATE_PROFILGR = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateProfilGR.php";
    public static final String URL_UPDATE_PROFLB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateProfilLB.php";
    public static final String URL_SPINNER_GURU = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerGuru.php";
    public static final String URL_SPINNER_LABORAN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerLaboran.php";
    public static final String URL_ADD_NILAI_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/addNilaiLab.php";
    public static final String URL_GET_DETAIL_NILAI = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getDetailNilaiLab.php?idNilai=";
    public static final String URL_GET_NILAI_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNilaiLab.php";
    public static final String URL_GET_NILAI_LAB_ORG = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNilaiLabOrg.php?nama_pegawai=";
    public static final String URL_GET_NILAI_drGR = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getNilaidrGR.php?nama_guru=";
    public static final String URL_ADD_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/addPeralatan.php";
    public static final String URL_EDIT_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updatePeralatan.php";
    public static final String URL_DELETE_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/deleteLaboratorium.php?id_peralatan=";
    public static final String URL_GET_ALL_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getDataAlat.php";
    public static final String URL_GET_DETAIL_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getDetailAlat.php?id_peralatan=";
    public static final String URL_GET_ALAT_RUSAK = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getAlatRusak.php";
    public static final String URL_GET_PINJAM_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getPinjamAlat.php";
    public static final String URL_GET_KEMBALI_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/pengembalianalat.php?nama_guru=";
    public static final String URL_GET_SEMUA_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/semuaAlat.php";
    public static final String URL_GET_PINJAM = "http://104.152.168.28/~arrafico/arrafi/laboratorium/cekPinjamAlat.php";
    public static final String URL_REKAP_KEGIATAN_ALL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapKegiatanSemua.php";
    public static final String URL_REKAP_KEGIATAN_BULAN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRewardKegiatanBulan.php?teks_bulan=";
    public static final String URL_REKAP_KEGIATAN_TAHUN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapKegiatanTahun.php?teks_tahun=";
    public static final String URL_REKAP_KEGIATAN_BT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapKegiatanBT.php?teks_bulan_tahun=";
    public static final String URL_REKAP_ALAT_ALL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapPerangkatALL.php";
    public static final String URL_REKAP_ALAT_BULAN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapPerangkatBulan.php?teks_bulan=";
    public static final String URL_REKAP_ALAT_TAHUN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapPerangkatTahun.php?teks_tahun=";
    public static final String URL_REKAP_ALAT_BT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getRekapPerangkatBT.php?teks_bulan_tahun=";
    public static final String URL_READ_KEGIATAN_ALL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapKegiatanALL.php";
    public static final String URL_READ_KEGIATAN_BULAN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapKegiatanBulan.php?bulan=";
    public static final String URL_READ_KEGIATAN_TAHUN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapKegiatanTahun.php?tahun=";
    public static final String URL_READ_KEGIATAN_BT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapKegiatanBT.php?bulantahun=";
    public static final String URL_READ_ALAT_ALL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapAlatALL.php";
    public static final String URL_READ_ALAT_BULAN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapAlatBulan.php?bulan=";
    public static final String URL_READ_ALAT_TAHUN = "http://104.152.168.28/~arrafico/arrafi/laboratorium/rekapRewardAlatTahun.php?tahun=";
    public static final String URL_READ_ALAT_BT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/readRekapAlatBT.php?bulantahun=";
    public static final String URL_ADD_HISTORY_ALAT = "http://104.152.168.28/~arrafico/arrafi/laboratorium/addHistoryAlat.php";
    public static final String URL_EDIT_HISTORY_1 = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateHistoryAlat.php";
    public static final String URL_EDIT_HISTORY_2 = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateHistoryAlat1.php";
    public static final String URL_ADD_PENILAIAN_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/addPenilaianLab.php";
    public static final String URL_GET_DETAIL_PENILAIAN_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getDetailPenilaianLab.php?idPenilaian=";
    public static final String URL_GET_DETAIL_PENILAIAN_GR = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getPenilaianGR.php?nama_guru=";
    public static final String URL_GET_ALL_PENILAIAN_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getALLPenilaianLab.php";
    public static final String URL_GET_DETAIL_PENILAIAN_PG = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getPenilaianPG.php?nama_pegawai=";
    public static final String URL_GET_MAPEL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getMapel.php?kelas=";
    public static final String URL_GET_MP_GR = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getMapelLab.php?kelas=";
    public static final String SPINNER_KELAS_LAB = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerKelasLab.php";
    public static final String URL_GET_MAPEL_NILAI = "http://104.152.168.28/~arrafico/arrafi/laboratorium/getMapelNilai.php?kelas=";
    public static final String URL_UPDATE_NILAI_JADWAL = "http://104.152.168.28/~arrafico/arrafi/laboratorium/updateNilaiJadwal.php?";
    public static final String URL_LAPORKAN_RUSAK = "http://104.152.168.28/~arrafico/arrafi/laboratorium/laporkankerusakan.php";
//    public static final String 
//    public static final String 
//    public static final String 
//    public static final String 
//    public static final String 
//    public static final String 
//    public static final String 

    //Keys that will be used to send the request to php scripts
    public static final String KEY_NO = "nom";
    //Tabel Jadwal
    public static final String KEY_ID = "no";
    public static final String KEY_TGL = "tgl";
    public static final String KEY_KELAS = "kelas";
    public static final String KEY_MAPEL = "mapel";
    public static final String KEY_JAMKE = "jamKe";
    public static final String KEY_KET = "ket";
    public static final String KEY_STATUS = "status";
    public static final String KEY_nmGR = "nama_guru";
    public static final String KEY_nmLB = "nama_pegawai";
    public static final String KEY_NIL_JAD = "nilai";
    
    //Tabel Guru
    public static final String KEY_ID_GURU = "id_guru";
    public static final String KEY_NAMA_GURU = "nama_guru";
    public static final String KEY_JK_GURU = "jenis_kelamin";
    public static final String KEY_NIP_GURU = "nip";
    public static final String KEY_TMT_GURU = "tmt";
    public static final String KEY_WWNG_GURU = "wewenang";
    public static final String KEY_KELAS_GURU = "kelas";
    public static final String KEY_USER_GURU = "username";
    public static final String KEY_PASS_GURU = "password";
    
    //Tabel Pegawai
    public static final String KEY_ID_PEG = "id_pegawai";
    public static final String KEY_NAMA_PEG = "nama_pegawai";
    public static final String KEY_JK_PEG = "jenis_kelamin";
    public static final String KEY_NIP_PEG = "nip";
    public static final String KEY_TMT_PEG = "tmt";
    public static final String KEY_JBTN_PEG = "jabatan";
    public static final String KEY_USER_PEG = "username";
    public static final String KEY_PASS_PEG = "password";
    
    //Tabel Nilai
    public static final String KEY_ID_NILAI = "idNilai";
    public static final String KEY_JENIS_NILAI = "jenisNilai";
    public static final String KEY_NILAI = "nilai";
    public static final String KEY_KET_NILAI = "keterangan";
    public static final String KEY_NILAI_GR = "nama_guru";
    public static final String KEY_NILAI_LB = "nama_pegawai";
    
    //Tabel Peralatan
    public static final String KEY_ID_ALAT = "id_peralatan";
    public static final String KEY_NAMA_BARANG = "nama_barang";
    public static final String KEY_KODISI_BGR = "kondisi";
    public static final String KEY_STATUS_BRG = "status";
    public static final String KEY_GURU_PIJNAM = "nama_guru";
    public static final String KEY_KELUHAN_BRG = "keluhan";
    public static final String KEY_KET_KELUHAN_BRG = "ket_keluhan";
    public static final String KEY_TGL_BELI_BRG = "tgl_beli";
    public static final String KEY_KONFIRM_BRG = "konfirm";
    public static final String KEY_TGL_INP = "waktu_pinjam";
    public static final String KEY_NM_LAB = "nama_pegawai";
    
    //Tabel History
    public static final String KEY_ID_HIS_ALAT = "idHistoryAlat";
    public static final String KEY_NAMA_PEMINJAM = "nama_peminjam";
    public static final String KEY_NAMA_BRG_PJM = "nama_barang";
    public static final String KEY_TGL_PJM = "tgl_pinjam";
    public static final String KEY_TGL_KBL = "tgl_kembali";
    public static final String KEY_NAMA_ORG_LAB = "nama_pegawai";
    
    //Jadwal Guru
    public static final String KEY_ID_JG = "idJadwalGR";
    public static final String KEY_KELAS_JG = "kelas";
    public static final String KEY_MAPEL_JG = "mapel";
    public static final String KEY_NAMAGR_JG = "nama_guru";
    
    //Penilaian Lab
    public static final String KEY_ID_PENILAIAN = "idPenilaian";
    public static final String KEY_PENJADWALAN = "penjadwalan";
    public static final String KEY_MEKANISME = "mekanisme";
    public static final String KEY_PELAYANAN = "pelayanan";
    public static final String KEY_SARANA = "sarana";
    public static final String KEY_SUASANA = "suasana";
    public static final String KEY_NAMA_PENILAI_GR = "nama_guru";
    public static final String KEY_NAMA_PENILAI_LB = "nama_pegawai";
    public static final String KEY_PENILAI_TGL = "tanggal";
    public static final String KEY_PENILAI_KELAS = "kelas";
    public static final String KEY_PENILAI_MAPEL = "mapel";
    public static final String KEY_PENILAI_JAMKE = "jamKe";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "no";
    public static final String TAG_TGL = "tgl";
    public static final String TAG_KELAS = "kelas";
    public static final String TAG_MAPEL = "mapel";
    public static final String TAG_JAMKE = "jamKe";
    public static final String TAG_KET = "ket";
    public static final String TAG_STATUS = "status";
    public static final String TAG_nmGR = "nama_guru";
    public static final String TAG_nmLB = "nama_pegawai";
    public static final String TAG_NIL_JAD = "nilai";
    
    public static final String TAG_ID_GURU = "id_guru";
    public static final String TAG_NAMA_GURU = "nama_guru";
    public static final String TAG_JK_GURU = "jenis_kelamin";
    public static final String TAG_NIP_GURU = "nip";
    public static final String TAG_TMT_GURU = "tmt";
    public static final String TAG_WWNG_GURU = "wewenang";
    public static final String TAG_KELAS_GURU = "kelas";
    public static final String TAG_USER_GURU = "username";
    public static final String TAG_PASS_GURU = "password";
    
    public static final String TAG_ID_PEG = "id_pegawai";
    public static final String TAG_NAMA_PEG = "nama_pegawai";
    public static final String TAG_JK_PEG = "jenis_kelamin";
    public static final String TAG_NIP_PEG = "nip";
    public static final String TAG_TMT_PEG = "tmt";
    public static final String TAG_JBTN_PEG = "jabatan";
    public static final String TAG_USER_PEG = "username";
    public static final String TAG_PASS_PEG = "password";
    
    public static final String TAG_ID_NILAI = "idNilai";
    public static final String TAG_JENIS_NILAI = "jenisNilai";
    public static final String TAG_NILAI = "nilai";
    public static final String TAG_KET_NILAI = "keterangan";
    public static final String TAG_NILAI_GR = "nama_guru";
    public static final String TAG_NILAI_LB = "nama_pegawai";
    
    public static final String TAG_ID_ALAT = "id_peralatan";
    public static final String TAG_NAMA_BARANG = "nama_barang";
    public static final String TAG_KODISI_BGR = "kondisi";
    public static final String TAG_STATUS_BRG = "status";
    public static final String TAG_GURU_PIJNAM = "nama_guru";
    public static final String TAG_KELUHAN_BRG = "keluhan";
    public static final String TAG_KET_KELUHAN_BRG = "ket_keluhan";
    public static final String TAG_TGL_BELI_BRG = "tgl_beli";
    public static final String TAG_KONFIRM_BRG = "konfirm";
    public static final String TAG_TGL_INP = "waktu_pinjam";
    public static final String TAG_NM_LAB = "nama_pegawai";
    
    public static final String TAG_ID_HIS_ALAT = "idHistoryAlat";
    public static final String TAG_NAMA_PEMINJAM = "nama_peminjam";
    public static final String TAG_NAMA_BRG_PJM = "nama_barang";
    public static final String TAG_TGL_PJM = "tgl_pinjam";
    public static final String TAG_TGL_KBL = "tgl_kembali";
    public static final String TAG_NAMA_ORG_LAB = "nama_pegawai";
    
    public static final String TAG_ID_JG = "idJadwalGR";
    public static final String TAG_KELAS_JG = "kelas";
    public static final String TAG_MAPEL_JG = "mapel";
    public static final String TAG_MAPEL_JG1 = "mapel";
    public static final String TAG_NAMAGR_JG = "nama_guru";
    
    public static final String TAG_ID_PENILAIAN = "idPenilaian";
    public static final String TAG_PENJADWALAN = "penjadwalan";
    public static final String TAG_MEKANISME = "mekanisme";
    public static final String TAG_PELAYANAN = "pelayanan";
    public static final String TAG_SARANA = "sarana";
    public static final String TAG_SUASANA = "suasana";
    public static final String TAG_NAMA_PENILAI_GR = "nama_guru";
    public static final String TAG_NAMA_PENILAI_LB = "nama_pegawai";
    public static final String TAG_PENILAI_TGL = "tanggal";
    public static final String TAG_PENILAI_KELAS = "kelas";
    public static final String TAG_PENILAI_MAPEL = "mapel";
    public static final String TAG_PENILAI_JAMKE = "jamKe";
    
  //jadwal id to pass with intent
    public static final String JWL_ID = "jwl_id";
    public static final String ID_PROFILGR = "id_profGR";
    public static final String ID_PROFILLB = "id_profLB";
    public static final String NIL_ID = "id_nil";
    public static final String NIL_PENIL = "id_penilaian";
    public static final String IDNYA_ALAT = "id_alat";
    public static final String NAMAPEG = "nama_pegawai";
    public static final String USERNAME = "username";
    public static final String GETJDWL = "getJDWL";

}
