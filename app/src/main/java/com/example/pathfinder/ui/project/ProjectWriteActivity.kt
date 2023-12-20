

package com.example.pathfinder.ui.project

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.databinding.ActivityProjectWriteBinding
import com.example.pathfinder.ui.project.viewmodel.ProjectViewModel
import com.example.pathfinder.utils.FBAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import com.hjq.toast.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectWriteBinding
    private val viewModel : ProjectViewModel by viewModels()
    private var endTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toaster.init(application);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_write)

        initProjectWriteButton()
        setupDatePicker()
    }

    private fun initProjectWriteButton() {
        binding.writeBtn.setOnClickListener {
            val uid = FBAuth.getUid()
            val category = binding.categorySpinner.selectedItem.toString()
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val region = binding.regionArea.text.toString()
            val endTime = endTimeInMillis
            val author = Firebase.auth.currentUser?.displayName
            val errorMessage = "모든 사항을 입력해주세요."

            val projectRequest = ProjectRequest(uid,category, title, content, region, endTime, author.toString())
            //todo: addBoard랑 하나로 통일시킬까?
            if (title.isEmpty() || content.isEmpty() || region.isEmpty() || endTime==0L) {
                Toaster.show(errorMessage)
            } else {
                viewModel.addProject(projectRequest)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun setupDatePicker() {
        binding.endDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                endTimeInMillis = selectedCalendar.timeInMillis

                val selectedDate = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일 까지"
                binding.endDateEditText.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }
    }



}
