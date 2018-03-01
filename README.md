<img src="https://simque.de/img/logo_simque.png" width="auto" height="100"/>

## Invitation-Service

### _Tasks:_

* E-Mail-Versand per [Mailgun](https://www.mailgun.com/)
* Generierung von JWTs zur Nutzerauthentifizierung
* Bereitstellung der Landingpage www.simque.de

### Umgebungsvariablen

* Secret für Token

### Welche Profile werden genutzt?

1. Local
2. Production
3. Mock: schaltet die lokale mongodb ab

####_ACHTUNG:_

Vor dem Start der Anwendung muss unter `Run Configuration -> Arguments -> Programm Arguments`

_für lokale Testing-E-Mails:_  
`--spring.profiles.active=local`

_für Live-E-Mails:_  
`--spring.profiles.active=production`
ausgewählt werden.

﻿Beispiel:  
`mvn clean install -Dspring.profiles.active=local`  
alternativ:  
`export SPRING_PROFILES_ACTIVE=local`


### Konfiguration

Siehe *application.properties* und *application-production.properties*

### JSON-Endpoints inkl. JSON-Struktur

* InvitationServiceController

_*JSON-Struktur:*_

Noch nicht abbildbar, da Work in Progress

### HTML-Endpoints

* https://www.simque.de
* https://www.simque.de/goToConfirmation

### Actuator-Endpoints (Togglz)

* Keine Actuators

### Datenbankstruktur

* mongoDB

Zwei Tabellen:

* Creators
* Participants


