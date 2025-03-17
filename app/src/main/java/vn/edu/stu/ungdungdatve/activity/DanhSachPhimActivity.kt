package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.adapter.AdapterPhimMoiPhatHanh
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap

class DanhSachPhimActivity : AppCompatActivity() {
    lateinit var lvphimtimkiem: ListView
    lateinit var apdater: AdapterPhimMoiPhatHanh
    var dsPhim: List<Phim> = ArrayList()
    var rap: Rap = Rap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_danh_sach_phim)
        addControls()
        addEvents()
        getData()
    }

    private fun addEvents() {
        lvphimtimkiem.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var ph = parent.adapter.getItem(position) as Phim
            var intent: Intent = Intent(this, ThongTinPhimActivity::class.java)
            intent.putExtra("PHIM", ph)
            startActivity(intent)
        })

    }

    private fun getData() {
        var intent: Intent = getIntent()
        rap = intent.getSerializableExtra("RAP") as Rap
        ApiService.rap = rap
        ApiService.xuLyGetDSPhim(
            this,
            { response ->
                dsPhim = response
                this.runOnUiThread {
                    apdater = AdapterPhimMoiPhatHanh(
                        this,
                        R.layout.danhsachphimmoiphathanh,
                        dsPhim
                    )
                    lvphimtimkiem.adapter = apdater
                }
            },
            { error ->
                Toast.makeText(this, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("Loi", error.message + "")
            })
    }

    private fun addControls() {
        lvphimtimkiem = findViewById(R.id.lvphimtimkiem1);

    }
}