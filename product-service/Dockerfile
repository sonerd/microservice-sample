FROM openjdk:8u111-jre-alpine
EXPOSE 8080:8080
ADD ./build/libs/*.jar product-service.jar

#CMD /usr/bin/java -Xmx400m -Xms400m -jar product-service.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/product-service.jar"]
