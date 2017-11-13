package kr.asv.apps.loancalculator.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_report.*
import kr.asv.apps.loancalculator.R
import kr.asv.apps.loancalculator.Services
import kr.asv.apps.loancalculator.fragments.ReportScheduleFragment
import kr.asv.apps.loancalculator.fragments.ReportSummaryFragment
import kr.asv.util.AdmobAdapter

class ReportActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    //private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        setSupportActionBar(toolbar)
        supportActionBar?.run{
            setDisplayHomeAsUpEnabled(true)
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        //mViewPager = findViewById(R.id.container)
        //mViewPager!!.adapter = mSectionsPagerAdapter
        container.adapter = mSectionsPagerAdapter

        //val tabLayout:TabLayout = findViewById(R.id.tabs)
        //tabLayout.setupWithViewPager(mViewPager)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        if (Services.getInstance().calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL) {
            title = "원금균등상환"
        } else {
            title = "원리금균등상환"
        }

        // Admob 호출
        AdmobAdapter.loadBannerAdMob(adView)
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
    }
}
