package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.stu.ungdungdatve.R

class ThanhToanActivity : AppCompatActivity() {
    lateinit var tvyeucautt:TextView
    lateinit var btnxacnhan : Button
    var data=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thanh_toan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvents()
        getdata()
    }

    private fun addEvents() {
        btnxacnhan.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }

    private fun addControls() {
        tvyeucautt = findViewById(R.id.tvyeucautt)
        btnxacnhan = findViewById(R.id.btnxacnhan)
    }

    private fun getdata() {
        var intent = intent
        data = intent.getStringExtra("DATA").toString()
        tvyeucautt.text = data
    }
}