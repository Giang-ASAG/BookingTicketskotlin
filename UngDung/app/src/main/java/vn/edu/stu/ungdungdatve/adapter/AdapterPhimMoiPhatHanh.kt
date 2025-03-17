package vn.edu.stu.ungdungdatve.adapter

import android.app.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.models.Phim

class AdapterPhimMoiPhatHanh(
    private val context: Activity,
    private val resource: Int,
    @NonNull private val objects: List<Phim>
) : ArrayAdapter<Phim>(context, resource, objects) {

    @NonNull
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(context).inflate(resource, parent, false)
        val tvtenPhim: TextView = item.findViewById(R.id.tvtenPhim)
        val tvNgayRaMat: TextView = item.findViewById(R.id.tvNgayRaMat)
        val tvTheLoai: TextView = item.findViewById(R.id.tvTheLoai)
        val tvThoiLuong: TextView = item.findViewById(R.id.tvThoiLuong)
        val imgview: ImageView = item.findViewById(R.id.imgview)
        val phim = objects[position]

        tvtenPhim.text = phim.TenPhim
        tvTheLoai.text = phim.KieuPhim
        tvNgayRaMat.text = "Ngày ra mắt ${phim.Ngayramat}"
        tvThoiLuong.text = "${phim.Thoiluong} phút"
        imgview.setImageBitmap(phim.getHinhanh())

        return item
    }
}