package kr.asv.androidutils

import android.content.Context
import android.provider.Settings
import com.google.android.gms.ads.*
import kr.asv.apps.loancalculator.BuildConfig

/**
 * AdMob 관련 클래스
 * Created by EXIZT on 2017-11-14.
 */
class AdMobAdapter {
    companion object {
        private var isFirebaseTestLab = false

        /**
         * 초기화 관련.
         */
        fun init(context: Context){
            // firebaseTestLab 에서 실행중일 때 에는 동작하지 않도록 함.
            isFirebaseTestLab = isFirebaseTestLab(context)
            if(isFirebaseTestLab) return

            // MobileAds 초기화. 국가에 따라서는 광고 동의 같은 것을 display 함.
            MobileAds.initialize(context) {}

            // Test 기기를 등록 (디버그 모드에서만 등록함. 릴리즈에서는 등록하지 않음.)
            if(BuildConfig.DEBUG) {
                setTestDeviceIds()
            }
        }
        /**
         * 구글 배너 광고 추가할 때에 사용하는 메서드
         */
        @Suppress("unused")
        fun loadBannerAd(mAdView: AdView) {
            // Firebase Test Lab 에서는 실행하지 않게 함.
            if(isFirebaseTestLab) return
            mAdView.loadAd(AdRequest.Builder().build())
        }

        /**
         * 구글 전면광고 추가할 때에.
         * @param context
         */
        @Suppress("unused")
        fun loadInterstitialAd(context: Context, adUnitId: String) {
            // Firebase Test Lab 에서는 실행하지 않게 함.
            if(isFirebaseTestLab) return
            val interstitialAd = InterstitialAd(context)
            interstitialAd.adUnitId = adUnitId
            interstitialAd.loadAd(AdRequest.Builder().build())
        }

        /**
         * 구글 광고의 adRequest 를 생성 및 반환
         * deprecated.
         * @return
         */
        @Suppress("unused", "SpellCheckingInspection")
        private fun newAdRequest(): AdRequest {
            val builder = AdRequest.Builder()
            //builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // 이제는 에뮬레이터는 기본으로 지정되어 있으니, 넣으면 오히려 에러나는 구문.
            //builder.addTestDevice("621CBEEDE09F6A5B37180A718E74C41C");// G pro 테스트 기기
            //builder.addTestDevice("2D81264572D2AB096C895509EDBD419F");// Samsung G3 테스트 기기
            return builder.build()
        }

        /**
         * 테스트 광고를 표시하기 위해 테스트 기기를 등록.
         */
        @Suppress("unused", "SpellCheckingInspection")
        private fun setTestDeviceIds(){
            // 621CBEEDE09F6A5B37180A718E74C41C // My G Pro
            // 2D81264572D2AB096C895509EDBD419F // My Samsung G3
            val testDeviceIds = listOf("621CBEEDE09F6A5B37180A718E74C41C","2D81264572D2AB096C895509EDBD419F")

            val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(configuration)
        }

        /**
         * 구글 Test Lab 에서 멍청하게 자꾸 광고를 클릭하기 때문에 추가한 메서드.
         * 나중에 구글 Test Lab 서비스가 똑똑해지면 필요하지 않게 될 예정. (가능하려나?)
         * 수신 IP 를 기준으로 무시한다고 하는데 잘 되고 있는지는 모르겠다... 음... 여튼 광고를 누르는 듯 하면
         * 이 메서드를 이용해서 체크할 것.
         * https://stackoverflow.com/questions/43598250/how-to-detect-running-in-firebase-test-lab
         */
        @Suppress("unused")
        private fun isFirebaseTestLab(context: Context): Boolean {
            val testLabSetting = Settings.System.getString(context.contentResolver, "firebase.test.lab")
            return "true" == testLabSetting
        }
    }
}