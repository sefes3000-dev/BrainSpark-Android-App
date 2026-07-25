package com.app.brainspark

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BrainSparkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // تهيئة AdMob
        MobileAds.initialize(this) {}
    }
}
