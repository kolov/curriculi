#!/usr/bin/env bash

# Comment out otr replace the following line
export CURRI_ROOT=~/projects/curriculi

if [ -z ${CURRI_ROOT+x} ]; then
  echo "CURRI_ROOT is unset"
  exit 1;
else
  echo "CURRI_ROOT is set to '$CURRI_ROOT'";
fi

kubectl delete configmap curri-config
sh  $CURRI_ROOT/secrets/config-server/set-config-server-config.sh

kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/redis-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/redis-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/redis-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/app-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/app-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/app-s.yml
