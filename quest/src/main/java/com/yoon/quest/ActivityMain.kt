package com.yoon.quest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.yoon.quest.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this,R.color.lightGreen)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        try {
            // 툴바
            mBinding.apply {
                setContentView(root)
                initNavigation()
                supportActionBar!!.hide()
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navi) as NavHostFragment
                val navController = navHostFragment.navController
                val appBarConfiguration = AppBarConfiguration(setOf(R.id.fragMain))
                setupActionBarWithNavController(navController, appBarConfiguration)
            }
        } catch (e: Exception) {
            Log.e("checkCheck", e.message.toString())
        }
    }

    private var mBackKeyPressedTime: Long = 0

    override fun onBackPressed() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

//        // fold view 닫기.
//        if (mBinding.foldView.getVisibility() === View.VISIBLE) {
//            clickUnFoldBtn()
//            return
//        }

        if (findNavController(R.id.navi).currentDestination!!.id != R.id.fragMain) {
            findNavController(R.id.navi).popBackStack(R.id.fragMain, false)
            return
        }
        // 두 번 눌러 종료.
        if (System.currentTimeMillis() <= mBackKeyPressedTime + 3000) {
            exitApp()
        }
        if (System.currentTimeMillis() > mBackKeyPressedTime + 3000) {
            mBackKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로가기' 버튼을 한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exitApp() {
        val mmIntent = Intent(this, ActivityLanding::class.java)
        mmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        mmIntent.putExtra(Key.EVENT_APP_EXIT, true)
        startActivity(mmIntent)
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navi)
        NavigationUI.setupWithNavController(mBinding.navigation, navController)
    }
}