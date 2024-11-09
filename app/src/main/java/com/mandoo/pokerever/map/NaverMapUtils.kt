package com.mandoo.pokerever.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

// 네이버 지도 URL을 호출하는 유틸리티 함수
fun openNaverMap(context: Context, address: String) {
    val encodedAddress = Uri.encode(address) // 주소 인코딩
    val uri = Uri.parse("https://m.map.naver.com/search2/search.html?query=$encodedAddress")
    val intent = Intent(Intent.ACTION_VIEW, uri)

    // 네이버 지도 앱이 설치되어 있는지 확인
    val packageManager = context.packageManager
    val isAppInstalled = try {
        packageManager.getPackageInfo("com.nhn.android.nmap", 0) != null
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    if (isAppInstalled) {
        // 네이버 지도 앱이 설치되어 있으면 네이버 지도 앱으로 열기
        val appUri = Uri.parse("naver.maps://search?query=$encodedAddress")
        val appIntent = Intent(Intent.ACTION_VIEW, appUri)
        context.startActivity(appIntent)
    } else {
        // 네이버 지도 앱이 없으면 웹으로 열기
        context.startActivity(intent)
    }
}
