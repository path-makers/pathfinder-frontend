package com.example.mysolelife.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mysolelife.R
import com.example.mysolelife.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentHomeBinding
    private var uid: String? = null
    private val datesWithExercises = ArrayList<CalendarDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            uid = firebaseUser.uid
            database = FirebaseDatabase.getInstance().getReference("exerciseRecords/$uid")
        } else {
            // No user is signed in
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val dateParts = snapshot.key?.split("-")?.map { it.toInt() }
//                    if (dateParts != null && dateParts.size == 3) {
//                        datesWithExercises.add(CalendarDay.from(dateParts[0], dateParts[1], dateParts[2]))
//                    }
//                }
//
//                // Add decorator
//                binding.calendarView.addDecorator(EventDecorator(android.R.color.holo_red_dark, datesWithExercises))
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle database error
//            }
//        })

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            val formattedDate = "${date.year}-${date.month + 1}-${date.day}"
            database.child(formattedDate).get().addOnSuccessListener {
                showExerciseDialog(formattedDate, it.value as? String)
            }
        }

        binding.talkTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        binding.storeTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }

        binding.chatButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }

        return binding.root
    }

    private fun showExerciseDialog(date: String, exerciseRecord: String?) {
        val exerciseEditText = EditText(context).apply {
            setText(exerciseRecord ?: "")
        }

        AlertDialog.Builder(context)
            .setTitle("운동 기록")
            .setMessage("$date 의 운동을 기록하세요.")
            .setView(exerciseEditText)
            .setPositiveButton("저장") { _, _ ->
                database.child(date).setValue(exerciseEditText.text.toString())
            }
            .setNegativeButton("취소", null)
            .show()
    }

    class EventDecorator(private val color: Int, dates: Collection<CalendarDay>) : DayViewDecorator {

        private val dates: HashSet<CalendarDay> = HashSet(dates)

        override fun shouldDecorate(day: CalendarDay): Boolean {
//            return dates.contains(day)
            return true
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(DotSpan(5f, color))
        }
    }
}