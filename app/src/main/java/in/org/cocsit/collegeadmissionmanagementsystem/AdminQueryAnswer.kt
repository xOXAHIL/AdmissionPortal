package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminQueryAnswer : AppCompatActivity(), QueryAdapter.OnItemClickListener {
    private var questionTextView: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: QueryAdapter? = null
    private var id: String? = null
    private val firestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_query_answer)

        questionTextView = findViewById(R.id.textViewQueryQuestion)
        recyclerView = findViewById(R.id.query_ans_reviews_idServiceUsersRecyclerView)

        val bundle = intent.extras
        id = bundle?.getString("id")
        val question = bundle?.getString("question")
        questionTextView?.setText(question)

        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = QueryAdapter(mutableListOf(), this)
        recyclerView?.adapter = adapter

        fetchDataFromFirestore()

        // Print received data
        Log.d("AdminQueryAnswer", "Question: $question")

        val addButton = findViewById<Button>(R.id.addAnswerButton)
        addButton.setOnClickListener {
            showInputDialog()
        }

    }

    override fun onItemClick(position: Int) {
        showInputDialog()
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dailog_reviews, null)

        val editText = view.findViewById<EditText>(R.id.edit_text_input)
        builder.setView(view)
        builder.setPositiveButton("Add Answer") { _, _ ->
            val answer = editText.text.toString().trim()
            if (answer.isNotEmpty()) {
                addAnswerToFirestore(answer)
            }
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun fetchDataFromFirestore() {
        firestore.collection("queries").document(id!!)
            .collection("answers")
            .get()
            .addOnSuccessListener { result ->
                val queryList = mutableListOf<QueryModel>()
                for (document in result) {
                    val answer = document.getString("answer") ?: ""
                    val query = QueryModel(document.id, "", answer)
                    queryList.add(query)
                }
                adapter?.setData(queryList)
            }
    }

    private fun addAnswerToFirestore(answer: String) {
        val answerMap = hashMapOf(
            "answer" to answer
        )

        firestore.collection("queries").document(id!!)
            .collection("answers")
            .add(answerMap)
            .addOnSuccessListener {
                fetchDataFromFirestore() // Refresh data after adding the answer
            }
    }
}