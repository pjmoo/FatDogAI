# FatDog AI - Clean/Hexagonal Architecture Project

이 프로젝트는 `fatdogai` 폴더의 각 계층별 규칙(`.md` 파일)을 바탕으로 기존 `archat` 프로젝트 소스 코드를 **헥사고날(Hexagonal) / 클린(Clean) 아키텍처** 형식으로 재구성한 챗봇 애플리케이션입니다.

---

## 🏗️ 아키텍처 구조 및 클래스 배치

`com.example.fatdogai` 패키지 아래의 구조와 실제 배치된 클래스들은 다음과 같습니다:

### 1. Domain Layer (도메인 계층)
- **`domain/model`** (시스템 핵심 도메인 객체, POJO)
  - [Chat.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/domain/model/Chat.java): 채팅 메시지의 개념과 상태를 표현하는 Record
- **`domain/service`** (순수 도메인 비즈니스 서비스)
  - 현재 시스템은 상태 조율 위주의 유스케이스만 다루고 있으므로 비즈니스 로직은 애플리케이션 서비스에서 담당합니다.

### 2. Application Layer (애플리케이션 계층)
- **`application/port`** (인터페이스만 존재하는 계층 경계 - DIP 실현)
  - **Inbound Ports (외부 -> 내부 호출 인터페이스)**
    - [ChatUseCase.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/port/ChatUseCase.java): 프레젠테이션 계층에서 호출하는 핵심 기능 정의
  - **Outbound Ports (내부 -> 외부 호출 인터페이스)**
    - [ChatRepository.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/port/ChatRepository.java): 데이터 영속화를 위한 포트
    - [ChatProvider.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/port/ChatProvider.java): AI 서비스 연결을 위한 포트
    - [ChatPublisher.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/port/ChatPublisher.java): 이벤트 전파 포트
- **`application/service`** (유스케이스 구현체 - 흐름 제어 및 도메인 객체 조율)
  - [GeminiChatService.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/service/GeminiChatService.java): Gemini/NVIDIA LLM 모델 처리 서비스 (ChatUseCase 구현)
  - [AIChatService.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/application/service/AIChatService.java): 다중 AI 모델 분기 처리 서비스 (ChatUseCase 구현)

### 3. Infrastructure Layer (인프라 계층)
- **`infrastructure/persistence`** (영속성 어댑터 구현)
  - [InMemoryChatRepository.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/infrastructure/persistence/InMemoryChatRepository.java): 인메모리 방식 데이터 저장 구현체 (ChatRepository 포트 구현)
- **`infrastructure/external`** (외부 API 어댑터 구현)
  - [GenAIChatProvider.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/infrastructure/external/GenAIChatProvider.java): Google GenAI SDK 연동 구현체 (ChatProvider 포트 구현)
  - [NimChatProvider.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/infrastructure/external/NimChatProvider.java): NVIDIA NIM API HTTP 연동 구현체 (ChatProvider 포트 구현)
  - [GroqChatProvider.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/infrastructure/external/GroqChatProvider.java): Groq API 연동 구현체 (ChatProvider 포트 구현 - 미완성)
  - [GenAIConfig.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/infrastructure/external/GenAIConfig.java): GenAI API 연동 설정 및 인스턴스 빌더

### 4. Presentation Layer (표현 계층)
- **`presentation/controller`** (HTTP 웹 요청 엔드포인트)
  - [ChatController.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/presentation/controller/ChatController.java): 사용자 메시지 전송 및 이력 조회를 처리하는 서블릿 컨트롤러 (Inbound Port인 `ChatUseCase`에 의존)
  - [BaseController.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/presentation/controller/BaseController.java): 뷰 접두사(Prefix) 관리 추상 서블릿
- **`presentation/dto`** (데이터 전송 객체)
  - [ChatResponseDTO.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/presentation/dto/ChatResponseDTO.java): 클라이언트에 반환할 데이터 형식 정의 및 도메인 객체 변환 매퍼 포함
- **`presentation/listener`** (웹 애플리케이션 초기화 리스너)
  - [WebEnvListener.java](file:///C:/workspace/FatDogAI/src/main/java/com/example/fatdogai/presentation/listener/WebEnvListener.java): 애플리케이션 실행 시 `.env` 환경 변수를 시스템 프로퍼티에 로드하는 서블릿 리스너
