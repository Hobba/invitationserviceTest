Vor dem Start der Anwendung muss in der Run Configuration -> Arguments -> Programm Arguments
-
Für locale Testing Emails:
--spring.profiles.active=local

Für Live Emails:
--spring.profiles.active=production

ausgewählt werden.

﻿Beispiel:
mvn clean install -Dspring.profiles.active=local
alternativ:
export SPRING_PROFILES_ACTIVE=local

Profiles:
mock:  schaltet die lokale mongodb ab