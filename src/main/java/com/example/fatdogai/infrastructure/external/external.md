# infrastructure/external

**외부 시스템 연동 구현**이 위치하는 곳입니다.
`application/port`의 Outbound Port를 외부 API·서비스 호출로 구현합니다.

## 무엇이 들어가는가
- 외부 API 클라이언트 어댑터
  - 예: AI 분석 서버, 결제, 알림(메일/푸시), 외부 인증 등
  - 예: `AiAnalysisAdapter implements AiAnalysisPort`
- 외부 통신용 요청/응답 모델, HTTP 클라이언트 설정
- 외부 응답 ↔ 도메인 모델 변환 매퍼

## 원칙
- `application/port`의 인터페이스를 구현하여 외부 의존성을 바깥으로 밀어낸다.
- 외부 API 규격 변경의 영향이 이 계층 밖으로 새어나가지 않도록 격리한다.
- 외부 응답을 도메인이 이해하는 형태로 변환해 전달한다.
