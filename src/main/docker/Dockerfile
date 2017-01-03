FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD eir-code-service-0.0.1-SNAPSHOT.jar eir-code-service.jar
RUN sh -c 'touch /eir-code-service.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /eir-code-service.jar" ]
