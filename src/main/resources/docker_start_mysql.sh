#!/usr/bin/env bash
MYSQL_ROOT_PASS=password
DB_NAME=classicmodels

docker run \
--name mysql \
 --env MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASS \
 --env MYSQL_DATABASE=$DB_NAME\
 --rm \
 --detach \
 --publish 3306:3306 \
 --volume $(pwd)/sql/classicmodels.sql:/docker-entrypoint-initdb.d/classicmodels.sql:ro \
mysql:8

