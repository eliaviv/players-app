#!/usr/bin/env bash

deploy_postgres() {
  docker rm -f postgres-db
  docker run -d --name postgres-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123 -p 5432:5432 postgres
}

deploy_players_server() {
    docker rm -f players-app
    docker run -d -p 8080:8080 --name players-app players-app-image
}

deploy_postgres
deploy_players_server
