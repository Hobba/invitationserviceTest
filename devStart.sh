JAR_NAME=$1
kill `cat /home/centos/$JAR_NAME.pid`
export SPRING_PROFILES_ACTIVE=production
nohup java -jar /home/centos/$JAR_NAME > /dev/null 2>&1 &
echo $! > /home/centos/$JAR_NAME.pid