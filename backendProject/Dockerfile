FROM maven:3.6-jdk-11 as build

# Copy the project files into the container
COPY src /home/app/src
COPY pom.xml /home/app


WORKDIR /home/app

# WAR 파일로 빌드 하면서 테스트 스킵
RUN mvn -e clean package -DskipTests


FROM tomcat:9.0-jdk11-openjdk


RUN rm -rf /usr/local/tomcat/webapps/*


COPY --from=build /home/app/target/*.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080