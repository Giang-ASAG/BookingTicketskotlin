package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class Ve : Serializable {
    var MaVe: String = ""
    var MaHd: String = ""
    var MaGhe: String = ""
    var MaSuatchieu: String = ""

    constructor(maVe: String, maHd: String, maGhe: String, maSuatchieu: String) {
        MaVe = maVe
        MaHd = maHd
        MaGhe = maGhe
        MaSuatchieu = maSuatchieu
    }

    constructor()
}