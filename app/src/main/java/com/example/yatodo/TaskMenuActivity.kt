package com.example.yatodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)

        val closeButton = findViewById<FloatingActionButton>(R.id.close_button)
        closeButton.setOnClickListener {
            finish()
        }

        // Popup menu handling
        val popupTextView = findViewById<TextView>(R.id.popup_button)
        popupTextView.setOnClickListener {
            val popupMenu = PopupMenu(this, popupTextView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setForceShowIcon(true)

            // High importance element drawing
            val importanceHighItem = popupMenu.menu.getItem(2)
            importanceHighItem.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.double_exclamation
            )
            val s = SpannableString(importanceHighItem.title)
            s.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),
                0, s.length, 0
            )
            importanceHighItem.title = s

            // Low importance element drawing
            val importanceLowItem = popupMenu.menu.getItem(1)
            importanceLowItem.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.arrow_down
            )
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.importance_none ->
                        popupTextView.text = item.title

                    R.id.importance_low ->
                        popupTextView.text = item.title

                    R.id.importance_high ->
                        popupTextView.text = item.title
                }
                true
            }
            popupMenu.show()
        }

        // Date picker construction
        val constraintsBuilder = CalendarConstraints.Builder()
            .setStart(MaterialDatePicker.todayInUtcMilliseconds())
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pick Deadline date")
            .setSelection(
                MaterialDatePicker.todayInUtcMilliseconds()
            )
            .setCalendarConstraints(
                constraintsBuilder.build()
            )
            .build()

        // Date picker call
        val datePickerIndicator = findViewById<TextView>(R.id.datepicker_indicator)
        val datePickerButton = findViewById<SwitchCompat>(R.id.toggle_date_button)
        datePickerButton.setOnClickListener { it ->
            it as SwitchCompat
            if (it.isChecked) {
                datePicker.show(supportFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener {it2 ->
                    val date = Date(it2)
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    datePickerIndicator.text = sdf.format(date)
                }
            } else {
                datePickerIndicator.text = ""
            }
        }
    }
}