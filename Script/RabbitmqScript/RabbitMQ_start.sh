#!/bin/bash

#nacos文件的目录
export RABBITMQ_PATH=/opt/rabbitmq-server-generic-unix-3.10.7/rabbitmq_server-3.10.7/sbin


cd ${RABBITMQ_PATH}


./rabbitmq-server start