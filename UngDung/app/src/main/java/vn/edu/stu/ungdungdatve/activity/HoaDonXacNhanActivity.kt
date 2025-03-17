package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.HoaDon
import vn.edu.stu.ungdungdatve.models.TinhTrangGhe
import vn.edu.stu.ungdungdatve.models.Ve
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalDate

class HoaDonXacNhanActivity : AppCompatActivity() {
    lateinit var imgvHD: ImageView
    lateinit var tvTieuDePhim_HD: TextView
    lateinit var tvTieuDeRap_HD: TextView
    lateinit var tvTieuDeNgayChieuPhim_HD: TextView
    lateinit var tvMa_HD: TextView
    lateinit var tvGhe_HD: TextView
    lateinit var tvTongTien: TextView
    lateinit var btnThanhToan_HD: Button
    lateinit var dsGhe: List<TinhTrangGhe>
    var data = "ĐẶT VÉ THÀNH CÔNG!!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hoa_don_xac_nhan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvents()
        getData()
    }

    private fun addControls() {
        imgvHD = findViewById(R.id.imgvHD)
        tvTieuDePhim_HD = findViewById(R.id.tvTieuDePhim_HD)
        tvTieuDeRap_HD = findViewById(R.id.tvTieuDeRap_HD)
        tvTieuDeNgayChieuPhim_HD = findViewById(R.id.tvTieuDeNgayChieuPhim_HD)
        tvMa_HD = findViewById(R.id.tvMa_HD)
        tvGhe_HD = findViewById(R.id.tvGhe_HD)
        tvTongTien = findViewById(R.id.tvTongTien)
        btnThanhToan_HD = findViewById(R.id.btnThanhToan_HD)
        dsGhe = ArrayList()
    }

    fun getRandomCharacterFromModifiedString(): String {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var temp = ""
        for (i in 0 until 4) {
            temp += characters.random()
        }
        return temp // Chọn ngẫu nhiên một ký tự từ chuỗi
    }

    fun loadWithDelay(action: () -> Unit) {
        // Khởi tạo một CoroutineScope
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            // Thực hiện delay 3 giây
            delay(3000)
            // Thực hiện action sau khi delay
            action()
        }
    }

    private fun addEvents() {
        var mahd = "HOADON_" + ApiService.khachhang.MaKh + getRandomCharacterFromModifiedString()
        tvMa_HD.text = mahd
        btnThanhToan_HD.setOnClickListener(View.OnClickListener {
            Log.e("MAHD", mahd)
            Log.e("MAHD", dsGhe.size.toString())
            var tongtien: Float = (dsGhe.size * 80000).toFloat()
            var qrcode = bitmapToByteArray(createQRCode(mahd))
            var hd = HoaDon(mahd, ApiService.khachhang.MaKh, LocalDate.now(), tongtien, qrcode)
            Log.e("Cai qr bi cc gi", qrcode.toString())
            xuLyTaoHD(hd)
            loadWithDelay {
                dsGhe.forEach { item ->
                    Log.e("MAHDVE", hd.MaHd)
                    Log.e("Tong tien", hd.Tongtien.toString())
                    var mave = ApiService.phim.MaPhim + getRandomCharacterFromModifiedString()
                    var _ve = Ve(mave, hd.MaHd, item.MaGhe, ApiService.suatchieu.MaSuatchieu)
                    xuLyTaoVe(_ve)
                    UpdateGhe(_ve.MaGhe)
                }
                val intent = Intent(this, ThanhToanActivity::class.java)
                intent.putExtra("DATA", data)
                startActivity(intent)
                finishAffinity()
            }
        })
    }

    private fun UpdateGhe(maghe: String) {
        ApiService.xuLyCapNhatTinhTrangGhe(this, Response.ErrorListener { error ->
            Toast.makeText(this, "Cập nhật ghế thất bại", Toast.LENGTH_SHORT).show()
            Log.e("Lỗi cập nhật ghế", error.toString())
            data = "ĐẶT VÉ THẤT BẠI"
        }, maghe, { response ->
            Toast.makeText(this, "Cập nhật ghế thành công", Toast.LENGTH_SHORT).show()
            data = "ĐẶT VÉ THÀNH CÔNG!!"
        })
    }

    @Throws(WriterException::class, IOException::class)
    private fun createQRCode(data: String): Bitmap {
        val matrix = MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500)
        val bitmap = Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_8888)
        for (x in 0 until matrix.width) {
            for (y in 0 until matrix.height) {
                bitmap.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun xuLyTaoVe(ve: Ve) {
        ApiService.xuLyCreateVe(this, { error ->
            Toast.makeText(this, "Tạo vé thất bại", Toast.LENGTH_SHORT).show()
        }, { respones ->
            Toast.makeText(this, "Tạo vé thành công", Toast.LENGTH_SHORT).show()
        }, ve)
    }

    private fun xuLyTaoHD(hoaDon: HoaDon) {
        ApiService.xuLyCreateHoaDon(this, { error ->
            Toast.makeText(this, "Đăng vé thất bại", Toast.LENGTH_SHORT).show()
        }, { respones ->
            Toast.makeText(this, "Đặt vé thành công", Toast.LENGTH_SHORT).show()
        }, hoaDon)
    }

    private fun getData() {
        val intent = intent
        dsGhe = intent.getSerializableExtra("DANHSACHGHE") as List<TinhTrangGhe>
        imgvHD.setImageBitmap(ApiService.phim.getHinhanh())
        tvTieuDePhim_HD.text = ApiService.phim.TenPhim
        tvTieuDeRap_HD.text = ApiService.rap.TenRap
        tvTieuDeNgayChieuPhim_HD.text = ApiService.suatchieu.Ngaychieu.toString()
        var soghe = ""
        dsGhe.forEach { item ->
            val lastTwoChars = item.MaGhe.takeLast(2).reversed()
            soghe += lastTwoChars + " "
        }
        tvGhe_HD.text = soghe
        var tien = 80000 * dsGhe.size
        tvTongTien.text = tien.toString() + "VND"
    }
}