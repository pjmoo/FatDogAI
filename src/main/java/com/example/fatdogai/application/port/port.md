# application/port

애플리케이션 계층의 **경계(interface)** 를 정의하는 곳입니다.
유스케이스가 바깥 세계와 소통하기 위한 계약(contract)만 두고, 구현은 두지 않습니다.

## 무엇이 들어가는가
- **Inbound Port (in)**: 유스케이스를 외부에 노출하는 인터페이스. `presentation` 계층이 호출한다.
  - 예: `RegisterDogUseCase`, `AnalyzeDogUseCase`
- **Outbound Port (out)**: 애플리케이션이 외부 자원(DB, 외부 API 등)을 사용하기 위해 요구하는 인터페이스. 구현은 `infrastructure`에 둔다.
  - 예: `LoadDogPort`, `SaveDogPort`, `AiAnalysisPort`

## 원칙
- 인터페이스만 정의하고 구현체는 넣지 않는다 (의존성 역전, DIP).
- `domain` 타입에만 의존하고, 프레임워크/외부 라이브러리에는 의존하지 않는다.
- 의존 방향: `presentation → inbound port → service → outbound port ← infrastructure`
