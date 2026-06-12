#!/bin/bash
#http://localhost:4566/_localstack/health

awslocal --version
#Create the bucket
awslocal s3 mb s3://ecommerce-bucket

#Copy fie to the bucket
awslocal s3 cp test.txt s3://ecommerce-bucket

#Versioning enable
awslocal s3api put-bucket-versioning --bucket ecommerce-bucket --versioning-configuration Status=Enabled

#List versions
awslocal s3api list-object-versions --bucket ecommerce-bucket

#Apply policy
awslocal s3api put-bucket-policy --bucket ecommerce-bucket --policy file://policy.json