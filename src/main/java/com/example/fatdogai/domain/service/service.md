# domain/service

**도메인 서비스(Domain Service)** 가 위치하는 곳입니다.
하나의 엔티티에 자연스럽게 담기 어려운, 여러 도메인 객체에 걸친 순수 비즈니스 규칙을 담습니다.

## 무엇이 들어가는가
- 여러 엔티티/값 객체를 조합해야 성립하는 비즈니스 로직
  - 예: `DogHealthEvaluator`, `BreedMatchingPolicy`
- 특정 엔티티의 책임으로 두면 어색한 계산·판단 규칙

## application/service 와의 차이
- **domain/service**: 순수 비즈니스 규칙. 외부 자원(DB, API)을 모른다.
- **application/service**: 유스케이스 흐름 조율. 트랜잭션·포트 호출을 담당한다.

## 원칙
- `domain/model`에만 의존하고 프레임워크·외부 라이브러리에 의존하지 않는다.
- 상태를 갖지 않는(stateless) 순수 로직으로 작성한다.
