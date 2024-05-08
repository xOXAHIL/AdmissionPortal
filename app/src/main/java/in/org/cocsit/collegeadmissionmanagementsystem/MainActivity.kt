package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

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
        val MainScreenRegistrationCard = findViewById<CardView>(R.id.mainScreenRegistrationCard)
        val MainScreenWalkthroughCard = findViewById<CardView>(R.id.mainScreenWalkthroughCard)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        MainScreenRegistrationCard.setOnClickListener(this) //added onclick listener
        MainScreenWalkthroughCard.setOnClickListener(this)
        val showDialogButton = findViewById<ImageView>(R.id.mainScreenBackImgBtn)
        showDialogButton.setOnClickListener { view: View? -> showExitDialog() }
        checkRegistrationStatus()

    }

    fun showExitDialog() {
        YoYo.with(Techniques.Shake)
            .duration(2000)
            .repeat(0)
            .playOn(findViewById(R.id.mainScreenBackImgBtn))
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.exit_alert_dialog)
        val acceptButton = dialog.findViewById<Button>(R.id.acceptButton)
        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
        acceptButton.setOnClickListener { view: View? ->
            dialog.dismiss()
            Log.e("Exit_App_Yes", "onClick: accept button")
            val handler = Handler() //handel the post delay method with intent to call next activity
            handler.postDelayed({
                finish()
                System.exit(0)
            }, 2000) //value seconds //current value is 2sec
            Toast.makeText(applicationContext, "You choose Yes action", Toast.LENGTH_SHORT).show()
        }
        cancelButton.setOnClickListener { view: View? ->
            Log.e("Exit_App_No", "onClick: cancel button")
            dialog.dismiss()
            Toast.makeText(applicationContext, "You choose No action", Toast.LENGTH_SHORT).show()
        }
        dialog.show()

    }

    private fun checkRegistrationStatus() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            db.collection("registrations")
                .whereEqualTo("secondaryPhone", user.email) // Assuming you store email in the document
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val document = documents.first()
                        val status = document.getString("status")
                        if (status != null) {
                            if (status == "Accepted") {
                                showPopup("Congratulations!", "Your registration request has been accepted.")
                            } else {
                                showPopup("Registration Rejected", "Your registration request has been rejected.")
                            }
                        } else {
                            showToast("Status not found")
                        }
                    } else {
                        showToast("No registration document found")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("MainActivity", "Error checking registration status", e)
                    showToast("Failed to check registration status")
                }
        } ?: showToast("No current user")
    }


    // Function to show a popup
    private fun showPopup(title: String, message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mainScreenRegistrationCard -> {
                val i = Intent(this, Courses::class.java)
                startActivity(i)
            }

            R.id.mainScreenWalkthroughCard -> {
                val j = Intent(this, WalkThroughActivity::class.java)
                startActivity(j)
            }
        }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation item selection
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            R.id.home -> {
                fragment = null
            }
            R.id.queries -> {
                startActivity(Intent(this, QueryActivity::class.java))
                return true
            }
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
            return true
        }
        return false
    }

}