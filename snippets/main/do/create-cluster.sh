#!/usr/bin/env bash

docker-machine create --driver digitalocean \
  --digitalocean-image ubuntu-16-04-x64 \
  --digitalocean-access-token $DOTOKEN \
  --digitalocean-region=fra1 \
  --digitalocean-size=512mb \
  --digitalocean-ssh-key-fingerprint=50:b8:ad:ed:16:c8:22:c4:43:60:28:aa:26:40:00:b3 \
  kube1