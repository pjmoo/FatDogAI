# application/service

**유스케이스(Use Case)를 구현**하는 곳입니다.
`port`의 Inbound Port를 구현하고, 필요한 작업을 Outbound Port에 위임하여 흐름을 조율합니다.

## 무엇이 들어가는가
- Inbound Port 구현체 (애플리케이션 서비스)
  - 예: `RegisterDogService implements RegisterDogUseCase`
- 유스케이스 단위의 흐름 제어: 입력 검증 → 도메인 호출 → 저장/외부 연동

## 원칙
- 비즈니스 규칙 자체는 `domain`에 두고, 여기서는 **조율(orchestration)** 만 담당한다.
- 트랜잭션 경계(`@Transactional`)를 관리한다.
- 구체 구현이 아닌 **Outbound Port(인터페이스)** 에만 의존한다.
- `domain`과 `application/port`에 의존하며, `infrastructure`·`presentation`은 알지 못한다.
