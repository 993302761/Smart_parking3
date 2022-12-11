#!/bin/bash



pid=`ps -ef | grep 'minio server' | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi
echo "关闭minio..."
