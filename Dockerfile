FROM jdk-11:v1
RUN mkdir /opt/app
COPY build/libs/perf-practise-latest.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
