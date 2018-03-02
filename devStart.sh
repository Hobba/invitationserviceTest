JAR_NAME=$1
TOKEN_KEY=$2
kill `cat /home/centos/$JAR_NAME.pid`
export SPRING_PROFILES_ACTIVE=production
nohup java -jar /home/centos/$JAR_NAME --token.key="$TOKEN_KEY" > /dev/null 2>&1 &
echo $! > /home/centos/$JAR_NAME.pid
echo "{\"service\": \"invitationservice\", \"deployment\" : \"success\"}" | nc 194.45.211.190 5000
