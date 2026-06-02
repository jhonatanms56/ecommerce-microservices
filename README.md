# Ecommerce Microservices Platform

This project implements a basic microservice architecture with three Spring Boot services: Order, Payment, and Shipping. It is designed to be deployed on Kubernetes (Minikube) with Istio service mesh and observability tools.

## Architecture
- **Language:** Java 21
- **Build Tool:** Gradle (Multi-module)
- **Framework:** Spring Boot 3.x
- **Infrastructure:** PostgreSQL, Redis, Nginx
- **Service Mesh:** Istio
- **Observability:** Prometheus, Grafana, Loki

## Project Structure
- `order-service`: Port 8081
- `payment-service`: Port 8082
- `shipping-service`: Port 8083
- `k8s/`: Kubernetes manifests for all components.

## Local Development

To run the services locally on your machine:

### 1. Start Infrastructure
Use the provided `docker-compose.yaml` to start PostgreSQL and Redis:
```bash
docker-compose up -d
```

### 2. Run a Microservice
You must activate the `local` profile so the service knows how to connect to your local database.

**Using Gradle:**
```bash
./gradlew :order-service:bootRun --args='--spring.profiles.active=local'
```

**Using IDE (IntelliJ):**
1. Edit Run Configuration.
2. Add VM Option: `-Dspring.profiles.active=local` OR set Environment Variable: `SPRING_PROFILES_ACTIVE=local`.

## Kubernetes Deployment
- Minikube installed and running.
- `kubectl` configured to your minikube cluster.
- Docker installed.

### 2. Build the Microservices
Configure your shell to use Minikube's Docker daemon:
```bash
eval $(minikube docker-env)
```

Build the images:
```bash
docker build -t order-service:latest -f order-service/Dockerfile .
docker build -t payment-service:latest -f payment-service/Dockerfile .
docker build -t shipping-service:latest -f shipping-service/Dockerfile .
```

### 3. Deploy to Kubernetes
Apply the configuration and infrastructure manifests:
```bash
kubectl apply -f k8s/config/
kubectl apply -f k8s/database/
kubectl apply -f k8s/redis/
kubectl apply -f k8s/nginx/
```

Apply the microservice manifests:
```bash
kubectl apply -f k8s/order/
kubectl apply -f k8s/payment/
kubectl apply -f k8s/shipping/
```

### 4. Accessing the Services
If using Nginx:
```bash
minikube service nginx-service --url
```
You can then access:
- `http://<minikube-ip>:<port>/orders/health`
- `http://<minikube-ip>:<port>/payments/health`
- `http://<minikube-ip>:<port>/shipping/health`

### 5. Istio & Observability
To enable Istio, ensure it is installed on your cluster and label the namespace:
```bash
kubectl label namespace default istio-injection=enabled
```

Apply Istio routing:
```bash
kubectl apply -f k8s/istio/
```

For Prometheus, Grafana, and Loki, it is recommended to use the [Istio Addons](https://istio.io/latest/docs/ops/integrations/prometheus/):
```bash
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.21/samples/addons/prometheus.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.21/samples/addons/grafana.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.21/samples/addons/kiali.yaml
```

## Documentation
Each service includes Swagger UI for API documentation:
- `http://<service-url>/swagger-ui.html`
