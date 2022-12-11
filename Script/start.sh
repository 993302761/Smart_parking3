#!/bin/bash

 ./MinioScript/minio_start.sh &
 ./NacosScript/nacos_start.sh &
 ./RabbitmqScript/RabbitMQ_start.sh &