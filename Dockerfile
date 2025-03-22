# Stage 1: Build native executable
FROM ghcr.io/graalvm/native-image-community:21-ol9 AS builder
WORKDIR /app
COPY . .
RUN microdnf install findutils
RUN ./gradlew nativeCompile -Pgraalvm

# Stage 2: Runtime
FROM ubuntu:22.04
WORKDIR /app
COPY --from=builder /app/build/native/nativeCompile/check-local-licence /app/check-local-licence
RUN chmod +x /app/check-local-licence
EXPOSE 8080
ENTRYPOINT ["/app/check-local-licence"]