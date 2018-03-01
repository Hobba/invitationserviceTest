<img src="https://simque.de/img/logo_simque.png" width="auto" height="100"/>

## Invitation-Service

### _Tasks:_

* E-Mail-Versand per [Mailgun](https://www.mailgun.com/)
* Generierung von JWTs zur Nutzerauthentifizierung
* ...

### Umgebungsvariablen

### Welche Profile werden genutzt?

####_ACHTUNG:_

Vor dem Start der Anwendung muss unter `Run Configuration -> Arguments -> Programm Arguments`

_für lokale Testing-E-Mails:_  
`--spring.profiles.active=local`

_für Live-E-Mails:_  
`--spring.profiles.active=production`
ausgewählt werden.


﻿Beispiel:
mvn clean install -Dspring.profiles.active=local
alternativ:
export SPRING_PROFILES_ACTIVE=local

Profiles:
mock:  schaltet die lokale mongodb ab

﻿Beispiel:  
`mvn clean install -Dspring.profiles.active=local`  
alternativ:  
`export SPRING_PROFILES_ACTIVE=local`


### Konfiguration -->App.Properties

### JSON-Endpoints inkl. JSON-Struktur

### HTML-Endpoints

### Actuator-Endpoints (Togglz)

### Datenbankstruktur

