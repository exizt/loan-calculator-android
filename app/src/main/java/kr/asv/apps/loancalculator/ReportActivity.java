package kr.asv.apps.loancalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import kr.asv.apps.loancalculator.fragments.ReportScheduleFragment;
import kr.asv.apps.loancalculator.fragments.ReportSummaryFragment;

public class ReportActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if(Services.getInstance().calculatorMethod == Services.CalculatorMethods.EQUAL_PRINCIPAL)
        {
            setTitle("원금균등상환");
        } else {
            setTitle("원리금균등상환");
        }

        loadAdMobBanner(R.id.adView);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ReportSummaryFragment.newInstance();
                case 1:
                    return ReportScheduleFragment.newInstance();
            }
            return ReportScheduleFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "요약";
                case 1:
                    return "상환 스케쥴";
            }
            return null;
        }
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
}
