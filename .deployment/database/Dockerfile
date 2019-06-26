FROM hseeberger/scala-sbt

WORKDIR /app

COPY ./database /app/database

COPY ./.deployment/database/build.sbt /app
COPY ./.deployment/database/application.conf /app/src/main/resources/application.conf

RUN sbt compile

ENTRYPOINT ["sbt", "project database", "run"]
