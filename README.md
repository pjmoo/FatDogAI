# 🐶 FatDog AI

<!-- workspace-readme-learning:start -->
## 파일과 연결한 학습 안내

아래 설명은 이 폴더의 실제 소스와 빌드 설정을 기준으로 정리했습니다. 기존 소개의 기능 설명은 연결된 파일과 함께 확인할 수 있습니다.

### 주요 파일과 역할

| 파일 | 역할과 읽을 내용 |
| --- | --- |
| [Dockerfile](<Dockerfile>) | 컨테이너 이미지의 빌드·실행 단계 |
| [pom.xml](<pom.xml>) | Maven 의존성·플러그인·패키징 설정 |
| [src/main/java/com/example/fatdogai/presentation/controller/BaseController.java](<src/main/java/com/example/fatdogai/presentation/controller/BaseController.java>) | HTTP 요청과 응답을 처리하는 Servlet |
| [src/main/java/com/example/fatdogai/presentation/controller/ChatController.java](<src/main/java/com/example/fatdogai/presentation/controller/ChatController.java>) | Java 타입과 동작 정의 — `init` |
| [src/main/java/com/example/fatdogai/application/port/ChatRepository.java](<src/main/java/com/example/fatdogai/application/port/ChatRepository.java>) | 데이터 저장·조회 인터페이스 또는 구현 |
| [src/main/java/com/example/fatdogai/application/service/AIChatService.java](<src/main/java/com/example/fatdogai/application/service/AIChatService.java>) | 업무 처리와 외부 의존성 호출 — `save`, `findAllByUserId` |
| [src/main/java/com/example/fatdogai/application/service/GeminiChatService.java](<src/main/java/com/example/fatdogai/application/service/GeminiChatService.java>) | 업무 처리와 외부 의존성 호출 — `save`, `findAllByUserId` |
| [src/main/java/com/example/fatdogai/infrastructure/persistence/InMemoryChatRepository.java](<src/main/java/com/example/fatdogai/infrastructure/persistence/InMemoryChatRepository.java>) | 데이터 저장·조회 인터페이스 또는 구현 — `save`, `findAllByUserId` |
| [src/main/java/com/example/fatdogai/application/port/ChatProvider.java](<src/main/java/com/example/fatdogai/application/port/ChatProvider.java>) | 구현체가 따라야 하는 인터페이스 |
| [src/main/java/com/example/fatdogai/application/port/ChatPublisher.java](<src/main/java/com/example/fatdogai/application/port/ChatPublisher.java>) | 구현체가 따라야 하는 인터페이스 |
| [src/main/java/com/example/fatdogai/application/port/ChatUseCase.java](<src/main/java/com/example/fatdogai/application/port/ChatUseCase.java>) | 업무 처리와 외부 의존성 호출 |
| [src/main/java/com/example/fatdogai/application/port/port.md](<src/main/java/com/example/fatdogai/application/port/port.md>) | 설계·학습·운영 내용을 설명하는 문서 |
| [src/main/java/com/example/fatdogai/application/service/service.md](<src/main/java/com/example/fatdogai/application/service/service.md>) | 설계·학습·운영 내용을 설명하는 문서 |
| [src/main/java/com/example/fatdogai/domain/model/Chat.java](<src/main/java/com/example/fatdogai/domain/model/Chat.java>) | Java 타입과 동작 정의 — `Chat` |
| [src/main/java/com/example/fatdogai/domain/model/model.md](<src/main/java/com/example/fatdogai/domain/model/model.md>) | 설계·학습·운영 내용을 설명하는 문서 |
| [src/main/java/com/example/fatdogai/domain/service/service.md](<src/main/java/com/example/fatdogai/domain/service/service.md>) | 설계·학습·운영 내용을 설명하는 문서 |
| [src/main/java/com/example/fatdogai/infrastructure/external/external.md](<src/main/java/com/example/fatdogai/infrastructure/external/external.md>) | 설계·학습·운영 내용을 설명하는 문서 |
| [src/main/java/com/example/fatdogai/infrastructure/external/GenAIChatProvider.java](<src/main/java/com/example/fatdogai/infrastructure/external/GenAIChatProvider.java>) | Java 타입과 동작 정의 — `useAI` |

### 실행과 설정 확인

