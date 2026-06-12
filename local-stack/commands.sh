#!/bin/bash
echo "Executing commands"
#awslocal dynamodb create-table --table-name Users --attribute-definitions AttributeName=UserId,AttributeType=S --key-schema AttributeName=UserId,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
#echo "User table created"

#awslocal dynamodb put-item --table-name=Usuarios --item '{"UserId":{"S":"001"},"Name":{"S":"Jhonatan Montes"},"Age":{"N":"35"},"Email":{"S":"jhonatan@sample.com"}}'

awslocal dynamodb get-item --table-name Usuarios --key '{"UserId":{"S":"001"}}'