#!/bin/bash

echo "Compiling" && mvn package -q || exit 1
echo "Done"

java -Xmx16m -Xms16m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow apache 10 200000
java -Xmx256m -Xms256m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow apache 10 200000

java -Xmx16m -Xms16m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow google 10 200000
java -Xmx256m -Xms256m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow google 10 200000

java -Xmx16m -Xms16m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow j11 10 200000
java -Xmx256m -Xms256m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow j11 10 200000
java -Xmx256m -Xms256m -jar ./target/hbench-1.0-SNAPSHOT.jar undertow j11_2_0 10 200000