FROM hseeberger/scala-sbt

WORKDIR /app

COPY ./database /app/database

COPY ./.deployment/database/build.sbt /app

RUN sbt compile

ENTRYPOINT ["sbt", "project database", "run"]
