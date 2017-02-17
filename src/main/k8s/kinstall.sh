#!/usr/bin/env bash

CURRI_ROOT=~/projects/curriculi
kubectl delete configmap curri-config
sh  $CURRI_ROOT/secrets/config-server/set-config-server-config.sh

kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-s.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-s.yml


kubectl delete -f $CURRI_ROOT/src/main/k8s/eureka-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/eureka-d.yml
kubectl delete -f $CURRI_ROOT/src/main/k8s/eureka-s.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/eureka-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl delete -f $CURRI_ROOT/src/main/k8s/mongo-users-s.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl delete -f $CURRI_ROOT/src/main/k8s/service-users-s.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-s.yml
