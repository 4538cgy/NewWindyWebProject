package com.uos.newwindysigolzip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.uos.newwindysigolzip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var backKeyPressedTime = 0
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.activitymain = this@MainActivity



        val webViewSettings = binding.activityMainWebview.settings

        webViewSettings.javaScriptEnabled = true
        webViewSettings.domStorageEnabled = true

        binding.activityMainWebview.webViewClient = WebViewClient()
        binding.activityMainWebview.loadUrl("http://tripadvisor.cafe24.com/")
    }

    override fun onBackPressed() {


        var curTime = System.currentTimeMillis()
        var gapTime = curTime - backKeyPressedTime

        if(binding.activityMainWebview.canGoBack()){
            binding.activityMainWebview.goBack()
        }else if(0<=gapTime && 2000 >= gapTime){
            super.onBackPressed()

        }else{
            backKeyPressedTime = curTime.toInt()
            showSettingPopup()

        }
    }

    fun showSettingPopup(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alert_popup,null)
        val alertDialog = AlertDialog.Builder(this)
                .setTitle("앱을 종료하시겠습니까?")
                .setNeutralButton("아니오",null)
                .setPositiveButton("네") {
                    dialog , which ->
                    finishAffinity()
                }

                .create()


        alertDialog.setView(view)
        alertDialog.show()
    }
}