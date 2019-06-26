FROM hseeberger/scala-sbt

WORKDIR /app

COPY ./game /app/game

COPY ./.deployment/game/build.sbt /app

RUN sbt compile

ENTRYPOINT ["sbt", "project game", "run"]
