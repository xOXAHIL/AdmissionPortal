package `in`.org.cocsit.collegeadmissionmanagementsystem
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class Registration : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var fName: EditText? = null
    private var mName: EditText? = null
    private var lName: EditText? = null
    private var my10passYear: EditText? = null
    private var my12passYear: EditText? = null
    private var myUGpassYear: EditText? = null
    private var my10marks: EditText? = null
    private var my12marks: EditText? = null
    private var myUGmarks: EditText? = null
    private var mBirthDay: EditText? = null
    private var mBirthMonth: EditText? = null
    private var mBirthYear: EditText? = null
    private var mBloodGroup: EditText? = null
    private var mNationality: EditText? = null
    private var mGender: EditText? = null
    private var mCourseName: EditText? = null
    private var mPriNo: EditText? = null
    private var mSecNo: EditText? = null
    private var mPerAdd: EditText? = null
    private var mCurAdd: EditText? = null
    private var mNextBtn: Button? = null
    private var mCancelBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // Add hardware acceleration enable
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        // Add notch displays cutout mode to short size
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        fName = findViewById(R.id.RegWinFname)
        mName = findViewById(R.id.RegWinMname)
        lName = findViewById(R.id.RegWinLname)
        my10marks = findViewById(R.id.RegWin10thMarks)
        my12marks = findViewById(R.id.RegWin12thMarks)
        myUGmarks = findViewById(R.id.RegWinUGmarks)
        my10passYear = findViewById(R.id.RegWin10thPassYear)
        my12passYear = findViewById(R.id.RegWin12thPassYear)
        myUGpassYear = findViewById(R.id.RegWinUGpassYear)
        mBirthDay = findViewById(R.id.RegWinBirthDay)
        mBirthMonth = findViewById(R.id.RegWinBirthMonth)
        mBirthYear = findViewById(R.id.RegWinBirthYear)
        mBloodGroup = findViewById(R.id.RegWinBloodGroup)
        mNationality = findViewById(R.id.RegWinNationality)
        mGender = findViewById(R.id.RegWinGender)
        mPriNo = findViewById(R.id.RegWinPrimaryPhone)
        mSecNo = findViewById(R.id.RegWinSecondaryPhone)
        mCourseName = findViewById(R.id.RegWinCourseName)
        mPerAdd = findViewById(R.id.RegWinPermanentAddress)
        mCurAdd = findViewById(R.id.RegWinCurrentAddress)
        mNextBtn = findViewById(R.id.coursePopupRegBtn)
        mCancelBtn = findViewById(R.id.coursePopupCancelBtn)

        mNextBtn?.setOnClickListener { showNextRegDialog() }
        mCancelBtn?.setOnClickListener { showCancelRegDialog() }
    }

    private fun showCancelRegDialog() {
        if (!isFinishing) {
            val dialog = Dialog(this@Registration)
            dialog.setContentView(R.layout.exit_alert_dialog)
            val acceptButton = dialog.findViewById<Button>(R.id.acceptButton)
            val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
            val textView = dialog.findViewById<TextView>(R.id.popupAlertTextView)
            acceptButton.text = "CONTINUE"
            cancelButton.text = "DISMISS"
            textView.text =
                "College of Computer Science\n& Information Technology\n\nOnline Admission Management System\n\nThis App is made possible by Sahil Patiyal by learning from his Teachers. Made with ❤. Thanks for using this app.\n\nWarning: You will lose all the data you entered !\nAre sure you want to go back ?"
            acceptButton.setOnClickListener {
                Log.e("Exit_App_Yes", "onClick: accept button")
                val handler = Handler()
                handler.postDelayed({
                    onBackPressed()
                }, 500)
                Toast.makeText(applicationContext, "You choose Yes action", Toast.LENGTH_SHORT).show()
            }

            cancelButton.setOnClickListener {
                Log.e("Exit_App_No", "onClick: cancel button")
                dialog.dismiss()
                Toast.makeText(applicationContext, "You choose No action", Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }

    private fun showNextRegDialog() {
        val dialog = Dialog(this@Registration)
        dialog.setContentView(R.layout.exit_alert_dialog)
        val acceptButton = dialog.findViewById<Button>(R.id.acceptButton)
        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
        val textView = dialog.findViewById<TextView>(R.id.popupAlertTextView)
        acceptButton.text = "REGISTER"
        cancelButton.text = "DISMISS"
        textView.text =
            "College of Computer Science\n& Information Technology\n\nOnline Admission Management System\n\nThis App is made possible by Sahil Patiyal by learning from his Teachers. Made with ❤. Thanks for using this app.\n\nWarning: By going ahead you will get registered !\nAre sure you want to register ?"
        acceptButton.setOnClickListener {
            sendDataToServer()
            Log.e("Exit_App_Yes", "onClick: accept button")
            val handler = Handler() //handel the post delay method with intent to call next activity
            handler.postDelayed({ onBackPressed() }, 500) //value seconds //current half millisecond
            Toast.makeText(applicationContext, "You choose Yes action", Toast.LENGTH_SHORT).show()
        }
        cancelButton.setOnClickListener {
            Log.e("Exit_App_No", "onClick: cancel button")
            dialog.dismiss()
            Toast.makeText(applicationContext, "You choose No action", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    private fun sendDataToServer() {
        val data = hashMapOf(
            "firstName" to fName?.text.toString(),
            "middleName" to mName?.text.toString(),
            "lastName" to lName?.text.toString(),
            "my10marks" to my10marks?.text.toString(),
            "my12marks" to my12marks?.text.toString(),
            "myUGmarks" to myUGmarks?.text.toString(),
            "my10passYear" to my10passYear?.text.toString(),
            "my12passYear" to my12passYear?.text.toString(),
            "myUGpassYear" to myUGpassYear?.text.toString(),
            "birthDay" to mBirthDay?.text.toString(),
            "birthMonth" to mBirthMonth?.text.toString(),
            "birthYear" to mBirthYear?.text.toString(),
            "bloodGroup" to mBloodGroup?.text.toString(),
            "nationality" to mNationality?.text.toString(),
            "gender" to mGender?.text.toString(),
            "primaryPhone" to mPriNo?.text.toString(),
            "secondaryPhone" to mSecNo?.text.toString(),
            "courseName" to mCourseName?.text.toString(),
            "permanentAddress" to mPerAdd?.text.toString(),
            "currentAddress" to mCurAdd?.text.toString(),
        )

        db.collection("registrations")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
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

    companion object {
        const val TAG = "Registration"
    }
}