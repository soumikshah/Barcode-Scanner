package com.soumikshah.barcodescanner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soumikshah.barcodescanner.databinding.FragmentStartupScreenBinding

class StartupFragment:Fragment() {

    var binding: FragmentStartupScreenBinding? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartupScreenBinding.inflate(inflater,container,false)
        binding?.welcomeTitle?.text = resources.getString(R.string.welcome_title)
        binding?.welcomeDescription?.text = resources.getText(R.string.welcome_description)

        binding?.startScanning?.setOnClickListener {
            (activity as MainActivity).loadFragment(BarcodeScanning())
        }
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}