package vn.edu.stu.ungdungdatve.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import vn.edu.stu.ungdungdatve.R
import vn.edu.stu.ungdungdatve.apiservice.ApiService
import vn.edu.stu.ungdungdatve.fragment.DanhSachPhimFragment
import vn.edu.stu.ungdungdatve.fragment.DanhSachRapFragment
import vn.edu.stu.ungdungdatve.fragment.QuanLyThongTinFragment
import vn.edu.stu.ungdungdatve.fragment.TrangChuFragment
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap
import vn.edu.stu.ungdungdatve.models.SuatChieu
import vn.edu.stu.ungdungdatve.models.TinhTrangGhe

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        // Thiết lập Fragment mặc định
        if (savedInstanceState == null) {
            replaceFragment(TrangChuFragment())
        }
//        val phim = intent.getSerializableExtra("PHIM") as? Phim
//        if (phim != null) {
//            // Nếu nhận được phim, chuyển đến DanhSachRapFragment
//            replaceFragment(DanhSachRapFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable("PHIM", phim)
//                }
//                bottomNavigationView.selectedItemId=R.id.nav_danh_sach_rap
//                bottomNavigationView.visibility = View.GONE
//            })
//
//        }
        var currentTabId: Int = R.id.nav_home // Khởi tạo ID tab hiện tại
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (currentTabId == item.itemId) {
                return@setOnNavigationItemSelectedListener false // Không làm gì nếu tab đang được chọn
            }

            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> TrangChuFragment()
                R.id.nav_danh_sach_phim -> DanhSachPhimFragment()
                R.id.nav_danh_sach_rap -> DanhSachRapFragment()
                R.id.nav_menu -> QuanLyThongTinFragment()
                else -> return@setOnNavigationItemSelectedListener false
            }

            replaceFragment(selectedFragment)
            currentTabId = item.itemId // Cập nhật ID tab hiện tại
            true // Trả về true để cho biết rằng sự kiện đã được xử lý
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Thay thế Fragment hiện tại bằng Fragment mới
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null) // Thêm vào back stack nếu cần
        transaction.commit()
    }
}