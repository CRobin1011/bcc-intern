FROM ghcr.io/oracle/oraclelinux:9-slim

COPY target/internship app

ENTRYPOINT ["/app"]
