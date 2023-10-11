

package com.example.pathfinder.pages.teamBuilding

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityTeamBuildingWriteBinding
import com.example.pathfinder.utils.FBAuth
import com.example.pathfinder.utils.FBRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class TeamBuildingWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamBuildingWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_building_write)

        setupButtonClickListener()

        setupDatePicker()
    }

    private fun setupButtonClickListener() {
        binding.writeBtn.setOnClickListener {

            val category = binding.categorySpinner.selectedItem.toString()
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val regionArea = binding.regionArea.text.toString()
            val recruitTime = binding.endDateEditText.text.toString()+"까지"
            val uploadTime = FBAuth.getTime()
            val displayName = Firebase.auth.currentUser?.displayName

            val key = FBRef.boardRef.push().key.toString()

            FBRef.teamBuildingRef
                .child(key)
                .setValue(TeamModel(category, title, content, regionArea, recruitTime, displayName, uploadTime))

            finish()
        }
    }

    private fun setupDatePicker() {
        binding.endDateEditText.setOnClickListener {
            showDatePickerDialog(binding.endDateEditText)
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
            editText.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

}
