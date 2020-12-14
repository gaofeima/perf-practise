#!/bin/bash

root_dir=`pwd`/

command=$1

if [[ "$command" == "build" ]]
then
    docker run --user $(id -u):$(id -g) -v /etc/passwd:/etc/passwd -v $root_dir:/code/ --entrypoint ''  -w /code/ jdk-11:v1 ./gradlew build
    docker build -t feimagao/perf-practise-cpu:high -f Dockerfile .
fi

if [[ "$command" == "run" ]]
then
    docker run --name perf-practise -p 10000:8080 -itd feimagao/perf-practise-cpu:high
fi

if [[ "$command" == "clean" ]]
then
    docker rm -f perf-practise || echo "Container already deleted."
fi

if [[ "$command" == "refresh" ]]
then
    docker rm -f perf-practise || echo "Container already deleted."
    docker run --user $(id -u):$(id -g) -v /etc/passwd:/etc/passwd -v $root_dir:/code/ --entrypoint ''  -w /code/ jdk-11:v1 ./gradlew build
    docker build -t feimagao/perf-practise-cpu:high -f Dockerfile .
    docker run --name perf-practise -p 10000:8080 -itd feimagao/perf-practise-cpu:high
fi

if [[ "$command" == "push" ]]
then
    docker push feimagao/perf-practise-cpu:high
fi