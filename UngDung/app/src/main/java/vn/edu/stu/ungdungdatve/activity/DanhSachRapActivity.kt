package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.adapter.AdapterRap
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap

class DanhSachRapActivity : AppCompatActivity() {
    private var dsrap: List<Rap> = ArrayList()
    private lateinit var lvrap: ListView
    private lateinit var adapter: AdapterRap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_danh_sach_rap)
        addControls()
        addEvents()
        getData()
    }

    private fun addEvents() {
        lvrap.setOnItemClickListener { parent, view, position, id ->
            var rap = parent.adapter.getItem(position) as Rap
            if (rap != null) {
                var intent = Intent(this, DatVeActivity::class.java).apply {
                    putExtra("RAP", rap)
                }
                startActivity(intent)
            } else {
                Log.e("Loi Rap", "Rap = null")
            }
        }
    }

    private fun getData() {
        val intent = intent
        val p = intent.getSerializableExtra("PHIM") as Phim
        LoadDSrapKhiCoPhim(p.MaPhim)


    }


    private fun LoadDSrapKhiCoPhim(maphim: String) {
        ApiService.xuLyGetDSRapKhicoMaPhim(this, { response ->
            dsrap = response
            for (rap in dsrap) {
                Log.e("LoadDSrapKhiCoPhim", "MaRap: ${rap.MaRap}")
            }
            this.runOnUiThread {
                adapter = AdapterRap(this, R.layout.danhsachrap, dsrap)
                lvrap.adapter = adapter
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(this, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            Log.e("Loi", error.message.toString())
        }, maphim)
    }

    private fun addControls() {
        lvrap = findViewById(R.id.lvrap1)
    }
}