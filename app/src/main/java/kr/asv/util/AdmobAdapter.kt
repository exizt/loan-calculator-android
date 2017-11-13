package kr.asv.util

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

/**
 * Created by EXIZT on 2017-11-14.
 */
class AdmobAdapter{
    companion object {
        /**
         * 구글 광고 추가할 때에.
         */
        @Suppress("unused")
        fun loadBannerAdMob(mAdView: AdView) {
            mAdView.loadAd(newAdRequest())
        }

        /**
         * 구글 전면광고 추가할 때에.
         * @param context
         */
        @Suppress("unused")
        fun loadInterstitialAdMob(context: Context,adUnitId:String) {
            val interstitialAd = InterstitialAd(context)
            interstitialAd.adUnitId = adUnitId
            interstitialAd.loadAd(newAdRequest())
        }

        /**
         * 구글 광고의 adRequest 를 생성 및 반환
         * @return
         */
        @Suppress("unused")
        fun newAdRequest(): AdRequest {
            return AdRequest.Builder().addTestDevice("2D81264572D2AB096C895509EDBD419F").build()
        }
    }
}