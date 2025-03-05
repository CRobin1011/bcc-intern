FROM ghcr.io/oracle/oraclelinux:9-slim

COPY ./target/ignit app

ENTRYPOINT ["/app"]
