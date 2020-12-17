package kr.asv.loancalculator.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import kr.asv.loancalculator.app.R
import kr.asv.loancalculator.app.Services
import kr.asv.loancalculator.app.databinding.ActivityReportBinding
import kr.asv.loancalculator.app.fragments.ReportScheduleFragment
import kr.asv.loancalculator.app.fragments.ReportSummaryFragment
import kr.asv.loancalculator.utils.AdmobAdapter


/**
 * 계산 결과를 보여주는 Activity
 */
class ReportActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var binding: ActivityReportBinding
    //private var mViewPager: ViewPager? = null

    // AdView 관련
    private lateinit var adView: AdView
    private var initialLayoutComplete = false
    @Suppress("DEPRECATION")
    private val adaptiveAdSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.adContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_report)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        // 뒤로가기 버튼 활성화
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        // pageAdapter 지정
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // tab container
        binding.viewPagerContainer.adapter = mSectionsPagerAdapter

        binding.viewPagerContainer.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.viewPagerContainer))

        title = if (Services.calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL) {
            getString(R.string.calculate_equal)
        } else {
            getString(R.string.calculate_full)
        }

        // Admob 호출
        AdmobAdapter.initMobileAds(this)
        adView = AdView(this)
        binding.adContainer.addView(adView)
        binding.adContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                adView.adSize = adaptiveAdSize
                adView.adUnitId = resources.getString(R.string.ad_unit_id_banner)
                AdmobAdapter.loadBannerAdMob(adView)
            }
        }
    }

    /**
     * 네비게이션 백버튼 클릭시, 메인액티비티를 recreate 를 하는 것을 방지하려는 목적.
     * @return
     */
    override fun getSupportParentActivityIntent(): Intent? {
        finish()
        return null
    }

    /** Called when leaving the activity  */
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    /** Called when returning to the activity  */
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    /** Called before the activity is destroyed  */
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return ReportSummaryFragment.newInstance()
                1 -> return ReportScheduleFragment.newInstance()
            }
            return ReportSummaryFragment.newInstance()
        }

        override fun getCount(): Int = 2
    }
}
