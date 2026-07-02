# presentation/dto

**요청/응답 데이터 전송 객체(DTO)** 가 위치하는 곳입니다.
클라이언트와 주고받는 데이터의 형식을 정의하며, 도메인 모델을 외부에 직접 노출하지 않도록 경계 역할을 합니다.

## 무엇이 들어가는가
- **Request DTO**: 클라이언트 요청 본문/파라미터 매핑 객체
  - 예: `RegisterDogRequest`
- **Response DTO**: 클라이언트에 반환할 응답 객체
  - 예: `DogResponse`, `DiagnosisResponse`
- DTO ↔ 도메인 모델(또는 유스케이스 입출력 모델) 변환 매퍼

## 원칙
- 도메인 모델을 그대로 노출하지 않는다 → 내부 구조 변경이 API 스펙에 새는 것을 막는다.
- 입력 검증(validation) 어노테이션은 여기(요청 DTO)에 둔다.
- `presentation` 계층 안에서만 사용한다. 안쪽 계층(domain/application)은 DTO를 알지 못한다.
