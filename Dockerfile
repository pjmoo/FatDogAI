# 1단계: 빌드 스테이지 (Maven + JDK 17)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 의존성 캐싱을 위해 pom.xml을 먼저 복사하여 다운로드
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 소스 코드 복사 후 WAR 파일 빌드
COPY src ./src
RUN mvn clean package -DskipTests

# 2단계: 실행 스테이지 (Tomcat 10.1 + JDK 17)
FROM tomcat:10.1-jdk17-temurin-jammy

# 기본 내장된 기본 애플리케이션들 삭제 (루트 경로 점유를 방지하기 위함)
RUN rm -rf /usr/local/tomcat/webapps/*

# 빌드된 WAR 파일을 ROOT.war로 복사하여 루트 경로(/)에 배포
COPY --from=build /app/target/FatDogAI-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat 포트 노출 및 실행
EXPOSE 8080
CMD ["catalina.sh", "run"]