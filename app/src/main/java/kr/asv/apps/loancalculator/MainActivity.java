package kr.asv.apps.loancalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //네비게이션 드로워 셋팅
        onCreateNavigationDrawer();

        //첫번째 Fragment 호출
        NavigationItemFactory.getInstance().onNavigationItemFirst(this);

        //Services 초기화 및 인스턴스 가져오기
        Services services = Services.getInstanceWithInit(this);

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        loadAdMobBanner(R.id.adView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     * 네비게이션 드로워 셋팅
     */
    private void onCreateNavigationDrawer()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 구글 광고 추가할 때에.
     */
    public void loadAdMobBanner(int id)
    {
        AdView mAdView = (AdView) findViewById(id);
        mAdView.loadAd(newAdRequest());
    }

    /**
     * 구글 전면광고 추가할 때에.
     * @param context
     */
    public void loadAdMobInterstitial(Context context)
    {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        interstitialAd.loadAd(newAdRequest());
    }
    /**
     * 구글 광고의 adRequest 를 생성 및 반환
     * @return
     */
    public AdRequest newAdRequest()
    {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("2D81264572D2AB096C895509EDBD419F").build();
        return adRequest;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        NavigationItemFactory.getInstance().onNavigationItemSelected(this,item);
        return true;
    }

    public void replaceFragments(Fragment fragment) {
        replaceFragments(fragment, true);
    }

    public void replaceFragments(Fragment fragment, Boolean backStack) {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);

        if (backStack) {
            fragmentTransaction.addToBackStack(null);//히스토리에 남긴다.
        }
        fragmentTransaction.commit();
    }

    /**
     * 키보드 내리기
     */
    public void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void debug(String msg) {
        Log.e("[SHH DEBUG]", msg);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
