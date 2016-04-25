FROM java:alpine
MAINTAINER Philippe Tjon-A-Hen (philippe@tjonahen.nl)

COPY target/classes /app/classes
COPY target/dependency /app/dependency

ENV PORT 80
EXPOSE 80

CMD java -cp /app/classes:/app/dependency/* com.kumuluz.ee.EeApplication
