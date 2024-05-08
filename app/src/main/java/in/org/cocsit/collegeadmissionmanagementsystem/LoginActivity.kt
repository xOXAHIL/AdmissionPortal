package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import `in`.org.cocsit.collegeadmissionmanagementsystem.AdminActivity
import `in`.org.cocsit.collegeadmissionmanagementsystem.MainActivity
import `in`.org.cocsit.collegeadmissionmanagementsystem.R
import `in`.org.cocsit.collegeadmissionmanagementsystem.SignupActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            signIn(email, password)
        }

        val signupText = findViewById<TextView>(R.id.signupText)
        signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
    private fun getCurrentUserEmail(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.email ?: ""
    }
    private fun checkRegistrationStatus(email: String, password: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("registrations")
            .whereEqualTo("secondaryPhone", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.first()
                    val status = document.getString("status")
                    if (status != null) {
                        if (status == "Accepted") {
                            showPopup("Congratulations!", "Your registration request has been accepted.")
                            signIn(email, password)
                        } else {
                            showPopup("Registration Rejected", "Your registration request has been rejected.")
                        }
                    } else {
                        showToast("Status not found")
                    }
                } else {
                    showToast("No registration found")
                }
            }
            .addOnFailureListener { e ->
                Log.e("LoginActivity", "Error checking registration status", e)
                showToast("Failed to check registration status")
            }
    }
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

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, check if the user is admin
                    val user = auth.currentUser
                    checkAdmin(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkAdmin(user: FirebaseUser?) {
        if (user != null) {
            val uid = user.uid
            // Replace "admin_uid_here" with the UID of your admin user
            if (uid == "10sKu0qY1rPwha5OvpeJadnaNv03") {
                // Navigate to admin activity
                startActivity(Intent(this, AdminActivity::class.java))
            } else {
                // Navigate to user profile activity
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }
    }
}