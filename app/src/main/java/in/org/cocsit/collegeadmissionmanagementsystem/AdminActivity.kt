package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class AdminActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val approveAdmission = findViewById<CardView>(R.id.approveAdmission)
        val queriesAnswer=findViewById<CardView>(R.id.QueriesAns)
        val showDialogButton = findViewById<ImageView>(R.id.mainScreenBackImgBtn)
        showDialogButton.setOnClickListener { view: View? -> showExitDialog() }

        approveAdmission.setOnClickListener {
            val intent = Intent(this, AdminAdmission::class.java)
            startActivity(intent)
        }
        queriesAnswer.setOnClickListener {
            val intent = Intent(this, AdminQueries::class.java)
            startActivity(intent)
        }
    }
    fun showExitDialog() {
        YoYo.with(Techniques.Shake)
            .duration(2000)
            .repeat(0)
            .playOn(findViewById(R.id.mainScreenBackImgBtn))
        val dialog = Dialog(this@AdminActivity)
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
}