package `in`.org.cocsit.collegeadmissionmanagementsystem


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class QueryGoActivity : AppCompatActivity() {
    private lateinit var queryEditText: EditText
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_go)

        queryEditText = findViewById(R.id.detailsBook)
        firestore = FirebaseFirestore.getInstance()
    }

    fun bookNowMe(view: View?) {
        val question = queryEditText.text.toString().trim()

        if (question.isNotEmpty()) {
            addQueryToFirestore(question)
        } else {
            Toast.makeText(this, "Please enter your query", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addQueryToFirestore(question: String) {
        val queryMap = hashMapOf(
            "question" to question
            // Add more fields if needed
        )

        firestore.collection("queries")
            .add(queryMap)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Query added successfully",
                    Toast.LENGTH_SHORT
                ).show()

                // Redirect to QueriesActivity
                val intent = Intent(this, QueryActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to add query: $e",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}