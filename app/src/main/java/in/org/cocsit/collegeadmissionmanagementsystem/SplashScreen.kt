package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //add hardware acceleration enable
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        //add notch displays cutout mode to short size
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        YoYo.with(Techniques.FadeInUp)
            .duration(2000)
            .repeat(0)
            .playOn(findViewById(R.id.SplashScreenLogo))
        YoYo.with(Techniques.FadeIn)
            .duration(2000)
            .repeat(0)
            .playOn(findViewById(R.id.SplashScreenTitle))
        YoYo.with(Techniques.FadeInUp)
            .duration(2000)
            .repeat(0)
            .playOn(findViewById(R.id.SplashScreenSubTitle))
        val handler = Handler() //handel the post delay method with intent to call next activity
        handler.postDelayed({
            startActivity(Intent(this@SplashScreen, SignupActivity::class.java))
            finish() //permanently closes the previous activity
        }, 3000) //value seconds //current value is 2sec
    }

    // Enables regular immersive mode or fullscreen mode
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val decorView = window.decorView
        if (hasFocus) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}