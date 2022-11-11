package com.example.store.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.store.R

class FiltersDialogFragment:DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_custom_dialog, container, false)
        rootView.findViewById<Button>(R.id.btnDecline).setOnClickListener {
            dismiss()
        }
        rootView.findViewById<Button>(R.id.btnAccept).setOnClickListener {
            dismiss()
        }
        return rootView
    }
}