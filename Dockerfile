# syntax=docker/dockerfile:1
# 빌드 스테이지
FROM gradle:8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/home/gradle/.gradle,uid=1000,gid=1000 \
    gradle :server:installDist --no-daemon -x test

# 실행 스테이지
FROM gcr.io/distroless/java21-debian12
WORKDIR /app
COPY --from=builder /app/server/build/install/server/lib /app/lib
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app/lib/*", "dev.shaper.akdymall.MainKt"]