FROM openjdk:17-alpine
RUN apk update && \
    apk add --no-cache tzdata
COPY cdm_html/ cdm_html/
ADD /out/artifacts/ModemAsker_jar/ModemAsker1.3c.jar modem_asker.jar
ENTRYPOINT ["java", "-jar", "modem_asker.jar"]