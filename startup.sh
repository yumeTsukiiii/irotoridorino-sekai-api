#!/bin/bash
docker stop blog.yumetsuki.fan
docker rm blog.yumetsuki.fan
docker rmi blog.yumetsuki.fan
docker build -t blog.yumetsuki.fan .
docker run -d -v ${pwd}:/app -p 8080:8080 --name blog.yumetsuki.fan blog.yumetsuki.fan