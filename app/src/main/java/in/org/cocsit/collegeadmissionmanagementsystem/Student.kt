package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.os.Parcel
import android.os.Parcelable

data class Student(
    val birthDay: String?,
    val birthMonth: String?,
    val birthYear: String?,
    val bloodGroup: String?,
    val courseName: String?,
    val currentAddress: String?,
    val firstName: String?,
    val gender: String?,
    val lastName: String?,
    val middleName: String?,
    val my10marks: String?,
    val my10passYear: String?,
    val my12marks: String?,
    val my12passYear: String?,
    val myUGmarks: String?,
    val myUGpassYear: String?,
    val nationality: String?,
    val permanentAddress: String?,
    val primaryPhone: String?,
    val secondaryPhone: String?,
    val status: String?,
) : Parcelable {
    // Empty constructor required by Firebase
    constructor() : this(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,null
    )

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(birthDay)
        parcel.writeString(birthMonth)
        parcel.writeString(birthYear)
        parcel.writeString(bloodGroup)
        parcel.writeString(courseName)
        parcel.writeString(currentAddress)
        parcel.writeString(firstName)
        parcel.writeString(gender)
        parcel.writeString(lastName)
        parcel.writeString(middleName)
        parcel.writeString(my10marks)
        parcel.writeString(my10passYear)
        parcel.writeString(my12marks)
        parcel.writeString(my12passYear)
        parcel.writeString(myUGmarks)
        parcel.writeString(myUGpassYear)
        parcel.writeString(nationality)
        parcel.writeString(permanentAddress)
        parcel.writeString(primaryPhone)
        parcel.writeString(secondaryPhone)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}
