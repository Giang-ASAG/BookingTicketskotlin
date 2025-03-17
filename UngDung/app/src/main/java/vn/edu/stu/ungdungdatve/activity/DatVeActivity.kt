package vn.edu.stu.ungdungdatve.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.adapter.AdapterSeat
import vn.edu.stu.ungdungdatve.adapter.AdapterThoiGian
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Rap
import vn.edu.stu.ungdungdatve.models.SuatChieu
import vn.edu.stu.ungdungdatve.models.TinhTrangGhe
import vn.edu.stu.ungdungdatve.models.TinhTrangGheEnum
import java.io.Serializable

class DatVeActivity : AppCompatActivity() {
    lateinit var tvdiachi: TextView
    lateinit var spnLich: Spinner
    lateinit var rvlich: RecyclerView
    lateinit var recyclerViewLeft: RecyclerView
    lateinit var recyclerViewRight: RecyclerView
    lateinit var btnThanhToan: Button
    lateinit var dssc: List<SuatChieu>
    lateinit var dstime: MutableList<SuatChieu>
    lateinit var seatLeft: MutableList<TinhTrangGhe>
    lateinit var seatRight: MutableList<TinhTrangGhe>
    lateinit var TinhTrangTamThoi: MutableList<TinhTrangGhe>
    lateinit var ttg: TinhTrangGhe
    var dsghe: List<TinhTrangGhe> = ArrayList()
    lateinit var adapterLeft: AdapterSeat
    lateinit var adapterRight: AdapterSeat
    lateinit var apdaterTime: AdapterThoiGian
    lateinit var adapterSpinner: ArrayAdapter<String>
    lateinit var rap: Rap
    lateinit var layoutSuatchieu: GridLayoutManager
    var selectedPosition: Int = RecyclerView.NO_POSITION
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dat_ve)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addControls()
        addEvent()
        getData()
    }

    private fun addEvent() {
        spnLich.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var s = adapterSpinner.getItem(position)
                if (s != null) {
                    Log.e("cc3m", s)
                    for (item in dssc) {
                        Log.e("cc4m", item.Ngaychieu.toString())

                        if (s.equals(item.Ngaychieu.toString())) {
                            dstime.add(item)
                        }
                    }
                    runOnUiThread {
                        apdaterTime = AdapterThoiGian(dstime)
                        rvlich.adapter = apdaterTime
                    }
                } else {
                    return
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

        }

        rvlich.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val child = rv.findChildViewUnder(e.x, e.y)
                        if (child != null) {
                            val pos = rv.getChildAdapterPosition(child)
                            val suatChieu = apdaterTime.getItem(pos) as SuatChieu

                            // Hiển thị thông tin
                            Toast.makeText(
                                this@DatVeActivity,
                                "Giờ chiếu: ${suatChieu.Thoigianbatdau}",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Log thông tin
                            Log.d("DatVe", "${suatChieu.MaSuatchieu} ${suatChieu.Ngaychieu}")

                            // Lưu dữ liệu
                            ApiService.suatchieu = suatChieu

                            // Xóa nền item trước và cập nhật item mới
                            clearPreviousSelection(rv, pos)
                            selectedPosition = pos
                            updateSelectedItem(rv, selectedPosition)

                            // Xử lý dữ liệu ghế trong thread riêng
                            Thread {
                                fetchAndUpdateSeatData(suatChieu.MaSuatchieu)
                            }.start()
                        } else {
                            Log.e("DatVe", "Không tìm thấy view tại vị trí click")
                        }
                    }
                }
                return false // Không chặn sự kiện, cho phép scroll bình thường
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                // Không cần xử lý ở đây nữa vì đã xử lý trong onInterceptTouchEvent
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                // Không cần xử lý
            }
        })

        setOnClickSeat(recyclerViewLeft, seatLeft)
        setOnClickSeat(recyclerViewRight, seatRight)

        btnThanhToan.setOnClickListener(View.OnClickListener {
            xuLyThanhToan()
        })
    }

    private fun xuLyThanhToan() {
        if(TinhTrangTamThoi.size==0){
            Toast.makeText(this@DatVeActivity, "Vui lòng chọn ghế", Toast.LENGTH_SHORT).show()

        }
        else{
            val intent = Intent(
                this@DatVeActivity,
                HoaDonXacNhanActivity::class.java
            )
            intent.putExtra("DANHSACHGHE", TinhTrangTamThoi as Serializable)

            startActivity(intent)
        }
    }

    private fun clearPreviousSelection(rv: RecyclerView, pos: Int) {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            val viewHolder = rv.findViewHolderForAdapterPosition(selectedPosition)
            viewHolder?.itemView?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun updateSelectedItem(rv: RecyclerView, pos: Int) {
        val newViewHolder = rv.findViewHolderForAdapterPosition(pos)
        newViewHolder?.itemView?.setBackgroundResource(R.drawable.container_frame)
    }

    private fun fetchAndUpdateSeatData(maSuatChieu: String) {
        TinhTrangTamThoi.clear()
        seatRight.clear()
        seatLeft.clear()

        getDanhSachGhe(maSuatChieu)

        dsghe.forEach { item ->
            val lastTwoChars = item.MaGhe.takeLast(2)
            if (lastTwoChars.contains("1") || lastTwoChars.contains("2") ||
                lastTwoChars.contains("3") || lastTwoChars.contains("4")
            ) {
                seatLeft.add(item)
            } else {
                seatRight.add(item)
            }
        }

        // Cập nhật UI trên luồng chính
        runOnUiThread {
            adapterLeft.notifyDataSetChanged()
            adapterRight.notifyDataSetChanged()
        }
    }

    private fun getData() {
        var intent: Intent = getIntent()
        rap = intent.getSerializableExtra("RAP") as Rap
        if (rap != null) {
            Log.e("XemRap", ApiService.rap.TenRap)
            tvdiachi.text = rap.DiachiRap
            getLichChieu(ApiService.phim.MaPhim, rap.MaRap)
        } else {
            rap = ApiService.rap
        }

    }

    private fun setOnClickSeat(recyclerView: RecyclerView, ghes: List<TinhTrangGhe>) {
        recyclerView.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val pos = rv.getChildAdapterPosition(child)
                    ttg = ghes.get(pos)
                    if (ttg.Tinhtrang == TinhTrangGheEnum.DA_DAT) {
                        Toast.makeText(
                            this@DatVeActivity,
                            "Ghế này đã có người đặt!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        if (ttg.Tinhtrang == TinhTrangGheEnum.CHUA_DAT) {
                            TinhTrangTamThoi.add(ttg)
                            child.setBackgroundResource(R.drawable.seats_selected)
                            ttg.Tinhtrang = TinhTrangGheEnum.DANG_CHON
                        } else if (ttg.Tinhtrang == TinhTrangGheEnum.DANG_CHON) {
                            TinhTrangTamThoi.remove(ttg)
                            child.setBackgroundResource(R.drawable.seats_empty)
                            ttg.Tinhtrang = TinhTrangGheEnum.CHUA_DAT
                        }
                    }
                }
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }

        })
    }

    private fun getLichChieu(maphim: String, marap: String) {
        ApiService.getNgayChieu(
            this,
            { response ->
                dssc = response
                val uniqueString = mutableSetOf<String>()
                for (sc in dssc) {
                    Log.e("DAnh sach SC", sc.MaSuatchieu)
                    uniqueString.add(sc.Ngaychieu.toString())
                }
                runOnUiThread {
                    adapterSpinner = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        uniqueString.toList()
                    )
                    spnLich.adapter = adapterSpinner
                }
            },
            Response.ErrorListener { error -> Log.e("getLichChieu", "Error: ${error.message}") },
            maphim,
            marap
        )
    }

    private fun getDanhSachGhe(masc: String) {
        ApiService.xuLyGetTinhTrangGhe(this,
            Response.ErrorListener { error ->
                // Handle error response
                Log.e("GetDanhSachGhe", "Error: ${error.message}")
                // You can also show a toast or update the UI based on the error
            },
            masc,
            { response ->
                dsghe = ArrayList()
                dsghe = response
            }
        )
    }

    private fun addControls() {
        tvdiachi = findViewById(R.id.tvdiachi)
        spnLich = findViewById(R.id.spnLich)
        rvlich = findViewById(R.id.rvlich)
        recyclerViewLeft = findViewById(R.id.recyclerViewLeft)
        recyclerViewRight = findViewById(R.id.recyclerViewRight)
        btnThanhToan = findViewById(R.id.btnThanhToan)
        seatLeft = ArrayList()
        seatRight = ArrayList()
        rap = Rap()
        dstime = ArrayList()
        layoutSuatchieu = GridLayoutManager(this, 3)
        rvlich.layoutManager = layoutSuatchieu
        recyclerViewLeft.layoutManager = GridLayoutManager(this, 4)
        recyclerViewRight.layoutManager = GridLayoutManager(this, 4)
        TinhTrangTamThoi = ArrayList()
        adapterLeft = AdapterSeat(seatLeft)
        adapterRight = AdapterSeat(seatRight)
        recyclerViewLeft.adapter = adapterLeft
        recyclerViewRight.adapter = adapterRight
    }
}