# Abk Client application (frontend)
# docker build -t tjonahen/abk-client  .
# VERSION 1.0.0
FROM tjonahen/http-server
MAINTAINER Philippe Tjon-A-Hen <philippe.tjonahen.nl>
COPY maven/dist content
#set environment variables 
COPY docker_env.js content/env.js
WORKDIR content
