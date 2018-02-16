JAR_NAME=$1
kill `cat /home/centos/$JAR_NAME.pid`
export SPRING_PROFILES_ACTIVE=production
nohup java  -Dserver.servlet.context-path=/IS -jar /home/centos/$JAR_NAME > /dev/null 2>&1 &
echo $! > /home/centos/$JAR_NAME.pid