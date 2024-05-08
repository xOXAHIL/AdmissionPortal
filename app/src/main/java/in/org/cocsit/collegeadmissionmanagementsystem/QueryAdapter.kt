package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import `in`.org.cocsit.collegeadmissionmanagementsystem.QueryModel
import `in`.org.cocsit.collegeadmissionmanagementsystem.R

class QueryAdapter(private var queryList: MutableList<QueryModel>, private val context: Context) :
    RecyclerView.Adapter<QueryAdapter.QueryViewHolder>(), Filterable {

    private var queryListFull = queryList.toList()
    private var listener: OnItemClickListener? = null

    inner class QueryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var questionTextView: TextView = itemView.findViewById(R.id.textViewQueryQuestion)
        var answerTextView: TextView = itemView.findViewById(R.id.textViewQueryAnswer)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun getItem(position: Int): QueryModel {
        return queryList[position]
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.query_item, parent, false)
        return QueryViewHolder(view)
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val query = queryList[position]
        holder.questionTextView.text = query.question
        holder.answerTextView.text = query.answer

    }

    override fun getItemCount(): Int {
        return queryList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<QueryModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(queryListFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                for (item in queryListFull) {
                    if (item.question!!.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            queryList.clear()
            queryList.addAll(results.values as List<QueryModel>)
            notifyDataSetChanged()
        }
    }

    fun setData(data: List<QueryModel>) {
        queryList.clear()
        queryList.addAll(data)
        queryListFull = data.toList()
        notifyDataSetChanged()
    }
}