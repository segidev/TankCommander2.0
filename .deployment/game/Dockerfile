FROM hseeberger/scala-sbt

RUN apt-get update && apt-get install xvfb -y

WORKDIR /app

COPY ./game /app/game

COPY ./.deployment/game/build.sbt /app
COPY ./.deployment/game/entrypoint.sh /app

RUN sbt compile

ENTRYPOINT ["/app/entrypoint.sh"]
