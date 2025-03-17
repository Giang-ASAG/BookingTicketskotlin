package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class Ghe : Serializable {
    var MaGhe: String = ""
    var MaPhong: String = ""
    var Cot: String = ""
    var Hang: String = ""

    constructor(MaGhe: String, MaPhong: String, Cot: String, Hang: String) {
        this.MaGhe = MaGhe
        this.MaPhong = MaPhong
        this.Cot = Cot
        this.Hang = Hang
    }

    constructor()

}