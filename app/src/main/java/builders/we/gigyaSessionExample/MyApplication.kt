package builders.we.gigyaSessionExample

import android.app.Application
import com.gigya.android.sdk.Gigya
import com.gigya.android.sdk.GigyaLogger

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Gigya.setApplication(this)
        GigyaLogger.setDebugMode(true)
    }

}