package vn.edu.stu.ungdungdatve.models

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

class SuatChieu : Serializable {
    var MaSuatchieu: String = ""
    var MaPhong: String = ""
    var MaPhim: String = ""
    var Ngaychieu: LocalDate? = null
    var Thoigianbatdau: LocalTime? = null
    var Thoigianketthuc: LocalTime? = null

    constructor()

    constructor(maSuatchieu: String, maPhong: String, maPhim: String, ngaychieu: LocalDate, thoigianbatdau: LocalTime, thoigianketthuc: LocalTime) {
        MaSuatchieu = maSuatchieu
        MaPhong = maPhong
        MaPhim = maPhim
        Ngaychieu = ngaychieu
        Thoigianbatdau = thoigianbatdau
        Thoigianketthuc = thoigianketthuc
    }
}