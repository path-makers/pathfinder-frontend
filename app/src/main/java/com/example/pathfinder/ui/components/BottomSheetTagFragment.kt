package com.example.pathfinder.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pathfinder.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTagFragment(private val tagListener: (List<String>) -> Unit) : BottomSheetDialogFragment() {

    private val selectedTags = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_tag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)
        val tagsButtons = listOf(
            view.findViewById<Button>(R.id.tagItem1),
            view.findViewById<Button>(R.id.tagItem2),
            view.findViewById<Button>(R.id.tagItem3),
            view.findViewById<Button>(R.id.tagItem4),
            view.findViewById<Button>(R.id.tagItem5),
            view.findViewById<Button>(R.id.tagItem6),
            view.findViewById<Button>(R.id.tagItem7),
            view.findViewById<Button>(R.id.tagItem8),
            view.findViewById<Button>(R.id.tagItem9),
            view.findViewById<Button>(R.id.tagItem10)
        )

        tagsButtons.forEach { tagButton ->
            tagButton.setOnClickListener {
                toggleTagSelection(tagButton.text.toString(), tagButton)
            }
        }

        btnConfirm.setOnClickListener {
            tagListener(selectedTags)
            dismiss()
        }
    }

    private fun toggleTagSelection(tag: String, tagButton: Button) {
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag)
            tagButton.isSelected = false
        } else {
            selectedTags.add(tag)
            tagButton.isSelected = true
        }
    }
}
