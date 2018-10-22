#!/bin/bash

export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo Script dir is $DIR

usage() {
  echo Usage:
  echo    curri-k8s destination
}

if [ $# -eq 0 ]; then
  echo Destination is not defined
  usage
  exit 1
fi

if [ -z "$GTILAB_REG_TOKEN" ]; then
  echo GTILAB_REG_TOKEN is not defined
  usage
  exit 1
fi



CURRI_DEST=$1
echo Will install to destination $CURRI_DEST

echo Will install curri in kubernetes context: $(kubectl config current-context)

Echo Setting secrets...
kubectl delete secret regcred 2> /dev/null

kubectl create secret docker-registry regcred --docker-server=registry.gitlab.com \
    --docker-username=assen.kolov@gmail.com \
    --docker-password=$GTILAB_REG_TOKEN \
    --docker-email=assen.kolov@gmail.com
$DIR/../secrets/curri-k8s-config.sh

if [[ -z "$CURRI_PROJECT" ]]; then
   CURRI_PROJECT="$( cd "$( dirname "$DIR/../../.." )" && pwd )"
   echo CURRI_PROJECT was not set, set to $CURRI_PROJECT
fi
echo Will use CURRI_PROJECT=$CURRI_PROJECT
$CURRI_PROJECT/curri-users/k8s/deploy-all-$CURRI_DEST.sh
$CURRI_PROJECT/curri-gate/k8s/deploy-all-$CURRI_DEST.sh
$CURRI_PROJECT/curri-elm/k8s/deploy-all-$CURRI_DEST.sh
$CURRI_PROJECT/curriculi-nginx/k8s/deploy-all-$CURRI_DEST.sh

# kubectl apply -f $DIR/ingress-$CURRI_DEST.yml

