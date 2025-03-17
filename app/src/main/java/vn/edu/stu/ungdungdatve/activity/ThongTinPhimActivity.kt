package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim

class ThongTinPhimActivity : AppCompatActivity() {
    lateinit var imgViewPoster: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvTheLoai: TextView
    lateinit var tvthongtinphim: TextView
    lateinit var btnBook: Button
    var phim: Phim = Phim()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thong_tin_phim)
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
        var intent: Intent = getIntent()
        ApiService.phim = intent.getSerializableExtra("PHIM") as Phim
        phim = ApiService.phim
        imgViewPoster.setImageBitmap(phim.getHinhanh())
        tvTitle.setText(phim.TenPhim)
        tvthongtinphim.setText(phim.ThongtinPhim)
        tvTheLoai.setText(phim.KieuPhim)
    }

    private fun addEvents() {
        btnBook.setOnClickListener(View.OnClickListener {
            if (ApiService.rap.MaRap != "" && ApiService.phim.MaPhim != "") {
                Log.e("Con cu dai 15m",ApiService.rap.MaRap)
                Log.e("Con cu dai 15m",ApiService.phim.MaPhim)
                val intent = Intent(this, DatVeActivity::class.java).apply {
                    putExtra("PHIM", phim)
                }
                startActivity(intent)
            } else{
                val intent = Intent(this, DanhSachRapActivity::class.java).apply {
                    putExtra("PHIM", phim)
                }
                startActivity(intent)
            }

        })
    }

    private fun addControls() {
        imgViewPoster = findViewById(R.id.imgViewPoster)
        tvTitle = findViewById(R.id.tvTitle)
        tvthongtinphim = findViewById(R.id.tvthongtinphim)
        tvTheLoai = findViewById(R.id.tvTheLoai)
        btnBook = findViewById(R.id.btnBook)
    }
}