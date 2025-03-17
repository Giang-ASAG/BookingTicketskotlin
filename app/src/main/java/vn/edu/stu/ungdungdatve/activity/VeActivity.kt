package vn.edu.stu.ungdungdatve.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.HoaDon
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap
import vn.edu.stu.ungdungdatve.models.SuatChieu
import vn.edu.stu.ungdungdatve.models.Ve

class VeActivity : AppCompatActivity() {
    private lateinit var imgvVe: ImageView
    private lateinit var imgvQRVe: ImageView
    private lateinit var tvTieuDeRap: TextView
    private lateinit var tvMaHoaDonn: TextView
    private lateinit var tvNgayDatVe: TextView
    private lateinit var tvNgay: TextView
    private lateinit var tvGio: TextView
    private lateinit var tvGhe: TextView
    private lateinit var dsVe: List<Ve>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ve)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        getData()
    }

    private fun getData() {
        val hd = intent.getSerializableExtra("HOADON") as? HoaDon
        if (hd == null) {
            Log.e("GetDataError", "HoaDon is null")
            Toast.makeText(this, "Invalid invoice data", Toast.LENGTH_SHORT).show()
            return
        }
        tvNgayDatVe.text = hd.NgaylapHd.toString()
        tvMaHoaDonn.text = hd.MaHd
        imgvQRVe.setImageBitmap(hd.getBitmapQrcode())
        // Gọi API để lấy vé
        ApiService.xuLyGetVetuHd(this, { error ->
            Log.e("GetDataError", "Error occurred: ${error.message}")
            Toast.makeText(this, "Failed to retrieve tickets: ${error.message}", Toast.LENGTH_SHORT).show()
        }, { response ->
            dsVe = response
            if (dsVe.isNotEmpty()) {
                handleTickets()
            } else {
                Log.e("GetDataError", "Danh sách vé rỗng")
                Toast.makeText(this, "No tickets found for this invoice", Toast.LENGTH_SHORT).show()
            }
        }, hd.MaHd)
    }

    private fun handleTickets() {
        var str = ""
        for (item in dsVe){
            val last = item.MaGhe.takeLast(2)
            str += " "+ last.reversed()
        }
        tvGhe.text = str
        fetchSuatChieu(dsVe[0].MaSuatchieu)
    }

    private fun fetchSuatChieu(maSuatchieu: String) {
        ApiService.getSuatChieu(this, { sc ->
            fetchPhim(sc.MaPhim, sc.MaPhong)
            // Cập nhật thông tin ngày chiếu và giờ
            tvNgay.text = sc.Ngaychieu.toString()
            tvGio.text = sc.Thoigianbatdau.toString()
        }, { error ->
            Log.e("GetDataError", "Error getting SuatChieu: ${error.message}")
        }, maSuatchieu)
    }

    private fun fetchPhim(maPhim: String, maPhong: String) {
        ApiService.xuLyGetPhim(this, { ph ->
            imgvVe.setImageBitmap(ph.getHinhanh())
            fetchRap(maPhong)
        }, { error ->
            Log.e("GetDataError", "Error getting Phim: ${error.message}")
        }, maPhim)
    }

    private fun fetchRap(maPhong: String) {
        ApiService.xuLyGetRap(this, { r ->
            tvTieuDeRap.text = r.DiachiRap
        }, { error ->
            Log.e("GetDataError", "Error getting Rap: ${error.message}")
        }, maPhong)
    }

    private fun addControls() {
        imgvVe = findViewById(R.id.imgvVe)
        imgvQRVe = findViewById(R.id.imgvQRVe)
        tvTieuDeRap = findViewById(R.id.tvTieuDeRap)
        tvMaHoaDonn = findViewById(R.id.tvMaHoaDonn)
        tvNgay = findViewById(R.id.tvNgay)
        tvGhe = findViewById(R.id.tvGhe)
        tvGio = findViewById(R.id.tvGio)
        tvNgayDatVe = findViewById(R.id.tvNgayDatVe)
        dsVe = emptyList() // Khởi tạo danh sách vé rỗng
    }
}