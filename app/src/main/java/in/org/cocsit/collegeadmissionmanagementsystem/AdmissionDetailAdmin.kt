package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

class AdmissionDetailAdmin : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var student: Student

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admission_detail_admin)

        db = FirebaseFirestore.getInstance()

        // Retrieve selected student's data from intent
        student = intent.getParcelableExtra<Student>(EXTRA_STUDENT)!!
        val accept = findViewById<Button>(R.id.accept)
        val reject = findViewById<Button>(R.id.reject)

        accept.setOnClickListener { acceptStudent() }
        reject.setOnClickListener { rejectStudent() }

        // Populate student details
        populateStudentDetails(student)
    }

    private fun acceptStudent() {
        // Update student status in Firestore
        db.collection("registrations")
            .whereEqualTo("primaryPhone", student.primaryPhone)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    for (document in documents) {
                        val newData = hashMapOf<String, Any>(
                            "status" to "Accepted"
                        )
                        db.collection("registrations")
                            .document(document.id)
                            .update(newData)
                            .addOnSuccessListener {
                                // Show success message or trigger notification
                                Toast.makeText(this, "Student accepted!", Toast.LENGTH_SHORT).show()

                                // Send notification to student
                                sendNotification(student.primaryPhone!!, "Your admission has been accepted!")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error updating student status", e)
                                Toast.makeText(this, "Failed to accept student!", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Log.d(TAG, "No such document")
                    Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun rejectStudent() {
        // Update student status in Firestore
        db.collection("registrations")
            .whereEqualTo("primaryPhone", student.primaryPhone)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    for (document in documents) {
                        val newData = hashMapOf<String, Any>(
                            "status" to "Rejected"
                        )
                        db.collection("registrations")
                            .document(document.id)
                            .update(newData)
                            .addOnSuccessListener {
                                // Show success message or trigger notification
                                Toast.makeText(this, "Student rejected!", Toast.LENGTH_SHORT).show()

                                // Send notification to student
                                sendNotification(student.primaryPhone!!, "Your admission has been rejected.")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error updating student status", e)
                                Toast.makeText(this, "Failed to reject student!", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Log.d(TAG, "No such document")
                    Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun sendNotificationToToken(token: String, title: String, body: String) {
        val message = RemoteMessage.Builder(token)
            .setMessageId(java.lang.String.valueOf(System.currentTimeMillis()))
            .setData(mapOf("title" to title, "body" to body))
            .build()

        try {
            FirebaseMessaging.getInstance().send(message)
            Log.d(TAG, "Message sent successfully.")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message: ${e.message}")
        }
    }
    private fun sendNotification(phoneNumber: String, message: String) {
        // Send notification using Firebase Cloud Messaging
        // Assuming phoneNumber is the FCM token of the device

        sendNotificationToToken(phoneNumber, "Notification Title", message)
    }





    private fun populateStudentDetails(student: Student?) {
        student?.let {
            // Find views by their IDs and set their text
            findViewById<TextView>(R.id.studentName).text =      " Name                         :${it.firstName} ${it.middleName} ${it.lastName}"
            findViewById<TextView>(R.id.studentNumber).text =    " Number                       :${it.primaryPhone}"
            findViewById<TextView>(R.id.my10marks).text =        " 10th Marks                   : ${it.my10marks}"
            findViewById<TextView>(R.id.my12marks).text =        " 12th Marks                   : ${it.my12marks}"
            findViewById<TextView>(R.id.myUGmarks).text =        " UG Marks                     : ${it.myUGmarks}"
            findViewById<TextView>(R.id.my10passYear).text =     " Passing year For 10th class  : ${it.my10passYear}"
            findViewById<TextView>(R.id.my12passYear).text =     " Passing year For 12th class  : ${it.my12passYear}"
            findViewById<TextView>(R.id.myUGpassYear).text =     " Passing year For UG          : ${it.myUGpassYear}"
            findViewById<TextView>(R.id.birthDay).text =         " D.O.B                        : ${it.birthDay}/${it.birthMonth}/${it.birthYear}"
            findViewById<TextView>(R.id.bloodGroup).text =       " Blood Group                  : ${it.bloodGroup}"
            findViewById<TextView>(R.id.nationality).text =      " Nationality                  : ${it.nationality}"
            findViewById<TextView>(R.id.gender).text =           " Gender                       : ${it.gender}"
            findViewById<TextView>(R.id.primaryPhone).text =     " Primary Phone Number   : ${it.primaryPhone}"
            findViewById<TextView>(R.id.secondaryPhone).text =   " Secondary Phone Number : ${it.secondaryPhone}"
            findViewById<TextView>(R.id.courseName).text =       " Course Name                  : ${it.courseName}"
            findViewById<TextView>(R.id.permanentAddress).text = " Permanent Address            : ${it.permanentAddress}"
            findViewById<TextView>(R.id.currentAddress).text =   "Current Address               : ${it.currentAddress}"
        }
    }

    companion object {
        const val EXTRA_STUDENT = "extra_student"
        const val TAG = "AdmissionDetailAdmin"
    }
}