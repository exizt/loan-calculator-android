package kr.asv.apps.loancalculator.activities

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kr.asv.androidutils.AdmobAdapter
import kr.asv.apps.loancalculator.NavigationItemFactory
import kr.asv.apps.loancalculator.R


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
        //NavigationItemFactory.instance.onNavigationItemFirst(this)
        onNavigationItemFirst()

        // Services 초기화 및 인스턴스 가져오기
        //Services.instance

        // Admob 호출
        AdmobAdapter.loadBannerAdMob(adView)
        Settings.System.getString(contentResolver, "firebase.test.lab")

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
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

        //NavigationItemFactory.instance.onNavigationItemSelected(this, item)
        if (!NavigationItemFactory.onItemSelected(this,item, true)) {
            //Snackbar.make(this.currentFocus, "준비중입니다", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
        return true
    }

    /**
     * default 로 로딩하는 fragment
     * navigation menu 의 특정 항목을 불러오게함.
     * 백스택 히스토리에는 기록하지 않는다.
     */
    private fun onNavigationItemFirst() {
        NavigationItemFactory.onItemSelected(this,nav_view.menu.findItem(R.id.nav_calculator_equal_principal), false)
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
        if (IS_DEBUG) {
            if(msg2 == ""){
                Log.d(TAG, msg)
            } else {
                Log.d(TAG, "$msg $msg2")
            }
        }
    }

    companion object {
        private const val TAG = "[EXIZT][MainActivity]"
        private const val IS_DEBUG = false
    }
}
