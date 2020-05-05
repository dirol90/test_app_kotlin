package com.test.testapp1202

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.test.testapp1202.view.MainFragment
import com.test.testapp1202.view.SlideFragment
import com.test.testapp1202.viewmodel.MainViewModel
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (isTablet()) {
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            drawerLayout = findViewById(R.id.drawer_layout)
            val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (isOnline(this)) {
            if (savedInstanceState == null) {
                val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
                setFragment(MainFragment())
            }
        } else {
            Toast.makeText(
                this,
                "Sorry, no internet connection, try again later",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun startGaleryFragment() {
        setFragment(SlideFragment())
    }

    private fun setFragment(fragment: Fragment?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fl, fragment!!)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity()
            } else {
                exitProcess(0)
            }
        }
        super.onBackPressed()
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    fun isTablet(): Boolean {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val widthPixels = metrics.widthPixels
        val heightPixels = metrics.heightPixels

        val widthDpi = metrics.xdpi
        val heightDpi = metrics.ydpi

        val widthInches = widthPixels / widthDpi
        val heightInches = heightPixels / heightDpi

        val diagonalInches =
            Math.sqrt((widthInches * widthInches + heightInches * heightInches).toDouble())

        return diagonalInches >= 6.89

    }

}
