package com.yoon.quest

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yoon.quest.databinding.ActivityLandingBinding
import timber.log.Timber
import timber.log.Timber.DebugTree
import kotlin.system.exitProcess

class ActivityLanding : AppCompatActivity() {
    private val This = this
    private lateinit var mBinding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this,R.color.purple)
        mBinding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Timber.plant(DebugTree())
        val delayHandler = Handler()
        delayHandler.postDelayed({ goToActivityMain() }, 2000)

        val mmAnimation = mBinding.frogImage.background as AnimationDrawable
        mBinding.frogImage.post { mmAnimation.start() }

        // 종료
        if (intent.getBooleanExtra(Key.EVENT_APP_EXIT, false)) {
            finishAndRemoveTask()
            exitProcess(0)
        }
    }

    private fun goToActivityMain() {
        val mmIntent = Intent(This, ActivityMain::class.java)
        startActivity(mmIntent)
    }
}