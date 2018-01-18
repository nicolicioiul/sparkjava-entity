#!/usr/bin/env bash
set -euo pipefail # https://coderwall.com/p/fkfaqq/safer-bash-scripts-with-set-euxo-pipefail

COMPOSE="docker-compose"
EXEC="docker exec -i"
EXEC_BIN="bash"
EXEC_MVN="mvn"
PROJECT_HOSTNAME="localhost:8080"
TEST_ENVIRONMENT="local"
if [ $# -gt 0 ];then
    if [ "$1" == "connect" ]; then
        $EXEC -t $2 bash
    elif [ "$1" == "start" ]; then
        $COMPOSE up -d
    elif [ "$1" == "stop" ]; then
        $COMPOSE stop
    elif [ "$1" == "clean" ]; then
        echo "Clean";
    elif [ "$1" == "build" ]; then
        $EXEC_MVN package
    elif [ "$1" == "system-integration" ]; then
        ./dev.sh build
        ./dev.sh stop
        ./dev.sh start
        sleep 2
        cd system-integration
        ./ht/ht_linux exec -output ./reports/ -Dfile ./env/docker.json ./entity/...
    elif [ "$1" == "run" ]; then
        ./dev.sh build
        ./dev.sh start
    elif [ "$1" == "run" ]; then
        ./dev.sh build
        ./dev.sh start
    else
        $COMPOSE "$@"
    fi
else
    $COMPOSE ps
fi
