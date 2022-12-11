#!/bin/bash
#这是数据和日志的配置文件
export MINIO_DATA=/opt/Minio/data
export MINIO_LOG=/opt/Minio/logs

#这是启动后需要的用户名和密码
export MINIO_ROOT_USER=admin
export MINIO_ROOT_PASSWORD=12345678

# nohup启动服务 指定文件存放路径 /root/data 还有设置日志文件路径 /root/minio/log
#./minio server 192.168.10.103:9002 192.168.10.103:9001 ${MINIO_DATA} > ${MINIO_LOG}/minio.log 2>&1 &

 /opt/Minio/minio server ${MINIO_DATA} > ${MINIO_LOG}/minio.log 2>&1 &


tail -100f ${MINIO_LOG}/minio.log


