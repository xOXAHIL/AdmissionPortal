// AdminAdmission.kt
package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminAdmission : AppCompatActivity(), StudentAdapter.OnItemClickListener {
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_admission)

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        students = mutableListOf()
        adapter = StudentAdapter(students, this) // Passing this as the listener
        recyclerView.adapter = adapter

        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        db.collection("registrations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val student = document.toObject(Student::class.java)
                    students.add(student)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onItemClick(position: Int) {
        // Handle click on a student card view
        val selectedStudent = students[position]

        // TODO: Show detailed information of selected student
        // You can start a new activity or show a dialog with the student's details
        val intent = Intent(this, AdmissionDetailAdmin::class.java)
        intent.putExtra(AdmissionDetailAdmin.EXTRA_STUDENT, selectedStudent)
        startActivity(intent)
    }

    override fun onAcceptClick(position: Int) {
        // TODO: Implement accept action
    }

    override fun onRejectClick(position: Int) {
        // TODO: Implement reject action
    }

    companion object {
        const val TAG = "AdminAdmission"
    }
}
