package vn.edu.stu.ungdungdatve.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.KhachHang

class ThongTinNguoiDungActivity : AppCompatActivity() {
    lateinit var edt_tt_ten : EditText;
    lateinit var edt_tt_dienthoai : EditText;
    lateinit var edt_tt_mail : EditText;
    lateinit var edt_tt_mk : EditText;
    lateinit var edt_tt_nhaplaimk : EditText;
    lateinit var btnSuaThongTin : Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thong_tin_nguoi_dung)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvent()
    }

    private fun addEvent() {
        btnSuaThongTin.setOnClickListener(View.OnClickListener {
            var K = KhachHang()
            if(edt_tt_mk.text.toString().equals(edt_tt_nhaplaimk.text.toString())){
                K.TenKh = edt_tt_ten.text.toString()
                K.Dienthoai = edt_tt_dienthoai.text.toString()
                K.Email = edt_tt_mail.text.toString()
                K.Matkhau = edt_tt_mk.text.toString()
                K.Gioitinh = ApiService.khachhang.Gioitinh
                K.MaKh = ApiService.khachhang.MaKh
                K.Quyen =ApiService.khachhang.Quyen
                xuLyUpdate(K)
                ApiService.khachhang=K
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                edt_tt_nhaplaimk.setError("Không trùng với mật khẩu")
            }

        })
    }
    fun xuLyUpdate(kh: KhachHang) {
        ApiService.xuLyUpdateKhachHang(this, Response.ErrorListener { error ->
            // Xử lý lỗi
            Log.e("Lỗi cập nhật", error.message ?: "Có lỗi xảy ra")
        },{ response ->
            // Xử lý phản hồi thành công
            Log.d("Cập nhật thành công", response.toString())
        }, ApiService.khachhang.MaKh, kh.TenKh, kh.Dienthoai, kh.Email, kh.Matkhau)
    }

    private fun addControls() {
        edt_tt_ten = findViewById(R.id.edt_tt_ten);
        edt_tt_dienthoai = findViewById(R.id.edt_tt_dienthoai);
        edt_tt_mail = findViewById(R.id.edt_tt_mail);
        edt_tt_mk = findViewById(R.id.edt_tt_mk);
        edt_tt_nhaplaimk = findViewById(R.id.edt_tt_nhaplaimk);
        btnSuaThongTin = findViewById(R.id.btnSuaThongTin);
        edt_tt_ten.setText(ApiService.khachhang.TenKh)
        edt_tt_dienthoai.setText((ApiService.khachhang.Dienthoai))
        edt_tt_mail.setText(ApiService.khachhang.Email)
    }
}