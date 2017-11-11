package kr.asv.apps.loancalculator.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kr.asv.apps.loancalculator.NavigationItemFactory
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //네비게이션 드로워 셋팅
        onCreateNavigationDrawer()

        //첫번째 Fragment 호출
        //NavigationItemFactory.onNavigationItemFirst(this)
        NavigationItemFactory.instance.onNavigationItemFirst(this)

        //Services 초기화 및 인스턴스 가져오기
        val services = Services.getInstanceWithInit(this)

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        loadAdMobBanner(R.id.adView)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    /**
     * 네비게이션 드로워 셋팅
     */
    private fun onCreateNavigationDrawer() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    /**
     * 구글 광고 추가할 때에.
     */
    fun loadAdMobBanner(id: Int) {
        val mAdView = findViewById<View>(id) as AdView
        mAdView.loadAd(newAdRequest())
    }

    /**
     * 구글 전면광고 추가할 때에.
     * @param context
     */
    fun loadAdMobInterstitial(context: Context) {
        val interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = resources.getString(R.string.banner_ad_unit_id)
        interstitialAd.loadAd(newAdRequest())
    }

    /**
     * 구글 광고의 adRequest 를 생성 및 반환
     * @return
     */
    fun newAdRequest(): AdRequest {
        return AdRequest.Builder().addTestDevice("2D81264572D2AB096C895509EDBD419F").build()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        NavigationItemFactory.instance.onNavigationItemSelected(this, item)
        return true
    }

    @JvmOverloads
    fun replaceFragments(fragment: Fragment, backStack: Boolean? = true) {
        val fragmentManager: FragmentManager
        fragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction
        fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)

        if (backStack!!) {
            fragmentTransaction.addToBackStack(null)//히스토리에 남긴다.
        }
        fragmentTransaction.commit()
    }

    /**
     * 키보드 내리기
     */
    fun hideSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun debug(msg: String) {
        Log.e("[SHH DEBUG]", msg)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.setTitle(title)
    }
}