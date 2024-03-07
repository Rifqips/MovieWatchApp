package id.rifqipadisiliwangi.movieapp.utils

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import id.rifqipadisiliwangi.movieapp.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

object Constant {

    val TMDb_BACKDROP_PATH = "https://image.tmdb.org/t/p/w500_and_h282_face"
    val REAL_LOCATION = "real_location"
    val REGISTER_PARSE = "register_parse"
    val IMAGE_PARSE = "image_parse"
    val IMAGE_FORMAT = "image/*"
    val COROUSEL_ONE = "https://wallpaperaccess.com/full/2052704.jpg"
    val COROUSEL_TWO = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
    val COROUSEL_THREE = "https://getwallpapers.com/wallpaper/full/c/a/9/75047.jpg"
    val MOVIE_POPULAR = "movie_popular"
    val SEARCH = "search"
    val NOW_PLAYING = "now_playing"
    val CART_DETAIL = "cart_detail"
    val WISHLIST_DETAIL = "wishlist_detail"
    val idn = "in"
    val en = "en"
    val lang_request = "en-US"
    val API_KEY_MOVIE_DB = "51d4336c22284544f84ccdd06444cf17"
    val DELAYED = 2000
    val token_result = "token_result"
    val original_title_result = "original_title_result"
    val release_date_result = "release_date_result"
    val description_result = "description_result"
    val img_result = "img_result"
    val id_result = "id_result"
    val poster_result = "poster_result"
    val original_result = "original_result"


    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    inline fun <reified T> T.toJson(): String {
        return Gson().toJson(this)
    }
    fun Int.toCurrencyFormat(): String {
        val format: android.icu.text.NumberFormat = android.icu.text.NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = android.icu.util.Currency.getInstance("IDR")
        return format.format(this)
    }
fun createFile(application: Application): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir
    val imageFile = File(outputDirectory, "$timeStamp.jpg")
    compressAndSaveImage(imageFile)

    return imageFile
}

    fun compressAndSaveImage(file: File) {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 80
        }
        val bitmap = BitmapFactory.decodeFile(file.path, options)
        bitmap?.let {
            val fos = FileOutputStream(file)
            it.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            fos.close()
        }
    }

    val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    val passwordPattern = Pattern.compile(
        "^(?=.*[A-Z])" +
                "(?=.*[a-z])" +
                "(?=.*\\d)" + //
                "(?=.*[@#\$%^&+=!])" +
                "[A-Za-z\\d@#\$%^&+=!]{8,}"
    )
}