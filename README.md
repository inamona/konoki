# konoki

[![Build Status](https://travis-ci.org/inamona/konoki.svg?branch=master)](https://travis-ci.org/inamona/konoki)

How to start the konoki application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/konoki-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
