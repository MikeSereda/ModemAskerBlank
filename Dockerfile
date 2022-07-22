FROM openjdk:17-alpine
COPY cdm_html/ cdm_html/
ADD /out/artifacts/ModemAsker_jar/ModemAsker.jar modem_asker.jar
ENTRYPOINT ["java", "-jar", "modem_asker.jar"]