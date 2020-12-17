package kr.asv.loancalculator.app.fragments

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.asv.loancalculator.app.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    // view binding
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    /**
     * onCreateView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayAbout()
    }

    private fun displayAbout(){
        activity?.let {
            binding.appVersion.text = getAppVersion(it.applicationContext)
        }
    }

    /**
     * 이 앱의 버전 조회
     */
    private fun getAppVersion(context: Context): String{
        var version = ""
        try {
            val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return version
    }

    /**
     * view 소멸 이벤트
     * view binding 메모리 해제 구문 추가.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}