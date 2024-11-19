package com.mandoo.pokerever.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

// 네이버 지도 URL을 호출하는 유틸리티 함수
fun openNaverMap(context: Context, address: String) {
    val encodedAddress = Uri.encode(address) // 주소 인코딩

    // 네이버 지도 앱 스킴
    val appUri = Uri.parse("naver.maps://search?query=$encodedAddress")
    // 웹 브라우저용 네이버 지도 URL
    val webUri = Uri.parse("https://map.naver.com/v5/search/$encodedAddress")

    // 네이버 지도 앱 설치 여부 확인
    val intent = Intent(Intent.ACTION_VIEW, appUri)
    val packageManager = context.packageManager
    val isAppInstalled =
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()

    if (isAppInstalled) {
        // 네이버 지도 앱으로 열기
        context.startActivity(intent)
    } else {
        // 네이버 지도 웹 브라우저로 열기
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}
