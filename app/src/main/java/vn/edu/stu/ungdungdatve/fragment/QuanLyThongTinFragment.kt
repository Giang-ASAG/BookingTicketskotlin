package vn.edu.stu.ungdungdatve.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.activity.DangNhapActivity
import vn.edu.stu.ungdungdatve.activity.DanhSachHoaDonActivity
import vn.edu.stu.ungdungdatve.activity.ThongTinNguoiDungActivity
import vn.edu.stu.ungdungdatve.apiservice.ApiService


class QuanLyThongTinFragment : Fragment() {
    lateinit var tv_QLTT_TenUser : TextView
    lateinit var tv_QLTT_MailUser : TextView
    lateinit var btnXemLichSu : Button
    lateinit var btnDangXuat : Button
    lateinit var btnXemThongTinUser : Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_thong_tin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControls(view)
        addEvent()
        getdata()
    }

    private fun getdata() {
        tv_QLTT_TenUser.text = ApiService.khachhang.TenKh
        tv_QLTT_MailUser.text = ApiService.khachhang.Email
    }

    private fun addControls(view: View) {
        tv_QLTT_TenUser = view.findViewById(R.id.tv_QLTT_TenUser)
        tv_QLTT_MailUser = view.findViewById(R.id.tv_QLTT_MailUser)
        btnXemLichSu = view.findViewById(R.id.btnXemLichSu)
        btnDangXuat = view.findViewById(R.id.btnDangXuat)
        btnXemThongTinUser = view.findViewById(R.id.btnXemThongTinUser)
    }

    private fun addEvent() {
        btnDangXuat.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireActivity(),DangNhapActivity::class.java))
            requireActivity().finishAffinity()
        })
        btnXemLichSu.setOnClickListener(View.OnClickListener {
            val intent=Intent(requireActivity(),DanhSachHoaDonActivity::class.java)
            startActivity(intent)
        })
        btnXemThongTinUser.setOnClickListener(View.OnClickListener {
            val intent=Intent(requireActivity(),ThongTinNguoiDungActivity::class.java)
            startActivity(intent)

        })
    }

    override fun onResume() {
        super.onResume()
        getdata()
    }

}