package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class PhongChieu : Serializable {
    var MaPhong: String = ""
    var MaRap: String = ""
    var TenPhong: String = ""

    constructor(maPhong: String, maRap: String, tenPhong: String) {
        MaPhong = maPhong
        MaRap = maRap
        TenPhong = tenPhong
    }

    constructor()
}