#!/usr/bin/env bash
set -euo pipefail # https://coderwall.com/p/fkfaqq/safer-bash-scripts-with-set-euxo-pipefail

COMPOSE="docker-compose"
EXEC="docker exec -i"
EXEC_BIN="bash"
EXEC_MVN="mvn"
PROJECT_HOSTNAME="localhost:8080"
TEST_ENVIRONMENT="local"
if [ $# -gt 0 ];then
    if [ "$1" == "start" ]; then
        $COMPOSE up -d

    elif [ "$1" == "stop" ]; then
        $COMPOSE stop

    elif [ "$1" == "clean" ]; then
        echo "Clean";
    elif [ "$1" == "build" ]; then
        $EXEC_MVN package
    elif [ "$1" == "system-integration" ]; then
        cd system-integration
        ./ht/ht_linux exec -output ./reports/ -Dfile ./env/local.json ./entity/...
    elif [ "$1" == "run" ]; then
        ./dev.sh build
        ./dev.sh start
        java -jar target/entityAPI-1.0-SNAPSHOT.jar > api-entity.log 2>&1
        echo "Started on $PROJECT_HOSTNAME"
        cat api-entity.log
    else
        $COMPOSE "$@"
    fi
else
    $COMPOSE ps
fi
