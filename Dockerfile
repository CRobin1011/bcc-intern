FROM ghcr.io/graalvm/graalvm-ce:latest AS build

COPY target/ignit app

ENTRYPOINT ["/app"]
