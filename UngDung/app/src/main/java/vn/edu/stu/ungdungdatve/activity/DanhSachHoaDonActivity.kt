package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.adapter.AdapterHD
import vn.edu.stu.ungdungdatve.apiservice.ApiService

class DanhSachHoaDonActivity : AppCompatActivity() {
    lateinit var lvDSHD : ListView
    lateinit var adapter : AdapterHD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_danh_sach_hoa_don)
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
        ApiService.xuLyGetDSHoaDonTuKh(this,{ error->
            Log.e("GetDataError", "Lay du lieu hoa don: ${error.message}")
        }, { respone->
            adapter = AdapterHD(this, R.layout.danhsachhoadon,respone)
            lvDSHD.adapter = adapter
        },ApiService.khachhang.MaKh)
    }

    private fun addControls() {
        lvDSHD = findViewById(R.id.lvDSHD)
    }

    private fun addEvents() {
        lvDSHD.setOnItemClickListener { parent, view, position, id ->
            var hd = adapter.getItem(position)
            val intent = Intent(this, VeActivity::class.java)
            intent.putExtra("HOADON",hd)
            startActivity(intent)
        }
    }
}