- [pom.xml](<pom.xml>)의 의존성과 패키징을 기준으로 구성합니다. 선언된 Java 설정은 17입니다.
- `.\mvnw.cmd package`로 빌드합니다. WAR 결과물은 프로젝트의 Servlet/JSP 규격과 호환되는 컨테이너에 배포해 확인합니다.
- 코드·설정에서 참조하는 환경 변수 이름: `GEMINI_API_KEY`, `GROQ_API_KEY`, `NIM_API_KEY`. 기본값과 필수 여부는 각 참조 위치에서 확인합니다.

### 관련 PDF와 보충 설명

- [7/2 강의](<../260629_ex/새 폴더/7-2/README.md>): 계층형·클린 아키텍처와 상태 관리의 책임 분리를 연결합니다.
- [6/4 강의](<../260629_ex/새 폴더/6-4/README.md>): 서버의 AI 제공자 호출과 입력·응답·환경 설정을 연결합니다.

이 링크는 구현을 이해하기 위한 관련 기초 자료입니다. 해당 강의가 이 저장소의 모든 기능이나 이후 버전의 API를 설명한다는 뜻은 아닙니다.

### 읽는 순서와 복습

- 요청 처리 → 유스케이스·서비스 → 포트·저장소·외부 API의 의존 방향을 읽습니다. 외부 구현을 교체할 때 바뀌는 코드와 업무 규칙을 가진 코드가 구분되는지 확인합니다.
- 화면 요청 → 서버 API → 모델 호출 → 결과 변환 순으로 책임을 구분합니다. 입력 누락과 모델 호출 실패의 처리를 확인하고 비밀 키가 브라우저로 전달되는지 점검합니다.

<!-- workspace-readme-learning:end -->

> 헥사고날(Hexagonal) / 클린(Clean) 아키텍처로 구성한 웹 기반 멀티-LLM 채팅 애플리케이션

브라우저에서 메시지와 AI 모델을 고르면, 서버가 여러 AI 제공자(Google Gemini · NVIDIA Nemotron · Groq) 중 하나에 물어보고 답을 화면에 돌려줍니다. "AI 챗봇"이라는 기능 자체보다 **어떻게 계층을 나누고 의존성을 관리하는가**를 학습하기 위한 프로젝트

---

## ✨ 주요 기능

- 🤖 **멀티 AI 제공자** — 하나의 `ChatProvider` 인터페이스로 Google Gemini(GenAI SDK), NVIDIA Nemotron(NIM API), Groq(REST API)를 다형적으로 처리
- 🔀 **모델별 전략 라우팅** — 선택한 모델 이름에 따라 런타임에 적절한 제공자를 선택 (`AIChatService`)
- 💬 **세션별 대화 이력** — `HttpSession` ID를 사용자 식별자로 사용해 대화방을 구분하고 맥락(history)을 함께 전달
- 🧹 **응답 후처리** — Groq 응답에서 사고 과정/영어 마커를 제거하고 한글 최종 답변만 추출(`GroqChatProvider.getFinalAnswer`)
- 🧱 **포트 & 어댑터 설계** — 저장소·AI 제공자를 인터페이스 뒤에 숨겨, 안쪽 계층 수정 없이 구현체 교체 가능

---

## 🛠️ 기술 스택

| 구분 | 사용 기술 |
|------|-----------|
| 언어 / 런타임 | Java 17 |
| 웹 | Jakarta Servlet 6.0, JSP + JSTL 3.0 |
| AI 연동 | Google GenAI SDK (`google-genai`), NVIDIA NIM · Groq REST API |
| JSON | Gson, Jackson Databind |
| 빌드 / 패키징 | Maven (WAR), `maven-war-plugin` |
| 테스트 | JUnit 5 |

> 서블릿 6.0 / JSP는 **Jakarta EE 10** 기준이므로 **Tomcat 10.1+** 등 Jakarta 네임스페이스를 지원하는 컨테이너가 필요합니다.

---

## 🏗️ 아키텍처

의존성은 항상 안쪽(도메인)을 향합니다:

```
presentation ──▶ inbound port ──▶ service ──▶ outbound port ◀── infrastructure
 (Controller)     (ChatUseCase)   (흐름 조율)   (Repository/Provider)   (실제 구현)
```

### 요청 처리 흐름

