# 🃏 Pokerever

<p align="center">
  <img src="assets/mainimg.png" alt="Pokerever UI Preview" width="500"/>
</p>

**Pokerever**는 변화하는 Poker 산업의 정부 지침에 따라 사용자 간 포인트 송수신을 지양하고,  
사용자와 매장 간에만 포인트 송수신이 가능하도록 접근을 제어하며 사용자에게 편의성을 제공하기 위해 제작된 앱입니다.  

Firestore 기반 포인트 관리 및 트랜잭션 내역 표시 기능을 포함한 포인트 추적 앱입니다.  
Jetpack Compose 기반의 최신 Android 기술을 활용하여 실시간 UI 업데이트, 트랜잭션 로그 표시,  
사용자 인터랙션을 효율적으로 구현했습니다.

---

<h2>📽️ 데모 영상 (PokerEver)</h2>

<table>
  <tr>
    <td align="center"><strong>🔐 회원가입</strong></td>
    <td align="center"><strong>🏪 매장정보</strong></td>
    <td align="center"><strong>📍 매장위치</strong></td>
  </tr>
  <tr>
    <td><img src="assets/회원가입.gif" width="240px"/></td>
    <td><img src="assets/매장정보.gif" width="240px"/></td>
    <td><img src="assets/매장위치.gif" width="240px"/></td>
  </tr>
</table>

<br/>

<table>
  <tr>
    <td align="center"><strong>📊 탭별정보</strong></td>
    <td align="center"><strong>💸 포인트 송수신</strong></td>
  </tr>
  <tr>
    <td align="center"><img src="assets/탭별정보.gif" width="280px"/></td>
    <td align="center"><img src="assets/포인트송수신.gif" width="280px"/></td>
  </tr>
</table>


---

## 📱 서비스 주요 기능

- **홈**  
  사용자가 추가한 매장 정보를 한눈에 확인할 수 있으며, 즐겨찾기 기능을 통해 자주 방문하는 매장을 쉽게 찾을 수 있습니다.  
  포인트 현황과 최근 활동이 실시간으로 반영되어 직관적으로 파악할 수 있습니다.

- **매장정보**  
  현재 위치 기반으로 가까운 매장을 자동으로 탐색하고, 매장 이름 또는 키워드 검색을 통해 원하는 매장을 빠르게 찾을 수 있습니다.  
  매장 등록 기능을 통해 사용자가 직접 매장 정보를 추가할 수도 있습니다.

- **상세 매장정보**  
  등록된 매장을 선택하면, 해당 매장에서 사용 가능한 포인트 잔액, 포인트 송수신 내역 등을 상세하게 확인할 수 있으며,  
  간편한 UI를 통해 실시간으로 포인트를 전송할 수 있는 기능을 제공합니다.

---

## 🧱 기술 스택

- **Architecture**: MVVM + Clean Architecture  
- **UI**: Jetpack Compose  
- **Dependency Injection**: Dagger-Hilt  
- **Image Loading**: Coil  
- **Realtime Database**: Cloud Firestore  

---

## 🛠️ 개선 사항 요약

| 이슈 | 해결 방식 | 결과 |
|------|-----------|------|
| Firestore 실시간 반영 불가 | `observeUserPoints`의 값 변화를 `mutableStateOf`로 처리 | 실시간 포인트 UI 반영 |
| 트랜잭션 이름 비동기 지연 | ViewModel에서 이름을 미리 fetch | 더 빠른 렌더링 |
| 리스트 렌더링 지연 | `items(..., key = { ... })` 명시적 키 지정 | 성능 개선 및 Warning 제거 |

