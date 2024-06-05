#!/bin/bash

# 获取所有节点的名称
nodes=$(kubectl get nodes -o jsonpath='{.items[*].metadata.name}')

# 打印标题
echo -e "Node\t\tAllocatable CPU (m)\tAllocatable Memory (Ki)\tAllocated CPU (m)\tAllocated Memory (Ki)\tFree CPU (m)\tFree Memory (Ki)"

# 遍历每个节点并获取所需信息
for node in $nodes; do
  # 获取可分配的 CPU 和内存
  allocatable_cpu=$(kubectl get node $node -o jsonpath='{.status.allocatable.cpu}')
  allocatable_memory=$(kubectl get node $node -o jsonpath='{.status.allocatable.memory}')
  
  # 转换 CPU 和内存单位
  allocatable_cpu_m=$(echo $allocatable_cpu | sed 's/m//')
  if [[ $allocatable_memory == *"Ki" ]]; then
    allocatable_memory_ki=$(echo $allocatable_memory | sed 's/Ki//')
  else
    allocatable_memory_ki=$(($allocatable_memory * 1024))
  fi
  
  # 获取已分配的 CPU 和内存
  allocated_cpu=0
  allocated_memory=0
  
  pods=$(kubectl get pods --all-namespaces -o json | jq -r ".items[] | select(.spec.nodeName==\"$node\") | .spec.containers[] | .resources.requests.cpu, .resources.requests.memory")
  
  while IFS= read -r line; do
    if [[ $line == *"m" ]]; then
      allocated_cpu=$(($allocated_cpu + ${line%m}))
    else
      allocated_cpu=$(($allocated_cpu + $line * 1000))
    fi
  done <<< "$(echo "$pods" | sed -n '1~2p')"
  
  while IFS= read -r line; do
    if [[ $line == *"Ki" ]]; then
      allocated_memory=$(($allocated_memory + ${line%Ki}))
    elif [[ $line == *"Mi" ]]; then
      allocated_memory=$(($allocated_memory + ${line%Mi} * 1024))
    elif [[ $line == *"Gi" ]]; then
      allocated_memory=$(($allocated_memory + ${line%Gi} * 1024 * 1024))
    fi
  done <<< "$(echo "$pods" | sed -n '2~2p')"
  
  # 计算剩余的 CPU 和内存
  free_cpu=$(($allocatable_cpu_m - $allocated_cpu))
  free_memory=$(($allocatable_memory_ki - $allocated_memory))
  
  # 打印结果
  echo -e "$node\t$allocatable_cpu_m\t\t$allocatable_memory_ki\t\t$allocated_cpu\t\t$allocated_memory\t\t$free_cpu\t\t$free_memory"
done
