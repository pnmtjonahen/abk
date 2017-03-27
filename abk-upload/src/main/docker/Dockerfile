FROM java:alpine
MAINTAINER Philippe Tjon-A-Hen (philippe@tjonahen.nl)

COPY maven/classes /app/classes
COPY maven/dependency /app/dependency

ENV PORT 80
EXPOSE 80

CMD java -Djava.util.logging.SimpleFormatter.format='%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n' -cp /app/classes:/app/dependency/* com.kumuluz.ee.EeApplication
