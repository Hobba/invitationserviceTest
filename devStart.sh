JAR_NAME=$1
$TOKEN_KEY=$2
kill `cat /home/centos/$JAR_NAME.pid`
export SPRING_PROFILES_ACTIVE=production
nohup java --token.key=$TOKEN_KEY -Dserver.servlet.context-path=/IS -jar /home/centos/$JAR_NAME > /dev/null 2>&1 &
echo $! > /home/centos/$JAR_NAME.pid