package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val students: MutableList<Student>, private val listener: OnItemClickListener) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onAcceptClick(position: Int)
        fun onRejectClick(position: Int)
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val studentNumber: TextView = itemView.findViewById(R.id.studentNumber)
        val studentCourse: TextView = itemView.findViewById(R.id.studentCourse)
        val cardView: CardView = itemView.findViewById(R.id.itemCard)

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = students[position]

        val fullName = "${currentItem.firstName} ${currentItem.middleName} ${currentItem.lastName}"
        holder.studentName.text = fullName
        holder.studentNumber.text = currentItem.primaryPhone
        holder.studentCourse.text = currentItem.courseName

        holder.cardView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = students.size
}
