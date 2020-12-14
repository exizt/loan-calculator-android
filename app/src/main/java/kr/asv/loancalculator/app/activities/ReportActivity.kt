package kr.asv.apps.loancalculator.activities

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
import kr.asv.loancalculator.utils.AdmobAdapter
import kr.asv.apps.loancalculator.R
import kr.asv.loancalculator.app.Services
import kr.asv.apps.loancalculator.databinding.ActivityReportBinding
import kr.asv.loancalculator.app.fragments.ReportScheduleFragment
import kr.asv.loancalculator.app.fragments.ReportSummaryFragment

