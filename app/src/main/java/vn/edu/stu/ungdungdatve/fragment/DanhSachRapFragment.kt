package vn.edu.stu.ungdungdatve.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.activity.DanhSachPhimActivity
import vn.edu.stu.ungdungdatve.activity.DatVeActivity
import vn.edu.stu.ungdungdatve.adapter.AdapterRap
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap


class DanhSachRapFragment : Fragment() {

    private var dsrap: List<Rap> = ArrayList()
    private lateinit var lvrap: ListView
    private lateinit var adapter: AdapterRap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_danh_sach_rap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControls(view)
        addEvents()
        getData()
    }

    private fun addEvents() {
        lvrap.setOnItemClickListener { parent, view, position, id ->
            var rap = parent.adapter.getItem(position) as Rap
            if (rap != null) {
                var intent = Intent(requireActivity(), DanhSachPhimActivity::class.java).apply {
                    putExtra("RAP", rap)
                }
                startActivity(intent)
            } else {
                Log.e("Loi Rap", "Rap = null")
            }
        }
    }

    private fun getData() {
//        if (arguments != null) {
//            arguments?.let {
//                var phim = it.getSerializable("PHIM") as? Phim
//                if (phim != null) {
//                    Log.e("PHIM NCC", "Mã Phim: ${phim.MaPhim}")
//                    LoadDSrapKhiCoPhim(phim.MaPhim)
//                } else {
//                    Log.e("PHIM NCC", "Mã Phim: null")
//                    LoadDSrap()
//                }
//            }
//        } else {
//            LoadDSrap()
//        }
        LoadDSrap()


    }

    private fun LoadDSrap() {
        ApiService.xuLyGetDSRap(requireActivity(), { response ->
            dsrap = response
            for (rap in dsrap) {
                Log.e("Kiem tra phim", "MaRap: ${rap.MaRap}")
            }
            requireActivity().runOnUiThread {
                adapter = AdapterRap(requireActivity(), R.layout.danhsachrap, dsrap)
                lvrap.adapter = adapter
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(requireContext(), "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            Log.e("Loi", error.message.toString())
        })
    }

    private fun LoadDSrapKhiCoPhim(maphim: String) {
        ApiService.xuLyGetDSRapKhicoMaPhim(requireActivity(), { response ->
            dsrap = response
            for (rap in dsrap) {
                Log.e("LoadDSrapKhiCoPhim", "MaRap: ${rap.MaRap}")
            }
            requireActivity().runOnUiThread {
                adapter = AdapterRap(requireActivity(), R.layout.danhsachrap, dsrap)
                lvrap.adapter = adapter
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(requireContext(), "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            Log.e("Loi", error.message.toString())
        }, maphim)
    }

    private fun addControls(view: View) {
        lvrap = view.findViewById(R.id.lvrap)
    }
}