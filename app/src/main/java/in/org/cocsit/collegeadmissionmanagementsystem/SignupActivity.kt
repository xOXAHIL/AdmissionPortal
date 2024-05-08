package `in`.org.cocsit.collegeadmissionmanagementsystem
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        val usernameEditText = findViewById<EditText>(R.id.usernameSignup)
        val emailEditText = findViewById<EditText>(R.id.emailSignup)
        val phoneEditText = findViewById<EditText>(R.id.phoneNumSignup)
        val passwordEditText = findViewById<EditText>(R.id.passwordSignup)
        val signUpButton = findViewById<Button>(R.id.SignUpButton)
        val loginText = findViewById<TextView>(R.id.loginText)
        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Sign up successful.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(baseContext, "Sign up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}