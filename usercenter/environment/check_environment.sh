#!/bin/bash
environment_file="environment"

cat $environment_file | while read line
do
    if [ ! -z "$line" ]
    then
        suc=`nc -z $line | grep succeeded`
        if [ -z "$suc" ]
        then
            echo "$line is not ready!" 
        fi
    fi
done
