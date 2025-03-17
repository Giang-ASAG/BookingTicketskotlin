package vn.edu.stu.ungdungdatve.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.models.SuatChieu

class AdapterThoiGian(private val suatChieuList: List<SuatChieu>) :
    RecyclerView.Adapter<AdapterThoiGian.ThoiGianViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ThoiGianViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_danhsachlichchieu, parent, false)
        return ThoiGianViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ThoiGianViewHolder, position: Int) {
        val suatChieu = suatChieuList[position]
        holder.bind(suatChieu)


    }

    override fun getItemCount(): Int {
        return suatChieuList.size
    }

    class ThoiGianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGio: TextView = itemView.findViewById(R.id.tvgio)

        fun bind(suatChieu: SuatChieu) {
            tvGio.setTextColor(Color.WHITE)
            val thoiGian = "[${suatChieu.Thoigianbatdau}]\n[${suatChieu.Thoigianketthuc}]"
            tvGio.text = thoiGian
        }
    }

    fun getItem(pos: Int): SuatChieu {
        return suatChieuList[pos]
    }
}