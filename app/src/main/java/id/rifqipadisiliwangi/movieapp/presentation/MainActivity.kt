package id.rifqipadisiliwangi.movieapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.home.HomeViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.en
import id.rifqipadisiliwangi.movieapp.utils.Constant.idn
import id.rifqipadisiliwangi.movieapp.utils.LocalizationUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpLocalization()
        fetchFcm()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    private fun setUpLocalization() {
        viewModel.appLocaleLiveData.observe(this){ isActive ->
            when(isActive){
                true -> {
                    LocalizationUtil.setLocale(this, idn)
                    val appLocale : LocaleListCompat =
                        LocaleListCompat.forLanguageTags(idn)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
                else -> {
                    LocalizationUtil.setLocale(this, en)
                    val appLocale : LocaleListCompat =
                        LocaleListCompat.forLanguageTags(en)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
        }
    }

    private fun fetchFcm(){
        Firebase.messaging.subscribeToTopic("promo")
            .addOnCompleteListener { task ->
                var msg = "Subscribe Success"
                if (!task.isSuccessful) {
                    msg = "Subscribe Failed"
                }
                Log.d("cekSubs", msg)
            }


    }
}