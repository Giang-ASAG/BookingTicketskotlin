package vn.edu.stu.ungdungdatve.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.models.TinhTrangGhe
import vn.edu.stu.ungdungdatve.models.TinhTrangGheEnum

class AdapterSeat(private val ghes: List<TinhTrangGhe>) :
    RecyclerView.Adapter<AdapterSeat.SeatViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_danhsachghe, parent, false)
        return SeatViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: SeatViewHolder, position: Int) {
        val seat = ghes[position]
        holder.bind(seat)
    }

    override fun getItemCount(): Int {
        return ghes.size
    }

    class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(seat: TinhTrangGhe) {
            val seatTextView = itemView.findViewById<TextView>(R.id.seatTextView)
            val lastTwoChars = seat.MaGhe.substring(seat.MaGhe.length - 2)
            seatTextView.text = lastTwoChars.reversed()
            if (seat.Tinhtrang == TinhTrangGheEnum.DA_DAT) {
                itemView.setBackgroundResource(R.drawable.seats_booked)
            } else if (seat.Tinhtrang == TinhTrangGheEnum.CHUA_DAT) {
                itemView.setBackgroundResource(R.drawable.seats_empty)
            }
        }
    }
}