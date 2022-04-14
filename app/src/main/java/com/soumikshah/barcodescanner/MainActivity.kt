package com.soumikshah.barcodescanner

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View.GONE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.soumikshah.barcodescanner.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val requestCodeCameraPermission = 1001

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED){
            askForCameraPermission()
        }else{
            loadFragment(StartupFragment())
        }
    }

    fun loadFragment(someFragment: Fragment){
        val fragment: Fragment = someFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadFragment(StartupFragment())
            } else {
                Toast.makeText(applicationContext, "Permission Denied, Closing application!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}