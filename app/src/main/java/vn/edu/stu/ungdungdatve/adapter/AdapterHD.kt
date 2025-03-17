package vn.edu.stu.ungdungdatve.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.models.HoaDon

class AdapterHD(
    private val context: Activity,
    private val resource: Int,
    private val objects: List<HoaDon>
) : ArrayAdapter<HoaDon>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(context).inflate(resource, parent, false)
        val tvhd: TextView = item.findViewById(R.id.tvhd)
        val tvdaylaphd: TextView = item.findViewById(R.id.tvdaylaphd)
        val tvTT: TextView = item.findViewById(R.id.tvTT)
        val hd = objects[position]

        tvhd.text = hd.MaHd
        tvdaylaphd.text = hd.NgaylapHd.toString()
        tvTT.text = "${hd.Tongtien} VNĐ"

        return item
    }
}