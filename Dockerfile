# 빌드 스테이지
FROM gradle:8-jdk21 AS builder
WORKDIR /app

# 소스 복사 후 빌드
COPY . .
RUN gradle buildFatJar --no-daemon

# 실행 스테이지
FROM gcr.io/distroless/java21-debian12
WORKDIR /app
COPY --from=builder /app/server/build/libs/*-all.jar app.jar
EXPOSE 8080
CMD ["app.jar"]