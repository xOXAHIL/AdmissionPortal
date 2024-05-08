package `in`.org.cocsit.collegeadmissionmanagementsystem

data class QueryModel(
    val id: String,  // Unique identifier for the query
    val question: String?,  // The question asked
    val answer: String?  // The answer to the question (nullable if not answered yet)
)