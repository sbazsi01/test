#!/usr/bin/env bash
MYSQL_ROOT_PASS=password
DB_NAME=classicmodels

docker run \
 --rm \
 --name mysql \
 --env MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASS \
 --env MYSQL_DATABASE=$DB_NAME \
 --publish 3306:3306 \
 --volume "$(dirname "$0")/sql/classicmodels.sql:/docker-entrypoint-initdb.d/classicmodels.sql" \
mysql:8
