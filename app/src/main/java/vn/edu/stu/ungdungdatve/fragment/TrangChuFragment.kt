package vn.edu.stu.ungdungdatve.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.VolleyError
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.activity.ThongTinPhimActivity
import vn.edu.stu.ungdungdatve.adapter.AdapterPhimMoiPhatHanh
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim
import java.time.LocalDate

class TrangChuFragment : Fragment() {
    var dsphim: List<Phim> = ArrayList()
    lateinit var lvphimmoi: ListView
    lateinit var apdater: AdapterPhimMoiPhatHanh
    private lateinit var imgCard: ImageSlider
    private var slideModels: ArrayList<SlideModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trang_chu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControls(view)
        addEvent()
        getData()
    }

    private fun getData() {
        ApiService.xuLyGetDSPhim(requireActivity(), { response ->
            dsphim = response
            sapXepPhimMoi(dsphim)
        }, Response.ErrorListener { error: VolleyError? ->
            error?.let {
                Log.e("Loi", it.message ?: "Có lỗi xảy ra")
            }
        })
    }

    private fun addEvent() {
        lvphimmoi.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val ph = parent.adapter.getItem(position) as Phim
            var intent: Intent = Intent(requireActivity(), ThongTinPhimActivity::class.java)
            intent.putExtra("PHIM", ph)
            startActivity(intent)
        })
        imgCard.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(i: Int) {
                var intent: Intent = Intent(requireActivity(), ThongTinPhimActivity::class.java)
                if (i == 0) {
                    val ph = dsphim.get(5)
                    intent.putExtra("PHIM", ph)
                    startActivity(intent)
                } else if (i == 1) {
                    val ph = dsphim.get(0)
                    intent.putExtra("PHIM", ph)
                    startActivity(intent)
                } else {
                    val ph = dsphim.get(6)
                    intent.putExtra("PHIM", ph)
                    startActivity(intent)
                }
            }
        })

    }

    private fun sapXepPhimMoi(dsPhim: List<Phim>) {
        val dsPhimMoiNhat = mutableListOf<Phim>()
        val now = LocalDate.now()
        for (p in dsPhim) {
            val ngayRaMat = p.Ngayramat
            if (ngayRaMat != null && now.year == ngayRaMat.year) {
                if (now.dayOfYear - ngayRaMat.dayOfYear < 31) {
                    dsPhimMoiNhat.add(p)
                }
            }
        }
        requireActivity().runOnUiThread {
            apdater = AdapterPhimMoiPhatHanh(
                requireActivity(),
                R.layout.danhsachphimmoiphathanh,
                dsPhimMoiNhat
            )
            lvphimmoi.adapter = apdater
        }

    }

    private fun addControls(view: View) {
        lvphimmoi = view.findViewById(R.id.lvphimmoi)
        imgCard = view.findViewById(R.id.imgCard)
        slideModels.add(SlideModel(R.drawable.venom3, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.spider, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.joker, ScaleTypes.FIT))
        imgCard.setImageList(slideModels)
        imgCard.startSliding(2000)
    }
}