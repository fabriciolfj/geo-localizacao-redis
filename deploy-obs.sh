#!/bin/bash

# Script para aplicar os YAMLs do Prometheus e Grafana no Kubernetes
set -e

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}[INFO]${NC} Aplicando configurações do Prometheus e Grafana..."

# Diretório dos arquivos YAML
YAML_DIR="observabilidade"

# Verificar se o diretório existe
if [ ! -d "$YAML_DIR" ]; then
    echo -e "${RED}[ERROR]${NC} Diretório $YAML_DIR não encontrado!"
    exit 1
fi

# Aplicar todos os YAMLs do diretório
echo -e "${BLUE}[INFO]${NC} Aplicando todos os YAMLs do diretório $YAML_DIR..."
kubectl apply -f ${YAML_DIR}/

echo -e "${GREEN}[SUCCESS]${NC} Todos os recursos foram aplicados!"

# Aguardar pods ficarem prontos
echo -e "${BLUE}[INFO]${NC} Aguardando pods ficarem prontos..."
kubectl wait --for=condition=ready pod -l app=prometheus -n monitoring --timeout=300s
kubectl wait --for=condition=ready pod -l app=grafana -n monitoring --timeout=300s

echo -e "${GREEN}[SUCCESS]${NC} Deploy concluído!"

# Mostrar status
echo -e "${BLUE}[INFO]${NC} Status dos pods:"
kubectl get pods -n monitoring

echo ""
echo "🔍 PROMETHEUS: kubectl port-forward -n monitoring svc/prometheus-service 9090:8080"
echo "📊 GRAFANA: kubectl port-forward -n monitoring svc/grafana-service 3000:3000"
echo "   Login: admin/admin"