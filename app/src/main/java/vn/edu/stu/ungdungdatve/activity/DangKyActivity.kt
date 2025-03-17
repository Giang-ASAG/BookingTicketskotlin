package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.KhachHang

class DangKyActivity : AppCompatActivity() {
    lateinit var edt_dk_tk: EditText
    lateinit var edttenkh: EditText
    lateinit var edtemail: EditText
    lateinit var edtsdt: EditText
    lateinit var edt_mk: EditText
    lateinit var edtnhaplai_mk: EditText
    lateinit var tvdangnhapngay: TextView
    lateinit var btndangky: Button
    lateinit var rdogt: RadioGroup
    lateinit var KHang: KhachHang
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dang_ky)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvents()
        getData()
    }

    private fun getData() {
        ApiService.xuLyDangKyKhachHang(
            this,
            Response.ErrorListener { error ->
                KHang = KhachHang()
            },
            { response ->
                KHang = response
            }, edt_dk_tk.text.toString()
        )
    }

    private fun addControls() {
        btndangky = findViewById(R.id.btndangky)
        edt_dk_tk = findViewById(R.id.edt_dk_tk)
        tvdangnhapngay = findViewById(R.id.tvdangnhapngay)
        edt_mk = findViewById(R.id.edt_mk)
        edtemail = findViewById(R.id.edtemail)
        edttenkh = findViewById(R.id.edttenkh)
        edtsdt = findViewById(R.id.edtsdt)
        edtnhaplai_mk = findViewById(R.id.edtnhaplai_mk)
        rdogt = findViewById(R.id.rdoGt)
        KHang = KhachHang()
    }

    private fun addEvents() {
        btndangky.setOnClickListener(View.OnClickListener {
            xuLyDangKy()
        })
    }

    private fun xuLyDangKy() {
        if (edt_dk_tk.getText() == null || edt_mk.getText() == null || edtnhaplai_mk.text == null || edtsdt.text == null || edtemail.text == null || edttenkh.text == null) {
            Toast.makeText(
                this@DangKyActivity,
                "Vui lòng nhập đầy đủ thông tin!!!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (KHang.MaKh=="") {
                val makh: String = edt_dk_tk.getText().toString()
                val email = edtemail.text.toString()
                val matkhau: String = edt_mk.getText().toString()
                val tenkh = edttenkh.text.toString()
                val sdt = edtsdt.text.toString()
                var gioitinh = true
                gioitinh = if (rdogt.checkedRadioButtonId == R.id.radio_nam) {
                    true
                } else {
                    false
                }
                if (edtnhaplai_mk.text.toString() == matkhau) {
                    val khachHang =
                        KhachHang(makh, tenkh, gioitinh, sdt, email, matkhau, "user")
                    ApiService.xuLyAddKhachHang(
                        this, Response.ErrorListener { error ->
                            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                        },
                        { response ->
                            Toast.makeText(
                                this@DangKyActivity,
                                "Đăng ký thành công",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            ApiService.khachhang= khachHang
                        }, khachHang
                    )
                    val intent = Intent()
                    intent.putExtra("KHACHHANG", khachHang)
                    setResult(RESULT_OK,intent)
                    finish()
                } else {
                    edtnhaplai_mk.error = "Không trùng mật khẩu"
                }
            } else {
                edt_dk_tk.setError("Đã có tài khoản này")
            }

        }
    }
}
