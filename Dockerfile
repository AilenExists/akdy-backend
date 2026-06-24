FROM gcr.io/distroless/java21-debian12
WORKDIR /app
COPY build/libs/*-all.jar app.jar

CMD ["app.jar"]