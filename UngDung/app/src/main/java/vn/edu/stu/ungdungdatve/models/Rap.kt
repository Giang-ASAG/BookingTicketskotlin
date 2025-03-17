package vn.edu.stu.ungdungdatve.models

import java.io.Serializable

class Rap : Serializable {
    var MaRap: String = ""
    var TenRap: String = ""
    var DiachiRap: String = ""
    var ToaDoX: Double? = null
    var ToaDoY: Double? = null

    constructor(maRap: String, tenRap: String, diachiRap: String, toaDoX: Double, toaDoY: Double) {
        MaRap = maRap
        TenRap = tenRap
        DiachiRap = diachiRap
        ToaDoX = toaDoX
        ToaDoY = toaDoY
    }

    constructor()

    override fun toString(): String {
        return "Rạp: $TenRap\nĐịa chỉ: $DiachiRap"
    }
}