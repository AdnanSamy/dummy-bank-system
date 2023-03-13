# dummy-bank-system
 #### Dummy system that streamed transaction using kafka from dummy transaction service and consume by stream proccessor service and change the transction amount to idr and transfer to another topic and consume by transaction consumer then put into database
 
### How to Setup
* cd kafka
* mvn clean install -DskipTests
* cd ..
* cd ./transaction
* mvn clean package -DskipTests
* docker . -t com.dummy.bank.system/transaction
* cd ..
* cd ./proccessor
* mvn clean package -DskipTests
* docker . -t com.dummy.bank.system/proccessor
* cd ..
* cd ./transaction-consumer
* mvn clean package -DskipTests
* docker . -t com.dummy.bank.system/transaction-consumer
* cd ..
* cd ./docker-compose
* docker-compose up
* access localhost:8082/api/transaction for access list streamed transaction 
* access localhost:8082/api/transaction/to/{to} for access list streamed transaction filter by destination account name
* access localhost:8082/api/transaction/from/{from} for access list streamed transaction filter by origin account name
