package vn.edu.stu.ungdungdatve.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.Serializable
import java.time.LocalDate

class HoaDon : Serializable {
    var MaHd: String = ""
    var MaKh: String = ""
    var NgaylapHd: LocalDate? = null // Sử dụng nullable để phù hợp với constructor không tham số
    var Tongtien: Float = 0.0f
    var qrcode: ByteArray? = null // Sử dụng nullable cho mảng byte


    constructor(maHd: String, maKh: String, ngaylapHd: LocalDate, tongtien: Float, qrcode: ByteArray) {
        this.MaHd = maHd
        this.MaKh = maKh
        this.NgaylapHd = ngaylapHd
        this.Tongtien = tongtien
        this.qrcode = qrcode
    }

    constructor()

    // Phương thức để chuyển đổi mảng byte thành Bitmap
    fun getBitmapQrcode(): Bitmap? {
        return qrcode?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}