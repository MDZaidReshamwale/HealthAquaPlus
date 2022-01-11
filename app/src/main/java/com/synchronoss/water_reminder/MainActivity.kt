package com.synchronoss.water_reminder

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.synchronoss.water_reminder.helper.SqliteHelper
import com.synchronoss.water_reminder.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var totalIntake: Int = 0
    private var inTook: Int = 0
    private lateinit var sharedPref: SharedPreferences
    private lateinit var dateNow: String
    private lateinit var sqliteHelper: SqliteHelper
    private var snackbar: Snackbar? = null
    private var selectedOption: Int? = null
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sqliteHelper = SqliteHelper(this)
        setContentView(R.layout.splash_screen_activity)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences(AppUtils.USERS_SHARED_PREF, AppUtils.PRIVATE_MODE)


        totalIntake = sharedPref.getInt(AppUtils.TOTAL_INTAKE, 0)

        if (sharedPref.getBoolean(AppUtils.FIRST_RUN_KEY, true)) {
//            startActivity(Intent(this,SplashScreen::class.java))
            startActivity(Intent(this , WalkThroughActivity::class.java))
            finish()
        } else if (totalIntake <= 0) {
            startActivity(Intent(this, InitUserInfoActivity::class.java))
            finish()
        }

        dateNow = AppUtils.getCurrentDate()!!

    }


    fun updateValues() {
        totalIntake = sharedPref.getInt(AppUtils.TOTAL_INTAKE, 0)

        inTook = sqliteHelper.getIntook(dateNow)

        setWaterLevel(inTook, totalIntake)
    }



    override fun onStart() {
        super.onStart()

        val outValue = TypedValue()
        applicationContext.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )


//        stats listner
        btnStats.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }


//        water intake listner
        op50ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 50
            Toast.makeText(applicationContext,"You have selected $selectedOption ml ",Toast.LENGTH_SHORT).show()
            op50ml.background = getDrawable(R.drawable.option_select_bg)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op100ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 100
            Toast.makeText(applicationContext,"You have selected $selectedOption ml ",Toast.LENGTH_SHORT).show()
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(R.drawable.option_select_bg)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op150ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 150
            Toast.makeText(applicationContext,"You have selected $selectedOption ml ",Toast.LENGTH_SHORT).show()
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(R.drawable.option_select_bg)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op200ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 200
            Toast.makeText(applicationContext,"You have selected $selectedOption ml ",Toast.LENGTH_SHORT).show()
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(R.drawable.option_select_bg)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        op250ml.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }
            selectedOption = 250
            Toast.makeText(applicationContext,"You have selected $selectedOption ml ",Toast.LENGTH_SHORT).show()
            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(R.drawable.option_select_bg)
            opCustom.background = getDrawable(outValue.resourceId)

        }

        opCustom.setOnClickListener {
            if (snackbar != null) {
                snackbar?.dismiss()
            }

            val li = LayoutInflater.from(this)
            val promptsView = li.inflate(R.layout.custom_input_dialog, null)

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setView(promptsView)

            val userInput = promptsView
                .findViewById(R.id.etCustomInput) as TextInputLayout

            alertDialogBuilder.setPositiveButton("OK") { dialog, id ->
                val inputText = userInput.editText!!.text.toString()
                if (!TextUtils.isEmpty(inputText)) {
                    tvCustom.text = "${inputText} ml"
                    selectedOption = inputText.toInt()
                }
            }.setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            op50ml.background = getDrawable(outValue.resourceId)
            op100ml.background = getDrawable(outValue.resourceId)
            op150ml.background = getDrawable(outValue.resourceId)
            op200ml.background = getDrawable(outValue.resourceId)
            op250ml.background = getDrawable(outValue.resourceId)
            opCustom.background = getDrawable(R.drawable.option_select_bg)

        }

    }
        private fun setWaterLevel(inTook: Int, totalIntake: Int) {

            YoYo.with(Techniques.SlideInDown)
                .duration(500)
                .playOn(tvIntook)
            tvIntook.text = "$inTook"
            tvTotalIntake.text = "/$totalIntake ml"
            val progress = ((inTook / totalIntake.toFloat()) * 100).toInt()
            YoYo.with(Techniques.Pulse)
                .duration(500)
                .playOn(intakeProgress)
            intakeProgress.currentProgress = progress
//        if ((inTook * 100 / totalIntake) > 140) {
//            Snackbar.make(main_activity_parent, "You achieved the goal", Snackbar.LENGTH_SHORT)
//                .show()
//        }
        }

        override fun onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Snackbar.make(
                this.window.decorView.findViewById(android.R.id.content),
                "Please click BACK again to exit",
                Snackbar.LENGTH_SHORT
            ).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1000)
        }


    }