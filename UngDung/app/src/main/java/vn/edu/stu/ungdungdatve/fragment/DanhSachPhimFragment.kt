package vn.edu.stu.ungdungdatve.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.activity.ThongTinPhimActivity
import vn.edu.stu.ungdungdatve.adapter.AdapterPhimMoiPhatHanh
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.models.Phim

class DanhSachPhimFragment : Fragment() {

    lateinit var lvphimtimkiem: ListView
    lateinit var apdater: AdapterPhimMoiPhatHanh
    var dsPhim: List<Phim> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_phim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControls(view)
        addEvents()
        getData()
    }

    private fun getData() {
        ApiService.xuLyGetDSPhim(
            requireActivity(),
            { response ->
                dsPhim = response
                requireActivity().runOnUiThread {
                    apdater = AdapterPhimMoiPhatHanh(
                        requireActivity(),
                        R.layout.danhsachphimmoiphathanh,
                        dsPhim
                    )
                    lvphimtimkiem.adapter = apdater
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("Loi", error.message + "")
            })
    }

    private fun addEvents() {
        lvphimtimkiem.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var ph = parent.adapter.getItem(position) as Phim
            var intent: Intent = Intent(requireActivity(), ThongTinPhimActivity::class.java)
            intent.putExtra("PHIM", ph)
            startActivity(intent)
        })
    }

    private fun addControls(view: View) {
        lvphimtimkiem = view.findViewById(R.id.lvphimtimkiem);

    }

}