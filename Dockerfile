FROM gradle:7.3.3-jdk17 as build

WORKDIR /usr/src/app

COPY build.gradle settings.gradle ./

COPY src ./src/

RUN gradle clean build

FROM tomcat:10-jdk17 as production

WORKDIR /usr/local/tomcat/webapps/

COPY --from=build /usr/src/app/build/libs/chatapp.war ./

CMD ["catalina.sh", "run"]