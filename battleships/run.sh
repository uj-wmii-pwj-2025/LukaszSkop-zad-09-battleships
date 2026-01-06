#!/bin/bash

PORT=7777
MAP=random_map.txt
HOST=localhost
JAR=app.jar

case "$1" in
  server)
    java -jar $JAR -mode server -port $PORT -map $MAP
    ;;
  client)
    java -jar $JAR -mode client -host $HOST -port $PORT -map $MAP
    ;;
  *)
    echo "Użycie: ./run.sh server | client"
    ;;
esac