```
[브라우저]            [진입점]              [유스케이스]              [갈아끼울 부품]
 채팅 폼  ── POST /chat ─▶ ChatController ─▶ ChatUseCase.save() ─┬─▶ ChatRepository (저장/조회)
 화면    ◀─ GET /chat ──  chat.jsp  ◀── DTO 변환   (AIChatService)  │      (InMemoryChatRepository)
                                                                     └─▶ ChatProvider (AI 호출, 모델별 분기)
                                                                          ├ GenAIChatProvider (Gemini/Gemma)
                                                                          ├ NimChatProvider   (Nemotron)
                                                                          └ GroqChatProvider  (그 외)
```

핵심은 **`AIChatService`가 자기가 부르는 게 Gemini인지 Nemotron인지 Groq인지, 저장이 메모리인지 DB인지 모른다**는 점입니다. 전부 인터페이스(포트) 뒤에 숨겨져 있어(DIP), 구현체를 갈아끼워도 안쪽 코드는 바뀌지 않습니다.

> 현재 [`ChatController`](src/main/java/com/example/fatdogai/presentation/controller/ChatController.java)는 3-way 라우팅을 하는 `AIChatService`를 사용합니다. `GeminiChatService`는 Gemini↔Nemotron 2-way 버전으로 남아 있는 대체 구현입니다.

### 계층별 클래스 배치

| 계층 | 패키지 | 주요 클래스 | 역할 |
|------|--------|-------------|------|
| **Domain** | `domain/model` | [`Chat`](src/main/java/com/example/fatdogai/domain/model/Chat.java) | 채팅 1건을 담는 불변 값 객체(record) |
| **Application** | `application/port` | [`ChatUseCase`](src/main/java/com/example/fatdogai/application/port/ChatUseCase.java) (in) · [`ChatRepository`](src/main/java/com/example/fatdogai/application/port/ChatRepository.java) · [`ChatProvider`](src/main/java/com/example/fatdogai/application/port/ChatProvider.java) · [`ChatPublisher`](src/main/java/com/example/fatdogai/application/port/ChatPublisher.java) (out) | 계층 경계(인터페이스)만 정의 |
| | `application/service` | [`AIChatService`](src/main/java/com/example/fatdogai/application/service/AIChatService.java) (활성) · [`GeminiChatService`](src/main/java/com/example/fatdogai/application/service/GeminiChatService.java) | 유스케이스 구현 · 흐름 조율 |
| **Infrastructure** | `infrastructure/persistence` | [`InMemoryChatRepository`](src/main/java/com/example/fatdogai/infrastructure/persistence/InMemoryChatRepository.java) | 인메모리 저장 어댑터 |
| | `infrastructure/external` | [`GenAIChatProvider`](src/main/java/com/example/fatdogai/infrastructure/external/GenAIChatProvider.java) · [`GenAIConfig`](src/main/java/com/example/fatdogai/infrastructure/external/GenAIConfig.java) · [`NimChatProvider`](src/main/java/com/example/fatdogai/infrastructure/external/NimChatProvider.java) · [`GroqChatProvider`](src/main/java/com/example/fatdogai/infrastructure/external/GroqChatProvider.java) · [`GroqAIConfig`](src/main/java/com/example/fatdogai/infrastructure/external/GroqAIConfig.java) | 외부 AI API 어댑터 |
| **Presentation** | `presentation/controller` | [`ChatController`](src/main/java/com/example/fatdogai/presentation/controller/ChatController.java) · [`BaseController`](src/main/java/com/example/fatdogai/presentation/controller/BaseController.java) | `/chat` 서블릿 진입점 |
| | `presentation/dto` | [`ChatResponseDTO`](src/main/java/com/example/fatdogai/presentation/dto/ChatResponseDTO.java) | 화면 전용 DTO + 도메인→DTO 매퍼 |
| | `presentation/listener` | [`WebEnvListener`](src/main/java/com/example/fatdogai/presentation/listener/WebEnvListener.java) | 앱 시작 시 `.env` 로드 |

---

## 📁 프로젝트 구조

