package id.rifqipadisiliwangi.movieapp

import android.app.Application
import id.rifqipadisiliwangi.core.di.CoreModule
import id.rifqipadisiliwangi.movieapp.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
            modules(CoreModule.coreModule)
        }
    }
}