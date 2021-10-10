FROM maven:ibmjava-alpine
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN chmod +x ./wait_for_it.sh
RUN mvn package -Dmaven.test.skip
CMD [ "sh", "-c", "mvn spring-boot:run" ]


