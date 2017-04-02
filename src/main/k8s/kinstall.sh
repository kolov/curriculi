#!/usr/bin/env bash

# Comment out otr replace the following line
export CURRI_ROOT=~/projects/curriculi
export DOCKER_REGISTRY=391471456712.dkr.ecr.eu-central-1.amazonaws.com
if [ -z ${CURRI_ROOT+x} ]; then
  echo "CURRI_ROOT is unset"
  exit 1;
else
  echo "CURRI_ROOT is set to '$CURRI_ROOT'";
fi

kubectl delete configmap curri-config
sh  $CURRI_ROOT/secrets/config-server/set-config-server-config.sh


# Config server
kubectl delete -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/config-server-s.yml

#volumes
kubectl delete -f $CURRI_ROOT/src/main/k8s/aws-storage.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/aws-storage.yml
kubectl delete -f $CURRI_ROOT/src/main/k8s/aws-pvc.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/aws-pvc.yml

#service users DB
kubectl delete -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-users-s.yml

#service users
kubectl delete -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-users-s.yml

#service docs
kubectl delete -f $CURRI_ROOT/src/main/k8s/mongo-docs-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-docs-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/mongo-docs-s.yml

kubectl delete -f $CURRI_ROOT/src/main/k8s/service-docs-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-docs-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/service-docs-s.yml

#redis
kubectl delete -f $CURRI_ROOT/src/main/k8s/redis-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/redis-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/redis-s.yml


#web app
kubectl delete -f $CURRI_ROOT/src/main/k8s/app-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/app-d.yml
kubectl apply -f $CURRI_ROOT/src/main/k8s/app-s.yml

kubectl expose deployment curriculi --port=8080 --target-port=8080 --name=curriculi --type=LoadBalancer
