package vn.edu.stu.ungdungdatve.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.Serializable
import java.time.LocalDate

class Phim : Serializable {
    var MaPhim: String = ""
    var TenPhim: String = ""
    var Ngayramat: LocalDate? = null
    var Thoiluong: Int = 0
    var ThongtinPhim: String = ""
    var KieuPhim: String = ""
    private var Hinhanh: ByteArray? = null // Khởi tạo với giá trị mặc định // Thay đổi kiểu tham số ở đây

    constructor()

    constructor(maPhim: String, tenPhim: String, ngayramat: LocalDate, thoiluong: Int, thongtinPhim: String, kieuPhim: String, hinhanh: ByteArray) {
        MaPhim = maPhim
        TenPhim = tenPhim
        Ngayramat = ngayramat
        Thoiluong = thoiluong
        ThongtinPhim = thongtinPhim
        KieuPhim = kieuPhim
        Hinhanh = hinhanh
    }

    fun getHinhanh(): Bitmap? {
        return Hinhanh?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }

    fun setHinhanh(hinhanh: ByteArray) {
        Hinhanh = hinhanh
    }
}