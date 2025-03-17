package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class TinhTrangGhe : Serializable {
    var MaGhe: String = ""
    var MaSuatchieu: String = ""
    var Tinhtrang: TinhTrangGheEnum? = null

    constructor()

    constructor(maGhe: String, maSuatchieu: String, tinhtrang: TinhTrangGheEnum) {
        MaGhe = maGhe
        MaSuatchieu = maSuatchieu
        Tinhtrang = tinhtrang
    }

    fun isTinhtrang(): Boolean {
        return Tinhtrang == TinhTrangGheEnum.DA_DAT
    }
}