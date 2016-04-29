FROM java:alpine
MAINTAINER Philippe Tjon-A-Hen (philippe@tjonahen.nl)

COPY maven/classes /app/classes
COPY maven/dependency /app/dependency

ENV PORT 80
EXPOSE 80

CMD java -cp /app/classes:/app/dependency/* com.kumuluz.ee.EeApplication
