package com.soumikshah.barcodescanner

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.soumikshah.barcodescanner.databinding.FragmentBarcodeScanningBinding
import java.lang.Exception

class BarcodeScanning:Fragment() {

    var binding: FragmentBarcodeScanningBinding?= null
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private var valueType = 0

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBarcodeScanningBinding.inflate(inflater,container,false)

        setupControls()
        val aniSlide: Animation = AnimationUtils.loadAnimation(
            requireActivity(), R.anim.scanner_animation)
        binding?.barcodeLine?.startAnimation(aniSlide)

        binding?.rescan?.setOnClickListener {

            binding?.cameraSurfaceView?.holder?.let { it1 -> cameraSource.start(it1) }
            binding?.barcodeLine?.startAnimation(aniSlide)
            binding?.rescan?.visibility = View.GONE
        }

        binding?.scannedText?.setOnLongClickListener(View.OnLongClickListener {
            copyToClipboard(binding?.scannedText?.text.toString(),
                binding?.root!!
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.copy_to_clipboard),
                Toast.LENGTH_SHORT
            ).show()
            true
        })
        return binding?.root
    }

    private fun copyToClipboard(text: CharSequence, view:View){
        val clipboard = ContextCompat.getSystemService(view.context, ClipboardManager::class.java)
        clipboard?.setPrimaryClip(ClipData.newPlainText("",text))
    }
    private fun setupControls(){
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(),barcodeDetector)
            .setRequestedPreviewSize(1920,1080)
            .setAutoFocusEnabled(true)
            .build()

        binding?.cameraSurfaceView?.holder?.addCallback(object: SurfaceHolder.Callback{
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    //start preview after 1s delay
                    cameraSource.start(p0)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            @SuppressLint("MissingPermission")
            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                try {
                    cameraSource.start(p0)
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object: Detector.Processor<Barcode>{
            override fun release() {
                Toast.makeText(requireContext(),"Scanner has been closed!", Toast.LENGTH_LONG).show()
            }
            override fun receiveDetections(p0: Detector.Detections<Barcode>) {
                val barcodes = p0.detectedItems
                if(barcodes.size()>0){
                    val scannedBarcode: Barcode = barcodes.valueAt(0)
                    if(barcodes.size() ==1){
                        Log.d("Scanner","${scannedBarcode.format}, ${scannedBarcode.valueFormat}")
                        scannedValue = scannedBarcode.rawValue
                        valueType = scannedBarcode.valueFormat
                        requireActivity().runOnUiThread {
                            cameraSource.stop()
                            binding?.barcodeLine?.clearAnimation()
                            binding?.barcodeLine?.visibility = View.GONE
                            binding?.rescan?.visibility = VISIBLE
                            checkValueType(scannedBarcode,valueType)
                        }
                    }else{
                        Toast.makeText(requireContext(),"value -else", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun checkValueType(scannedBarcode:Barcode,valueType:Int){
        when(valueType){
            Barcode.WIFI ->{
                binding?.scannedText?.text = "SSID - ${scannedBarcode.wifi.ssid} \n" +
                        "Password - ${scannedBarcode.wifi.password} \n" +
                        "Encryption type - ${scannedBarcode.wifi.encryptionType}"
            }
            Barcode.URL ->{
                binding?.scannedText?.text = "URL -${scannedBarcode.url.title}, ${scannedBarcode.url.url}"
            }
            Barcode.PRODUCT ->{
                binding?.scannedText?.text = "Product - ${scannedBarcode.displayValue}"
            }
            Barcode.EMAIL ->{
                binding?.scannedText?.text = "Email address- ${scannedBarcode.email.address}\n, " +
                        "${scannedBarcode.email.subject}\n,${scannedBarcode.email.body}\n," +
                        "${scannedBarcode.email.type}"
            }
            Barcode.PHONE -> {
                binding?.scannedText?.text = "Phone number- ${scannedBarcode.phone.number}"
            }
            else ->{
                binding?.scannedText?.text = scannedValue
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }
}