# curri

Scripts to install curri

Before running the scripts, the following environment variables must be defined:
GTILAB_REG_TOKEN

To create cluster on GKE and deploy the application:

    ./src/main/k8s/curri-gke-all.sh

To deploy on any Kubernetes, after `kubectl` has been configured:

    ./src/main/k8s/curri-k8s.sh provider

where provider=do,mininkube

## Tricks

To get service account token from a cluster, prepare file secret.json:

    {
        "kind": "Secret",
        "apiVersion": "v1",
        "metadata": {
            "name": "mysecretname",
            "annotations": {
                "kubernetes.io/service-account.name": "default"
            }
        },
        "type": "kubernetes.io/service-account-token"
    }

    kubectl create -f secret.json
    kubectl get secrets -n kube-system


