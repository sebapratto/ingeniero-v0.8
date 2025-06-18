package ar.com.mychallenge

import android.app.Application
import ar.com.mychallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IngenieroApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IngenieroApp)
            modules(appModule)
        }
    }
}