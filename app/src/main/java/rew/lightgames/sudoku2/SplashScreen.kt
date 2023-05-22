package rew.lightgames.sudoku2


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        MobileAds.initialize(this) {
            Handler(Looper.getMainLooper()).postDelayed({
                // Start main activity
                startActivity(Intent(this, MenuHostActivity::class.java))
                // close splash activity

                finish()

            }, 2000)
        }
 // delay for 2 seconds
    }
}