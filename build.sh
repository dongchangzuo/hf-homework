#!/bin/bash

COMMIT_HASH=$(git rev-parse --short=8 HEAD)
mvn clean package -s settings.xml
docker build -t crpi-pn2xwedazprppt1j.cn-beijing.personal.cr.aliyuncs.com/chadong/fraud_detection:$COMMIT_HASH -f Dockerfile .
docker push crpi-pn2xwedazprppt1j.cn-beijing.personal.cr.aliyuncs.com/chadong/fraud_detection:$COMMIT_HASH