FROM openjdk:11-jdk
WORKDIR /app

COPY ./fan.yumetsuki.irotoridorino-sekai-api /app/fan.yumetsuki.irotoridorino-sekai-api
COPY ./static /app/static

EXPOSE 8080

CMD ./fan.yumetsuki.irotoridorino-sekai-api/bin/fan.yumetsuki.irotoridorino-sekai-api