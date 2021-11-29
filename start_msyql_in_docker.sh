#!/usr/bin/env bash

docker run \
 --env MYSQL_ROOT_PASSWORD=password \
 --env MYSQL_DATABASE=classicmodels \
 --name mysql \
 --publish 3306:3306 \
 --rm \
 --detach \
 mysql:8
