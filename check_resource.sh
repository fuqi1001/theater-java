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
  allocated_resources=$(kubectl describe node $node | awk '/Allocated resources/,/events/')
  allocated_cpu=$(echo "$allocated_resources" | grep -m 1 "cpu" | awk '{print $3}' | sed 's/m//')
  allocated_memory=$(echo "$allocated_resources" | grep -m 1 "memory" | awk '{print $3}' | sed 's/Ki//')
  
  # 计算剩余的 CPU 和内存
  free_cpu=$(($allocatable_cpu_m - $allocated_cpu))
  free_memory=$(($allocatable_memory_ki - $allocated_memory))
  
  # 打印结果
  echo -e "$node\t$allocatable_cpu_m\t\t$allocatable_memory_ki\t\t$allocated_cpu\t\t$allocated_memory\t\t$free_cpu\t\t$free_memory"
done
