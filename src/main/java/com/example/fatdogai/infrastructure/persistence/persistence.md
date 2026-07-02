# infrastructure/persistence

**데이터 영속화(저장소) 관련 구현**이 위치하는 곳입니다.
`application/port`의 Outbound Port(저장/조회 인터페이스)를 실제 DB 기술로 구현합니다.

## 무엇이 들어가는가
- Outbound Port 구현체 (Persistence Adapter)
  - 예: `DogPersistenceAdapter implements LoadDogPort, SaveDogPort`
- JPA Entity / 매핑 클래스 (도메인 모델과 분리된 DB 전용 엔티티)
- Spring Data Repository 인터페이스
  - 예: `DogJpaRepository`
- 도메인 모델 ↔ JPA 엔티티 변환 매퍼

## 원칙
- `application/port`가 정의한 인터페이스를 구현하여 의존성 역전을 완성한다.
- DB 세부사항(JPA, SQL 등)은 이 계층 안에 가둔다. 안쪽 계층은 이를 알지 못한다.
- 가능하면 도메인 모델과 JPA 엔티티를 분리해 도메인이 DB 어노테이션에 오염되지 않게 한다.
