package kr.asv.apps.loancalculator.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kr.asv.androidutils.AdMobAdapter
import kr.asv.apps.loancalculator.NavigationItemFactory
import kr.asv.apps.loancalculator.R


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var mAdView : AdView
    private val isDebug = false

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*
         * 네비게이션 메뉴 셋팅
         */
        // 네비게이션 드로워 셋팅
        onCreateNavigationDrawer()

        // 첫번째 Fragment 호출
        NavigationItemFactory.onItemFirst(this)
        // << 네비게이션 메뉴 셋팅

        // Services 초기화 및 인스턴스 가져오기
        //Services.instance

        /*
         * Admob 셋팅
         */
        // Admob 초기화
        //MobileAds.initialize(this) {}
        AdMobAdapter.init(this)

        // Admob 로드
        mAdView = findViewById(R.id.adView)
        AdMobAdapter.loadBannerAd(mAdView)
        // << Admob 셋팅

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // Firebase Analytics 초기화
        firebaseAnalytics = Firebase.analytics
    }

    /**
     * 네비게이션 드로워 셋팅
     */
    private fun onCreateNavigationDrawer() {
        val toggle = object: ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideSoftKeyboard()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                hideSoftKeyboard()
            }
        }
        //drawer.setDrawerListener(toggle);//deprecated
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * drawer 형태이고 open 이라면 closeDrawer 호출
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * navigationDrawer 에서 item 을 선택했을 때 발생하는 메서드
     * 해당 항목이 없을 시에는 '준비중입니다' 가 뜨도록 처리
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        @Suppress("ControlFlowWithEmptyBody")
        if (!NavigationItemFactory.onItemSelected(this,item, true)) {
            //Snackbar.make(this.currentFocus, "준비중입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
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
     * 디버깅 메서드
     * @param msg 메시지
     */
    @Suppress("unused")
    private fun debug(msg: String, msg2 : Any = "") {
        @Suppress("ConstantConditionIf")
        val tag = "[EXIZT-LC]"
        val subTag = "(MainActivity)"
        if (isDebug) {
            if(msg2 == ""){
                Log.d(tag, "$subTag $msg")
            } else {
                Log.d(tag, "$subTag $msg $msg2")
            }
        }
    }
}
