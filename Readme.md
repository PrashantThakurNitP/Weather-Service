**Start Jenkins server**

brew services start jenkins-lts

**ReStart Jenkins server**

brew services restart jenkins-lts

**Stop Jenkins server**

brew services stop jenkins-lts

**start minikube**

minikube start  

**Get nodes**

kubectl get nodes

**Get pods**

kubectl get pods

**Delete pod**

kubectl delete pod <pod_name>

**Get cluster ip**

minikube ip

**Get all services**

minikube service --all

**stop minikube**

minikube stop

**Delete minikube cluster**

minikube delete

**Access Service**
get service ip and node port

http://<minikube-ip>:<node-port>

**Get Deployment**
kubectl get deployments 

**Get Replicaset**
kubectl get replicasets

**Get service**

kubectl get services <service_name>

**To check log of service**

kubectl logs <pod_name>

**List all service**

kubectl get svc



