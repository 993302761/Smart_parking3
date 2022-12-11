#!/bin/bash



echo "开始重启服务..."
sudo /opt/Minio/minio_stop.sh
echo "开启服务..."
sudo /opt/Minio/minio_start.sh


