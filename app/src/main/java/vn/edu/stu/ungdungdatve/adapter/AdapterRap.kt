package vn.edu.stu.ungdungdatve.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.models.Rap


class AdapterRap(
    private val context: Activity,
    private val resource: Int,
    @NonNull private val objects: List<Rap>
) : ArrayAdapter<Rap>(context, resource, objects) {

    @NonNull
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(context).inflate(resource, parent, false)
        val tvtenRap: TextView = item.findViewById(R.id.tvTenRap)
        val tvDiaChiRap: TextView = item.findViewById(R.id.tvDiaChiRap)
        val rap = objects[position]

        tvtenRap.text = "Rạp: ${rap.TenRap}"
        tvDiaChiRap.text = "Địa chỉ: ${rap.DiachiRap}"

        return item
    }
}