```
FatDogAI/
├── src/main/
│   ├── java/com/example/fatdogai/
│   │   ├── domain/model/Chat.java
│   │   ├── application/
│   │   │   ├── port/          # ChatUseCase, ChatRepository, ChatProvider, ChatPublisher
│   │   │   └── service/       # AIChatService(활성), GeminiChatService
│   │   ├── infrastructure/
│   │   │   ├── persistence/   # InMemoryChatRepository
│   │   │   └── external/      # GenAIChatProvider, GenAIConfig,
│   │   │                      #   NimChatProvider, GroqChatProvider, GroqAIConfig
│   │   └── presentation/
│   │       ├── controller/    # ChatController, BaseController
│   │       ├── dto/           # ChatResponseDTO
│   │       └── listener/      # WebEnvListener
│   └── webapp/WEB-INF/
│       ├── views/chat.jsp     # 채팅 화면 (HTML + CSS + JS)
│       └── web.xml
├── .env.sample               # 필요한 환경변수 양식
├── pom.xml
└── mvnw / mvnw.cmd            # Maven Wrapper
```

---

## 🚀 시작하기

### 사전 준비

- JDK 17
- Tomcat 10.1+ (또는 Jakarta EE 10 호환 서블릿 컨테이너)
- API 키
  - [Google AI Studio](https://aistudio.google.com/)의 `GEMINI_API_KEY`
  - [NVIDIA NIM](https://build.nvidia.com/)의 `NIM_API_KEY`
  - [Groq Console](https://console.groq.com/)의 `GROQ_API_KEY`

### 1) 환경변수 설정

`.env.sample`을 복사해 프로젝트 루트에 `.env`를 만들고 실제 키를 채웁니다.

```bash
cp .env.sample .env
```

```dotenv
# .env
GEMINI_API_KEY=your-gemini-api-key
NIM_API_KEY=your-nim-api-key
GROQ_API_KEY=your-groq-api-key
```

> 앱 시작 시 [`WebEnvListener`](src/main/java/com/example/fatdogai/presentation/listener/WebEnvListener.java)가 `.env`를 찾아 값을 시스템 프로퍼티로 로드합니다. `.env`는 `.gitignore`에 포함되어 커밋되지 않습니다.

### 2) 빌드

```bash
./mvnw clean package
```

`target/` 아래에 WAR 파일이 생성됩니다.

### 3) 실행

- **IDE(IntelliJ 등)**: Tomcat 10.1+ 런 구성에 이 모듈을 WAR(exploded)로 배포
- **직접 배포**: 생성된 WAR를 Tomcat의 `webapps/`에 복사 후 서버 기동

### 4) 접속

브라우저에서 컨텍스트 경로 뒤에 `/chat`을 붙여 접속합니다.

```
http://localhost:8080/<context-path>/chat
```

---

## 💬 사용법

1. 하단 입력창에 메시지를 입력합니다.
2. 드롭다운에서 AI 모델을 선택합니다. 모델 이름에 따라 제공자가 자동 결정됩니다.

   | 화면 표시 | 모델 값 | 라우팅 (`AIChatService`) |
   |-----------|---------|--------------------------|
   | gemma-4-26b | `gemma-4-26b-a4b-it` | Gemini (GenAI) — 이름에 `gemma` |
   | gemma-4-31b | `gemma-4-31b-it` | Gemini (GenAI) — 이름에 `gemma` |
   | gemini-3.1 | `gemini-3.1-flash-lite` | Gemini (GenAI) — 이름에 `gemini` |
   | 네모트론 3 | `nemotron-3-ultra-550b-a55b` | NVIDIA (NIM) — 이름에 `nemotron` |
   | Qwen 3.6 | `qwen/qwen3.6-27b` | Groq — 그 외 전부 |

3. **전송**을 누르면 같은 세션의 대화 이력을 맥락으로 함께 보내 답변을 받습니다.

> 라우팅 규칙: 모델명에 `gemini`/`gemma` → Gemini, `nemotron` → NVIDIA, **그 외 → Groq**. Groq 경로에서는 기본 모델(`openai/gpt-oss-20b`)과 시스템 지시를 사용하며, 응답의 사고 과정을 제거하고 한글 답변만 추출해 반환합니다.

---

## 🔌 새 AI 제공자 추가하기

이 아키텍처의 장점은 **AI를 갈아끼우기 쉽다**는 것입니다. 예를 들어 OpenAI를 추가한다면:

1. `infrastructure/external`에 `ChatProvider`를 구현하는 `OpenAIChatProvider` 작성
2. `AIChatService`의 라우팅 분기에 조건 추가 (또는 모델명→Provider 매핑 팩토리로 통일)

`ChatUseCase`, `ChatController`, `chat.jsp` 등 나머지 코드는 **한 줄도 바뀌지 않습니다.** 인메모리 저장소를 DB로 바꾸는 것도 `ChatRepository`를 구현하는 새 어댑터를 만들면 끝입니다.

---

## ⚠️ 알려진 제약 / 개선 예정

- **인메모리 저장** — 서버를 재시작하면 대화가 사라집니다. 실사용 시 DB 어댑터로 교체 필요.
- **Groq 응답 후처리(`getFinalAnswer`)가 휴리스틱** — 줄 단위로 영어/마커를 지우고 한글만 남기는 방식이라, 답변이 영어이거나 형식이 다르면 잘려 나갈 수 있습니다.
- **`WebEnvListener`의 디버그 로그 경로가 하드코딩** — 윈도우 절대경로(`C:\workspace\...`)라 다른 OS에서는 로그 파일 기록이 조용히 실패합니다(앱 동작에는 영향 없음).
- **JSP 출력 미이스케이프** — `${chat.message}` 직접 출력으로 XSS 여지가 있어 `<c:out>` 처리 권장.
- **`pom.xml`의 `junit-jupiter-api` 중복 선언** — 동일 의존성이 두 번 선언되어 있어 하나로 정리 필요. (`artifactId`/`name`은 `FatDogAI`로 정리 완료)

---

<!-- pdf-til-supplement:start -->
## TIL 부연 설명 — PDF와 연결하기

기존 실습 내용을 이해하기 위한 PDF 기반 부연 설명이다. 아래 예시는 개념을 설명하기 위한 것이며, 이 프로젝트에서 실행해 관찰한 결과와는 구분한다. 페이지 번호는 표지를 포함한 PDF 순서다.

함께 읽을 파일: [src/main/java/com/example/fatdogai/presentation/controller/BaseController.java](<src/main/java/com/example/fatdogai/presentation/controller/BaseController.java>) · [src/main/java/com/example/fatdogai/presentation/controller/ChatController.java](<src/main/java/com/example/fatdogai/presentation/controller/ChatController.java>) · [src/main/java/com/example/fatdogai/application/port/ChatRepository.java](<src/main/java/com/example/fatdogai/application/port/ChatRepository.java>)

### MVC와 계층형 설계의 역할 차이

MVC는 입력 제어·데이터·화면의 역할을 나누고, 계층형 설계는 웹 처리·업무 규칙·저장소 접근의 책임을 나눈다. 따라서 MVC와 Controller–Service–Repository 구조를 함께 사용할 수 있다. 클린 아키텍처에서는 업무 규칙이 외부 구현을 직접 참조하지 않도록 의존 방향을 조정한다.

**예시로 이해하기:** 컨트롤러는 “요청이 어떤 형식인가”, 서비스는 “이 작업이 허용되는가”, 저장소는 “어떻게 읽고 쓰는가”를 맡도록 생각한다. 외부 AI 제공자를 교체할 때 요청 API까지 바꿔야 한다면 제공자 전용 타입이 경계를 넘는지 살펴본다.

근거: 232 소프트웨어 아키텍처 패턴 — [4쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=4>) · [12쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=12>) · [15쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=15>) · [19쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=19>)

### 쿠키는 전달 수단, 세션은 서버 상태

쿠키는 브라우저가 조건에 맞는 요청에 실어 보내는 값이고 세션은 서버가 식별자에 연결해 보관하는 상태다. 세션 방식도 브라우저가 세션 ID를 제시하므로 식별자 보호와 만료 처리가 필요하다. 인증으로 신원을 확인한 후 실제 자원에 대한 인가를 별도로 판단한다.

**예시로 이해하기:** 로그인 → 세션 생성 → 이후 요청의 세션 조회 → 로그아웃 시 무효화 순서로 읽는다. HttpOnly는 자바스크립트의 쿠키 읽기를 제한하지만 브라우저의 자동 전송은 막지 않는다. Secure·SameSite·CSRF 정책은 요청을 보내는 방식과 함께 이해한다.

근거: 233-1 쿠키와 세션 — [4쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=4>) · [6쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=6>) · [10쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=10>) · [12쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=12>) · [17쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=17>)

### 브라우저와 AI 호출 사이에 서버를 두는 이유

AI 기능을 앱에 붙일 때는 사용자 입력 수신, 제공자 호출, 결과 가공을 구분한다. 서버는 비밀키를 보관하고 허용된 입력·모델·응답 형식을 통제하는 경계가 된다. 브라우저에 비밀키를 넣으면 코드나 네트워크 요청에서 확인될 수 있다.

**예시로 이해하기:** 챗봇이라면 브라우저 → 앱 서버의 대화 API → AI 제공자 → 앱 응답 → 화면 표시로 추적한다. 제공자 오류를 그대로 화면에 노출하기보다 사용자에게 필요한 실패 안내로 바꾼다. 429 같은 호출 제한과 잘못된 입력은 대응이 다르므로 무조건 재시도하는 흐름은 피한다.

근거: 171-1 Gen AI 활용 웹앱 개발 — [21쪽](<../260629_ex/새 폴더/6-4/171-1_Gen_AI_활용_웹앱_개발.pdf#page=21>) · [22쪽](<../260629_ex/새 폴더/6-4/171-1_Gen_AI_활용_웹앱_개발.pdf#page=22>) · [23쪽](<../260629_ex/새 폴더/6-4/171-1_Gen_AI_활용_웹앱_개발.pdf#page=23>) · [27쪽](<../260629_ex/새 폴더/6-4/171-1_Gen_AI_활용_웹앱_개발.pdf#page=27>)

<!-- pdf-til-supplement:end -->

<!-- infra-pdf-20260914:start -->
## TIL 부연 설명 — 9월 인프라 PDF

기존 실습을 새로 추가된 PDF와 연결해 풀어 쓴 설명이다. 페이지 번호는 표지를 포함한 PDF 순서이며, 아래 개념 예시는 실제 실행 결과와 구분한다.

### 이미지 빌드와 컨테이너 실행은 다른 단계

현재 [Dockerfile](<Dockerfile>)은 Maven 단계에서 WAR를 만든 뒤 Tomcat 이미지의 `webapps/ROOT.war`에 복사한다. 마지막에 실행하는 것은 `java -jar`가 아니라 `catalina.sh run`이며 Tomcat이 WAR를 배포한다. Tomcat의 컨테이너 내부 수신 포트는 이 파일에서 8080으로 안내한다. `ROOT.war`라는 배포 이름은 애플리케이션을 루트 경로에 배치하는 데 연결된다.

멀티 스테이지는 컴파일에 필요한 도구와 운영 시 필요한 실행 파일을 분리하는 방식이다. 앞 단계에서 만든 파일 중 `COPY --from`으로 선택한 것만 다음 단계로 옮긴다. `docker build`는 이미지를 만들며 웹 서버를 계속 실행해 두는 명령은 아니다. 실제 서비스는 그 이미지로 컨테이너를 생성·실행할 때 시작된다. 빌드 성공 후에도 런타임의 DB 접속·환경변수·포트 문제로 시작에 실패할 수 있다.

PDF 근거: 이미지 빌드·볼륨·네트워크 — [4쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=4>) · [5쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=5>) · [6쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=6>) · [7쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=7>)

### 설정·공개 포트·저장 데이터의 경계

이 프로젝트에서는 Tomcat의 설정 파일에 지정된 포트가 실제 수신 포트다.

`EXPOSE`는 사용 포트를 이미지에 명시하는 것이며 호스트 포트를 실제로 여는 동작은 아니다. `-p 호스트포트:컨테이너포트` 또는 Compose의 `ports`가 두 포트를 연결한다. 컨테이너 안의 `localhost`는 그 컨테이너 자신이므로 별도 DB 컨테이너를 찾는 주소로 사용할 수 없다. 같은 사용자 정의 네트워크에 연결된 컨테이너는 이름으로 상대를 찾는 구성을 사용할 수 있다.

이미지에는 실행 코드를 두고 환경별 값은 실행 시 전달하면 같은 이미지를 여러 환경에서 사용할 수 있다. 업로드·DB 파일처럼 재생성 후에도 남아야 하는 데이터는 컨테이너 쓰기 레이어와 분리한다. 볼륨은 영속 저장의 수단이며 백업 자체를 대신하지는 않는다.

PDF 근거: 이미지 빌드·볼륨·네트워크 — [10쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=10>) · [11쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=11>) · [12쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=12>) · [13쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=13>) · [14쪽](<../260629_ex/새 폴더/9-8/03-1_도커_이미지_빌드와_볼륨_네트워크.pdf#page=14>)

<!-- infra-pdf-20260914:end -->
