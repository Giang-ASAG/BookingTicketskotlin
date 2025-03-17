package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.KhachHang

class DangNhapActivity : AppCompatActivity() {
    lateinit var edtma_kh: EditText
    lateinit var edtmatkhau: EditText
    lateinit var btndangnhap: Button
    lateinit var btntaotaikhoan: Button
    lateinit var tvquenpass: TextView
    lateinit var khachHang: KhachHang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dang_nhap)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_from)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvents()
    }

    private fun addEvents() {
        btndangnhap.setOnClickListener(View.OnClickListener {
            xulyDangNhap(edtma_kh.text.toString(), edtmatkhau.text.toString())
        })
        btntaotaikhoan.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DangKyActivity::class.java)

            startActivityForResult(intent,100)
        })
    }

    private fun xulyDangNhap(taikhoan: String, matkhau: String) {
        ApiService.xuLyGetKhachHang(
            this, Response.ErrorListener { error ->
                edtma_kh.error = "Sai cai gi do"
                edtmatkhau.error = "Sai cai gi do"
            }, { response ->
                khachHang = response
                ApiService.khachhang = khachHang
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, taikhoan, matkhau

        )
    }

    private fun addControls() {
        edtma_kh = findViewById(R.id.edtma_kh)
        edtmatkhau = findViewById(R.id.edtmatkhau)
        btndangnhap = findViewById(R.id.btndangnhap)
        btntaotaikhoan = findViewById(R.id.btntaotaikhoan)
        tvquenpass = findViewById(R.id.tvquenpass)
        khachHang = KhachHang()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data!=null) {
            var resultData = data.getSerializableExtra("KHACHHANG") as KhachHang
            edtma_kh.setText(resultData.MaKh)
            edtmatkhau.setText(resultData.Matkhau)
        }
    }
}