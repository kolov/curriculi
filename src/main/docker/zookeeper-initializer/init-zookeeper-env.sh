
$CMD -server $ZK create /config/application/redis.host redis
$CMD -server $ZK create /config/application/mongodb.host mongodb

$CMD -server $ZK create /config/application/spring.data.mongodb.uri=mongodb://mongo:27017/curri
$CMD -server $ZK create /config/application/spring.data.mongodb.database=curri
$CMD -server $ZK create /config/application/spring.data.mongodb.name=curriculi
