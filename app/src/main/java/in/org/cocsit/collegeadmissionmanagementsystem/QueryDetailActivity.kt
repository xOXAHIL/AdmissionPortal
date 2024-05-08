package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import `in`.org.cocsit.collegeadmissionmanagementsystem.QueryAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class QueryDetailActivity : AppCompatActivity(), QueryAdapter.OnItemClickListener {
    private var questionTextView: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: QueryAdapter? = null
    private var id: String? = null
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_detail)

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
        Log.d("QueryDetailActivity", "Question: $question")

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

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}