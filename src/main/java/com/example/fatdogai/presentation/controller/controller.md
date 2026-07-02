# presentation/controller

**외부 요청의 진입점(Controller)** 이 위치하는 곳입니다.
HTTP 요청을 받아 애플리케이션의 Inbound Port(유스케이스)로 위임합니다.

## 무엇이 들어가는가
- REST 컨트롤러 / 엔드포인트
  - 예: `DogController`, `DiagnosisController`
- 요청 라우팅, 인증/인가 진입 처리, 예외 → HTTP 응답 변환(핸들러)

## 원칙
- 비즈니스 로직을 두지 않는다. **요청 수신 → DTO 변환 → 유스케이스 호출 → 응답 반환** 흐름만 담당한다.
- `application/port`의 Inbound Port에만 의존한다 (구현체를 직접 알지 않는다).
- 외부에 노출되는 형식(DTO)과 내부 도메인 모델을 분리한다.
