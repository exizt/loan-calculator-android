package kr.asv.apps.loancalculator.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kr.asv.androidutils.AdmobAdapter
import kr.asv.apps.loancalculator.NavigationItemFactory
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // 네비게이션 드로워 셋팅
        onCreateNavigationDrawer()

        // 첫번째 Fragment 호출
        //NavigationItemFactory.onNavigationItemFirst(this)
        NavigationItemFactory.instance.onNavigationItemFirst(this)

        // Services 초기화 및 인스턴스 가져오기
        Services.getInstance()

        // Admob 호출
        AdmobAdapter.loadBannerAdMob(adView)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    /**
     * 네비게이션 드로워 셋팅
     */
    private fun onCreateNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        //drawer.setDrawerListener(toggle);//deprecated
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        NavigationItemFactory.instance.onNavigationItemSelected(this, item)
        return true
    }


    /**
     * 키보드 내리기
     */
    @Suppress("unused")
    fun hideSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     *
     */
    @Suppress("unused")
    fun debug(msg: String) {
        Log.e("[EXIZT-DEBUG]", "[MainActivity]" + msg)
    }
}
