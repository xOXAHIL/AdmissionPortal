package `in`.org.cocsit.collegeadmissionmanagementsystem

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

class Courses : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        //add hardware acceleration enable
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        //add notch displays cutout mode to short size
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        val coursesScreenBackButton = findViewById<ImageView>(R.id.courseScreenBackImgBtn)
        coursesScreenBackButton.setOnClickListener { v: View? -> onBackPressed() }

        // following are the courses names cardViews
        val cardViewBscCS = findViewById<CardView>(R.id.courseRegBscCS)
        val cardViewBscSE = findViewById<CardView>(R.id.courseRegBscSE)
        val cardViewBscNT = findViewById<CardView>(R.id.courseRegBscNT)
        val cardViewMscCS = findViewById<CardView>(R.id.courseRegMscCS)
        val cardViewMscSE = findViewById<CardView>(R.id.courseRegMscSE)
        val cardViewMscSA = findViewById<CardView>(R.id.courseRegMscSA)
        val cardViewMscCM = findViewById<CardView>(R.id.courseRegMscCM)
        val cardViewBCA = findViewById<CardView>(R.id.courseRegBCA)
        val cardViewBscBT = findViewById<CardView>(R.id.courseRegBscBT)
        val cardViewMscBT = findViewById<CardView>(R.id.courseRegMscBT)
        val cardViewBBA = findViewById<CardView>(R.id.courseRegBBA)
        val cardViewMBA = findViewById<CardView>(R.id.courseRegMBA)

        // following are listeners to the above cardViews
        cardViewBscCS.setOnClickListener(this)
        cardViewBscSE.setOnClickListener(this)
        cardViewBscNT.setOnClickListener(this)
        cardViewMscCS.setOnClickListener(this)
        cardViewMscSE.setOnClickListener(this)
        cardViewMscSA.setOnClickListener(this)
        cardViewMscCM.setOnClickListener(this)
        cardViewBCA.setOnClickListener(this)
        cardViewBscBT.setOnClickListener(this)
        cardViewMscBT.setOnClickListener(this)
        cardViewBBA.setOnClickListener(this)
        cardViewMBA.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.courseRegBscCS -> showCoursePopup(
                v,
                "B.Sc. Computer Science",
                "Eligibility : 12th Science\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bsc_cs_fy.pdf",
                "cbcs_bsc_cs_sy.pdf",
                "cbcs_bsc_cs_ty.pdf"
            )

            R.id.courseRegBscSE -> showCoursePopup(
                v,
                "B.Sc. Software Engineering",
                "Eligibility : 12th Science\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bsc_se_fy.pdf",
                "cbcs_bsc_se_sy.pdf",
                "cbcs_bsc_se_ty.pdf"
            )

            R.id.courseRegBscNT -> showCoursePopup(
                v,
                "B.Sc. Network Technology",
                "Eligibility : 12th Science\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bsc_nt_fy.pdf",
                "cbcs_bsc_nt_sy.pdf",
                "cbcs_bsc_nt_ty.pdf"
            )

            R.id.courseRegMscCS -> showCoursePopup(
                v,
                "M.Sc. Computer Science",
                "Eligibility : Any Computer UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 29,900.0 Rs.",
                "cbcs_msc_cs_fy.pdf",
                "cbcs_msc_cs_sy.pdf",
                "0"
            )

            R.id.courseRegMscSE -> showCoursePopup(
                v,
                "M.Sc. Software Engineering",
                "Eligibility : Any Computer UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 29,900.0 Rs.",
                "cbcs_msc_se_fy.pdf",
                "cbcs_msc_se_sy.pdf",
                "0"
            )

            R.id.courseRegMscSA -> showCoursePopup(
                v,
                "M.Sc. System Admin. & N/W",
                "Eligibility : Any UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 29,900.0 Rs.",
                "cbcs_msc_sa_fy.pdf",
                "cbcs_msc_sa_sy.pdf",
                "0"
            )

            R.id.courseRegMscCM -> showCoursePopup(
                v,
                "M.Sc. Computer Management",
                "Eligibility : Any UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 29,900.0 Rs.",
                "cbcs_msc_cm_fy.pdf",
                "cbcs_msc_cm_sy.pdf",
                "0"
            )

            R.id.courseRegBCA -> showCoursePopup(
                v,
                "BCA - Bachelor of\nComputer Application",
                "Eligibility : Any 12th\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bca_fy.pdf",
                "cbcs_bca_sy.pdf",
                "cbcs_bca_ty.pdf"
            )

            R.id.courseRegBscBT -> showCoursePopup(
                v,
                "B.Sc. Biotechnology",
                "Eligibility : 12th Science\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bsc_bt_fy.pdf",
                "cbcs_bsc_bt_sy.pdf",
                "cbcs_bsc_bt_ty.pdf"
            )

            R.id.courseRegMscBT -> showCoursePopup(
                v,
                "M.Sc. Biotechnology",
                "Eligibility : Any UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 29,900.0 Rs.",
                "cbcs_msc_bt_fy.pdf",
                "cbcs_msc_bt_sy.pdf",
                "0"
            )

            R.id.courseRegBBA -> showCoursePopup(
                v,
                "BBA - Bachelor of\nBusiness Administration",
                "Eligibility : Any 12th\nDuration : 3 Years\nSemesters : Total 6\nFees/Year : 17,900.0 Rs.",
                "cbcs_bba_fy.pdf",
                "cbcs_bba_sy.pdf",
                "cbcs_bba_ty.pdf"
            )

            R.id.courseRegMBA -> showCoursePopup(
                v,
                "MBA - Master of\nBusiness Administration",
                "Eligibility : Any UG\nDuration : 2 Years\nSemesters : Total 4\nFees/Year : 17,900.0 Rs.",
                "0",
                "0",
                "0"
            )
        }
    }

    fun showCoursePopup(
        view: View?,
        myCourseName: String?,
        myCourseDetails: String?,
        myFyPDF: String,
        mySyPDF: String,
        myTyPDF: String
    ) {
        val dialog = DialogPlus.newDialog(this)
            .setGravity(Gravity.CENTER)
            .setMargin(50, 0, 50, 0)
            .setContentHolder(ViewHolder(R.layout.course_popup))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .create()
        val holderView = dialog.holderView
        val animationView = holderView.findViewById<LottieAnimationView>(R.id.RegWinCautionAnim)
        val openPDFButton1 = holderView.findViewById<Button>(R.id.coursePopupPDFBtn1)
        val openPDFButton2 = holderView.findViewById<Button>(R.id.coursePopupPDFBtn2)
        val openPDFButton3 = holderView.findViewById<Button>(R.id.coursePopupPDFBtn3)
        val registrationButton = holderView.findViewById<Button>(R.id.coursePopupRegBtn)
        val cancelButton = holderView.findViewById<Button>(R.id.coursePopupCancelBtn)
        val textViewTitle = holderView.findViewById<TextView>(R.id.coursePopupTextViewTitle)
        val textViewSubTitle = holderView.findViewById<TextView>(R.id.coursePopupTextViewSubTitle)
        textViewTitle.text = myCourseName
        textViewSubTitle.text = myCourseDetails
        openPDFButton1.setOnClickListener { v: View? ->
            val i = Intent(this, PDFviewer::class.java)
            i.putExtra("myPDFname", myFyPDF)
            this.startActivity(i)
        }
        openPDFButton2.setOnClickListener { v: View? ->
            val i = Intent(this, PDFviewer::class.java)
            i.putExtra("myPDFname", mySyPDF)
            this.startActivity(i)
        }
        openPDFButton3.setOnClickListener { v: View? ->
            val i = Intent(this, PDFviewer::class.java)
            i.putExtra("myPDFname", myTyPDF)
            this.startActivity(i)
        }
        if (myTyPDF.contentEquals("0")) {
            openPDFButton3.visibility = View.INVISIBLE
            openPDFButton3.isClickable = false
            openPDFButton3.isEnabled = false
        }
        if (mySyPDF.contentEquals("0")) {
            openPDFButton2.visibility = View.INVISIBLE
            openPDFButton2.isClickable = false
            openPDFButton2.isEnabled = false
        }
        if (myFyPDF.contentEquals("0")) {
            openPDFButton1.visibility = View.INVISIBLE
            openPDFButton1.isClickable = false
            openPDFButton1.isEnabled = false
            animationView.playAnimation()
        }
        registrationButton.setOnClickListener { v: View? ->
            val i = Intent(this, Registration::class.java)
            startActivity(i)
        }
        cancelButton.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    // Enables regular immersive mode or fullscreen mode
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val decorView = window.decorView
        if (hasFocus) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}