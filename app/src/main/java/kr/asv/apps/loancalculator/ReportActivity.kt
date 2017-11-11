package kr.asv.apps.loancalculator

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

import kr.asv.apps.loancalculator.fragments.ReportScheduleFragment
import kr.asv.apps.loancalculator.fragments.ReportSummaryFragment

class ReportActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        mViewPager = findViewById<View>(R.id.container)
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        if (Services.getInstance().calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL) {
            title = "원금균등상환"
        } else {
            title = "원리금균등상환"
        }

        loadAdMobBanner(R.id.adView)
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return ReportSummaryFragment.newInstance()
                1 -> return ReportScheduleFragment.newInstance()
            }
            return ReportScheduleFragment.newInstance()
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "요약"
                1 -> return "상환 스케쥴"
            }
            return null
        }
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
}
