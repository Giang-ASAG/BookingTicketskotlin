package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class KhachHang : Serializable {
    var MaKh: String = ""
    var TenKh: String = ""
    var Gioitinh: Boolean = false
    var Dienthoai: String = ""
    var Email: String = ""
    var Matkhau: String = ""
    var Quyen: String = ""

    constructor()

    constructor(tenKh: String, gioitinh: Boolean, dienthoai: String, email: String, matkhau: String, quyen: String) {
        TenKh = tenKh
        Gioitinh = gioitinh
        Dienthoai = dienthoai
        Email = email
        Matkhau = matkhau
        Quyen = quyen
    }

    constructor(maKh: String, tenKh: String, gioitinh: Boolean, dienthoai: String, email: String, matkhau: String, quyen: String) {
        MaKh = maKh
        TenKh = tenKh
        Gioitinh = gioitinh
        Dienthoai = dienthoai
        Email = email
        Matkhau = matkhau
        Quyen = quyen
    }
}