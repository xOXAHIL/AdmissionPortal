package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminQueries : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QueryAdapter
    private val firestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_queries)
        recyclerView = findViewById(R.id.queriesUser)

        adapter = QueryAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadDataFromFirestore()

        adapter.setOnItemClickListener(object : QueryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@AdminQueries, AdminQueryAnswer::class.java)
                val query = adapter.getItem(position)
                intent.putExtra("id", query.id)
                intent.putExtra("question", query.question)
                startActivity(intent)
            }
        })
    }

    private fun loadDataFromFirestore() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()

        firestore.collection("queries")
            .get()
            .addOnSuccessListener { result ->
                val queryList = mutableListOf<QueryModel>()
                for (document in result) {
                    val id = document.id
                    val question = document.getString("question") ?: ""
                    val query = QueryModel(id, question, "")
                    queryList.add(query)
                }
                adapter.setData(queryList)
                progressDialog.dismiss()
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                // Handle errors
            }
    }
}