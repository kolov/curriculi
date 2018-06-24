#!/bin/bash

# Sets up a GKE cluster and runs curri-k8s.sh gke
CLUSTER_NAME=cluster-curri
PROJECT_NAME=burnished-block-834
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"


if gcloud beta container --project "$PROJECT_NAME" clusters list | grep $CLUSTER_NAME ; then
 echo Cluster $CLUSTER_NAME already exists
else
 gcloud beta container --project "$PROJECT_NAME" clusters \
  create "$CLUSTER_NAME" --zone "europe-west1-b" --username "admin" \
  --cluster-version "1.7.14-gke.1" \
  --machine-type "f1-micro" --num-nodes "8" \
  --image-type "COS" \
  --disk-size "100" --scopes "https://www.googleapis.com/auth/compute","https://www.googleapis.com/auth/devstorage.read_only","https://www.googleapis.com/auth/logging.write","https://www.googleapis.com/auth/monitoring","https://www.googleapis.com/auth/servicecontrol","https://www.googleapis.com/auth/service.management.readonly","https://www.googleapis.com/auth/trace.append" \
  --network "default" --enable-cloud-logging --enable-cloud-monitoring \
  --subnetwork "default" --enable-legacy-authorization \
  --verbosity=debug
fi

echo GKE cluster found, installing curri
echo The following must have been executed once on GKE:
echo gcloud compute addresses create curri-ip --region europe-west1
echo gcloud compute addresses describe curri-ip --region europe-west1

$DIR/curri-k8s.sh gke

#  --machine-type "g1-small" --num-nodes "3" \