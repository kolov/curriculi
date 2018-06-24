# curri

Scripts to install curri

Before running the scripts, the following environment variables must be defined:
GTILAB_REG_TOKEN
URRI_GOGGLE_CLIENT_ID
CURRI_GOOGLE_CLIENT_SECRET

To install on GKE:
./src/main/k8s/curri-gke-all.sh

To install on any Kubernetes, after kubectl has been configured:
./src/main/k8s/curri-k8s.sh

