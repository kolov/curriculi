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

CURRI_DEST=$1
echo Will install to destination $CURRI_DEST

echo Will install curri in kubernetes context: $(kubectl config current-context)

Echo Setting secrets...

kubectl create secret docker-registry regcred --docker-server=registry.gitlab.com \
    --docker-username=assen.kolov@gmail.com \
    --docker-password=$GTILAB_REG_TOKEN \
    --docker-email=assen.kolov@gmail.com
$DIR/../secrets/curri-k8s-config.sh

if [[ -z "$CURRI_PROJECT" ]]; then
   CURRI_PROJECT="$( cd "$( dirname "$DIR/../../.." )" && pwd )"
   echo CURRI_PROJECT is not set
fi
echo Will use CURRI_PROJECT=$CURRI_PROJECT
$CURRI_PROJECT/curri-users/k8s/deploy-all-$CURRI_DEST.sh
$CURRI_PROJECT/curri-gate/k8s/deploy-all-$CURRI_DEST.sh
$CURRI_PROJECT/curri-elm/k8s/deploy-all-$CURRI_DEST.sh

kubectl apply -f $DIR/ingress-$CURRI_DEST.yml

