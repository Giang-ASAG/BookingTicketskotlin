package vn.edu.stu.ungdungdatve.apiservice

import android.app.Activity
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import vn.edu.stu.ungdungdatve.models.HoaDon
import vn.edu.stu.ungdungdatve.models.KhachHang
import vn.edu.stu.ungdungdatve.models.Phim
import vn.edu.stu.ungdungdatve.models.Rap
import vn.edu.stu.ungdungdatve.models.SuatChieu
import vn.edu.stu.ungdungdatve.models.TinhTrangGhe
import vn.edu.stu.ungdungdatve.models.TinhTrangGheEnum
import vn.edu.stu.ungdungdatve.models.Ve
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime

object ApiService {
    const val API_URL: String = "http://nguyenvangiang.runasp.net/api"
    var khachhang: KhachHang = KhachHang()
    var phim: Phim = Phim()
    var rap: Rap = Rap()
    var suatchieu: SuatChieu = SuatChieu()
    var tinhtrangghe: TinhTrangGhe = TinhTrangGhe()

    fun xuLyGetDSPhim(
        context: Activity,
        callback: Response.Listener<List<Phim>>,
        errorCallback: Response.ErrorListener
    ) {
        val dsp = ArrayList<Phim>()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val ma = obj.getString("maPhim");
                    val ten = obj.getString("tenPhim")
                    val date = LocalDate.parse(obj.getString("ngayramat"))
                    val thoiluong = obj.getInt("thoiluong")
                    val ttp = obj.getString("thongtinPhim")
                    val kieuPhim = obj.getString("kieuPhim")
                    val base64String = obj.getString("hinhanh")
                    val hinhanh = Base64.decode(base64String, Base64.DEFAULT)
                    val _phim = Phim(ma, ten, date, thoiluong, ttp, kieuPhim, hinhanh)
                    dsp.add(_phim)
                }
                callback.onResponse(dsp)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", e.toString())
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            }
        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val builder = Uri.parse("$API_URL/Phim").buildUpon()
        val url = builder.build().toString()
        val request = StringRequest(
            Request.Method.GET, url, listener, errorListener
        )
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // Timeout in milliseconds
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }

    fun xuLyGetDSRap(
        context: Activity, callback: Response.Listener<List<Rap>>, errorCallback: ErrorListener
    ) {
        val dsrap = ArrayList<Rap>()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val ma = obj.getString("maRap")
                    val ten = obj.getString("tenRap")
                    val diachi = obj.getString("diachiRap")
                    val toadoX = obj.getDouble("toaDoX")
                    val toadoY = obj.getDouble("toaDoY")
                    val _rap = Rap(ma, ten, diachi, toadoX, toadoY)
                    dsrap.add(_rap)
                }
                callback.onResponse(dsrap)
            } catch (e: Exception) {
                Log.e("LOI", e.toString())
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            }

        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val builder = Uri.parse("$API_URL/rap").buildUpon()
        val url = builder.build().toString()
        val request = StringRequest(url, listener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }

    fun xuLyGetDSRapKhicoMaPhim(
        context: Activity,
        callback: Response.Listener<List<Rap>>,
        errorCallback: ErrorListener,
        maphim: String
    ) {
        val dsrap = ArrayList<Rap>()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val ma = obj.getString("maRap")
                    val ten = obj.getString("tenRap")
                    val diachi = obj.getString("diachiRap")
                    val toadoX = obj.getDouble("toaDoX")
                    val toadoY = obj.getDouble("toaDoY")
                    val _rap = Rap(ma, ten, diachi, toadoX, toadoY)
                    dsrap.add(_rap)
                }
                callback.onResponse(dsrap)
            } catch (e: Exception) {
                Log.e("LOI", e.toString())
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            }

        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val builder = Uri.parse("$API_URL/rap/$maphim").buildUpon()
        val url = builder.build().toString()
        val request = StringRequest(url, listener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }

    fun getNgayChieu(
        context: Activity,
        callback: Response.Listener<List<SuatChieu>>,
        errorCallback: ErrorListener,
        maphim: String,
        marap: String
    ) {
        val dssc = ArrayList<SuatChieu>()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val masc = obj.getString("maSuatchieu")
                    val maphong = obj.getString("maPhong")
                    val maphim = obj.getString("maPhim")
                    val ngaychieu = LocalDate.parse(obj.getString("ngaychieu"))
                    val thoigianbatdau = LocalTime.parse(obj.getString("thoigianbatdau"))
                    val thoigianket = LocalTime.parse(obj.getString("thoigianketthuc"))

                    var sc =
                        SuatChieu(masc, maphong, maphim, ngaychieu, thoigianbatdau, thoigianket)
                    dssc.add(sc)
                }
                callback.onResponse(dssc)
            } catch (e: Exception) {
                Log.e("LOI", e.toString())
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            }
        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val builder = Uri.parse("$API_URL/suatchieu/$maphim/$marap").buildUpon()
        val url = builder.build().toString()
        val request = StringRequest(url, listener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }

    fun xuLyGetTinhTrangGhe(
        context: Activity,
        errorCallback: ErrorListener,
        masc: String,
        callback: Response.Listener<List<TinhTrangGhe>>
    ) {
        if (masc.isEmpty()) {
            errorCallback.onErrorResponse(VolleyError("Mã số ghế không hợp lệ"))
            return
        }

        val dsttg = ArrayList<TinhTrangGhe>()
        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val maghe = obj.getString("maGhe")
                    val maSuatchieu = obj.getString("maSuatchieu")
                    val tinhtrang = obj.getBoolean("tinhtrang")
                    val tt = if (tinhtrang) {
                        TinhTrangGheEnum.DA_DAT
                    } else {
                        TinhTrangGheEnum.CHUA_DAT
                    }
                    val tinhTrangGhe = TinhTrangGhe(maghe, maSuatchieu, tt)
                    Log.e(
                        "Co lay duoc du lieu k",
                        "${tinhTrangGhe.MaSuatchieu} ${tinhTrangGhe.MaGhe}"
                    )
                    dsttg.add(tinhTrangGhe)
                }
                callback.onResponse(dsttg)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        val url = Uri.parse("$API_URL/TinhTrangGhe/sc/$masc").buildUpon().toString()
        val request = StringRequest(url, listener, errorListener)

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(request)
    }

    fun xuLyCapNhatTinhTrangGhe(
        context: Activity,
        errorCallback: ErrorListener,
        maghe: String,
        callback: Response.Listener<String>
    ) {
        if (maghe.isEmpty()) {
            errorCallback.onErrorResponse(VolleyError("Mã số ghế không hợp lệ"))
            return
        }

        val url = Uri.parse("$API_URL/TinhTrangGhe/update/$maghe").buildUpon().build().toString()

        // Tạo JSON object để gửi
        val jsonObject = JSONObject()
        jsonObject.put("maGhe", maghe)

        val request = object : JsonObjectRequest(Method.PUT, url, jsonObject,
            Response.Listener { response ->
                callback.onResponse(response.toString())
            },
            Response.ErrorListener { error ->
                Log.e("LOI", "Volley error: ${error.toString()}")
                errorCallback.onErrorResponse(error)
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        Volley.newRequestQueue(context).add(request)
    }


    fun xuLyDangKyKhachHang(
        context: Activity, errorCallback: ErrorListener, callback: Response.Listener<KhachHang>,
        taikhoan: String
    ) {
        if (taikhoan.isEmpty()) {
            errorCallback.onErrorResponse(VolleyError("Không hợp lệ"))
            return
        }
        val kh = KhachHang()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val makh = obj.getString("maKh")
                val tenkh = obj.getString("tenKh")
                val gioitinh = obj.getBoolean("gioitinh")
                val dienthoai = obj.getString("dienthoai")
                val email = obj.getString("email")
                val matkhau = obj.getString("matkhau")
                val quyen = obj.getString("quyen")
                khachhang = KhachHang(makh, tenkh, gioitinh, dienthoai, email, matkhau, quyen)
                callback.onResponse(khachhang)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val url = Uri.parse("$API_URL/KhachHang/$taikhoan").buildUpon().toString()
        val request = StringRequest(url, listener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(request)
    }

    fun xuLyGetKhachHang(
        context: Activity, errorCallback: ErrorListener, callback: Response.Listener<KhachHang>,
        taikhoan: String, matkhau: String
    ) {
        if (taikhoan.isEmpty() || matkhau.isEmpty()) {
            errorCallback.onErrorResponse(VolleyError("Không hợp lệ"))
            return
        }
        val kh = KhachHang()
        val requestQueue = Volley.newRequestQueue(context)
        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val makh = obj.getString("maKh")
                val tenkh = obj.getString("tenKh")
                val gioitinh = obj.getBoolean("gioitinh")
                val dienthoai = obj.getString("dienthoai")
                val email = obj.getString("email")
                val matkhau = obj.getString("matkhau")
                val quyen = obj.getString("quyen")
                khachhang = KhachHang(makh, tenkh, gioitinh, dienthoai, email, matkhau, quyen)
                callback.onResponse(khachhang)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }
        val url = Uri.parse("$API_URL/KhachHang/login/$taikhoan/$matkhau").buildUpon().toString()
        val request = StringRequest(url, listener, errorListener)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(request)
    }

    fun xuLyAddKhachHang(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<KhachHang>,
        _kh: KhachHang
    ) {

        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val makh = obj.getString("maKh")
                val tenkh = obj.getString("tenKh")
                val gioitinh = obj.getBoolean("gioitinh")
                val dienthoai = obj.getString("dienthoai")
                val email = obj.getString("email")
                val matkhau = obj.getString("matkhau")
                val quyen = obj.getString("quyen")
                val khachhang = KhachHang(makh, tenkh, gioitinh, dienthoai, email, matkhau, quyen)
                callback.onResponse(khachhang)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Build POST request URL
        val url = Uri.parse("$API_URL/KhachHang").buildUpon().toString()

        // Create StringRequest for POST
        val request = object : StringRequest(
            Method.POST,
            url,
            listener,
            errorListener
        ) {
            override fun getBody(): ByteArray {
                val jsonBody = JSONObject().apply {
                    put("maKh", _kh.MaKh)
                    put("tenKh", _kh.TenKh)
                    put("gioitinh", _kh.Gioitinh)
                    put("dienthoai", _kh.Dienthoai)
                    put("email", _kh.Email)
                    put("matkhau", _kh.Matkhau)
                    put("quyen", "user")
                }
                return jsonBody.toString().toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        // Set retry policy if needed
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }

    fun xuLyCreateHoaDon(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<HoaDon>,
        _hd: HoaDon
    ) {

        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val mahd = obj.getString("maHd")
                val maKh = obj.getString("maKh")
                val ngaylap = LocalDate.parse(obj.getString("ngaylapHd"))
                val tongtien = obj.getString("tongtien").toFloat()
                val qrCode = obj.getString("qrCode").toByteArray()
                val Hoadon = HoaDon(mahd, maKh, ngaylap, tongtien, qrCode)
                callback.onResponse(Hoadon)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Build POST request URL
        val url = Uri.parse("$API_URL/HoaDon").buildUpon().toString()

        // Create StringRequest for POST
        val request = object : StringRequest(
            Method.POST,
            url,
            listener,
            errorListener
        ) {
            override fun getBody(): ByteArray {
                val jsonBody = JSONObject().apply {
                    put("maHd", _hd.MaHd)
                    put("maKh", _hd.MaKh)
                    put("ngaylapHd", _hd.NgaylapHd)
                    put("tongtien", _hd.Tongtien)
                    // Chuyển đổi mã QR sang chuỗi Base64
                    val qrBase64 = Base64.encodeToString(_hd.qrcode, Base64.NO_WRAP)
                    put("qrCode", qrBase64)
                    //put("qrCode", _hd.qrcode)

                }
                return jsonBody.toString().toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        // Set retry policy if needed
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }

    fun xuLyGetDSHoaDonTuKh(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<List<HoaDon>>, makh: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                val hoaDonList = mutableListOf<HoaDon>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val mahd = obj.getString("maHd")
                    val maKh = obj.getString("maKh")
                    val ngaylap = LocalDate.parse(obj.getString("ngaylapHd"))
                    val tongtien = obj.getString("tongtien").toFloat()
                    val qrCode = Base64.decode(
                        obj.getString("qrCode"),
                        Base64.NO_WRAP
                    ) // Decode Base64 to byte array

                    val hoaDon = HoaDon(mahd, maKh, ngaylap, tongtien, qrCode)
                    hoaDonList.add(hoaDon)
                }

                callback.onResponse(hoaDonList) // Pass the list of invoices to the callback
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Build GET request URL for the invoice list
        val url = "$API_URL/HoaDon/kh/$makh"

        // Create StringRequest for GET
        val request = StringRequest(
            Method.GET,
            url,
            listener,
            errorListener
        )

        // Set retry policy if needed
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }


    fun xuLyCreateVe(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<Ve>,
        _ve: Ve
    ) {

        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val mave = obj.getString("maVe")
                val mahd = obj.getString("maHd")
                val maGhe = obj.getString("maGhe")
                val maSuatchieu = obj.getString("maSuatchieu")
                var _ve = Ve(mave, mahd, maGhe, maSuatchieu)
                callback.onResponse(_ve)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Build POST request URL
        val url = Uri.parse("$API_URL/Ve").buildUpon().toString()

        // Create StringRequest for POST
        val request = object : StringRequest(
            Method.POST,
            url,
            listener,
            errorListener
        ) {
            override fun getBody(): ByteArray {
                val jsonBody = JSONObject().apply {
                    put("maVe", _ve.MaVe)
                    put("maHd", _ve.MaHd)
                    put("maGhe", _ve.MaGhe)
                    put("maSuatchieu", _ve.MaSuatchieu)

                }
                return jsonBody.toString().toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        // Set retry policy if needed
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }

    fun xuLyGetVetuHd(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<List<Ve>>,
        maHd: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        // Build GET request URL with query parameter
        val url = Uri.parse("$API_URL/Ve/HD/$maHd").buildUpon().build().toString()

        // Initialize the list to hold Ve objects
        val ds: MutableList<Ve> = mutableListOf()
        val listener = Response.Listener<String> { response ->
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val mave = obj.getString("maVe")
                    val mahd = obj.getString("maHd")
                    val maGhe = obj.getString("maGhe")
                    val maSuatchieu = obj.getString("maSuatchieu")
                    val ve = Ve(mave, mahd, maGhe, maSuatchieu)
                    ds.add(ve)
                }
                callback.onResponse(ds)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Create StringRequest for GET
        val request = StringRequest(
            Method.GET,
            url,
            listener,
            errorListener
        )

        // Set retry policy if needed (optional)
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }

    fun xuLyUpdateKhachHang(
        context: Activity,
        errorCallback: ErrorListener,
        callback: Response.Listener<KhachHang>,
        makh: String,
        tenkh: String,
        dt: String,
        email: String,
        pass: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        // Listener for successful response
        val listener = Response.Listener<String> { response ->
            try {
                Log.e("Update thanh cong", "Update");
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Error: ${e.message}"))
            }
        }

        // Listener for error response
        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        // Build PUT request URL with query parameters
        val url = Uri.parse("$API_URL/KhachHang/$makh")
            .buildUpon()
            .appendQueryParameter("tenkh", tenkh)
            .appendQueryParameter("dienthoai", dt)
            .appendQueryParameter("email", email)
            .appendQueryParameter("matkhau", pass)
            .toString()

        // Create StringRequest for PUT
        val request = object : StringRequest(
            Method.PUT,
            url,
            listener,
            errorListener
        ) {
        }

        // Set retry policy if needed
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to the queue
        requestQueue.add(request)
    }

    fun xuLyGetPhim(
        context: Activity,
        callback: Response.Listener<Phim>,
        errorCallback: Response.ErrorListener,
        maphim: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response) // Phân tích cú pháp đối tượng JSON
                val ma = obj.getString("maPhim")
                val ten = obj.getString("tenPhim")
                val date = LocalDate.parse(obj.getString("ngayramat"))
                val thoiluong = obj.getInt("thoiluong")
                val ttp = obj.getString("thongtinPhim")
                val kieuPhim = obj.getString("kieuPhim")
                val base64String = obj.getString("hinhanh")
                val hinhanh = Base64.decode(base64String, Base64.DEFAULT)

                // Tạo đối tượng Phim và gọi callback
                val phim = Phim(ma, ten, date, thoiluong, ttp, kieuPhim, hinhanh)
                callback.onResponse(phim)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Unexpected error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        val url = Uri.parse("$API_URL/Phim/phim/$maphim").buildUpon().toString() // Đảm bảo định dạng URL chính xác
        val request = StringRequest(
            Request.Method.GET, url, listener, errorListener
        )

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(request)
    }

    fun getSuatChieu(
        context: Activity,
        callback: Response.Listener<SuatChieu>,
        errorCallback: Response.ErrorListener,
        masc: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response)
                val masc = obj.getString("maSuatchieu")
                val maphong = obj.getString("maPhong")
                val maphim = obj.getString("maPhim")
                val ngaychieu = LocalDate.parse(obj.getString("ngaychieu"))
                val thoigianbatdau = LocalTime.parse(obj.getString("thoigianbatdau"))
                val thoigianket = LocalTime.parse(obj.getString("thoigianketthuc"))

                val sc = SuatChieu(masc, maphong, maphim, ngaychieu, thoigianbatdau, thoigianket)
                callback.onResponse(sc) // Pass the SuatChieu object to the callback
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Unexpected error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        val url = Uri.parse("$API_URL/suatchieu/$masc").buildUpon().toString()
        val request = StringRequest(
            Request.Method.GET, // Specify the request method
            url,
            listener,
            errorListener
        )

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        requestQueue.add(request)
    }

    fun xuLyGetRap(
        context: Activity,
        callback: Response.Listener<Rap>,
        errorCallback: Response.ErrorListener,
        maphong: String
    ) {
        val requestQueue = Volley.newRequestQueue(context)

        val listener = Response.Listener<String> { response ->
            try {
                val obj = JSONObject(response) // Phân tích cú pháp đối tượng JSON
                val ma = obj.getString("maRap")
                val ten = obj.getString("tenRap")
                val diachi = obj.getString("diachiRap")
                val toadoX = obj.getDouble("toaDoX")
                val toadoY = obj.getDouble("toaDoY")

                // Tạo đối tượng Rap và gọi callback
                val rap = Rap(ma, ten, diachi, toadoX, toadoY)
                callback.onResponse(rap)
            } catch (e: JSONException) {
                Log.e("LOI", "JSON parsing error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("JSON parsing error"))
            } catch (e: Exception) {
                Log.e("LOI", "Unexpected error: ${e.message}")
                errorCallback.onErrorResponse(VolleyError("Unexpected error"))
            }
        }

        val errorListener = Response.ErrorListener { error ->
            Log.e("LOI", "Volley error: ${error.toString()}")
            errorCallback.onErrorResponse(error)
        }

        val url = Uri.parse("$API_URL/rap/phong/$maphong").buildUpon().toString() // Đảm bảo định dạng URL chính xác
        val request = StringRequest(
            Request.Method.GET,
            url,
            listener,
            errorListener
        )

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(request)
    }
}