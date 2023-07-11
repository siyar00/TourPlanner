# TourPlanner_BhinderYamin
Bearbeiter: Siyar Yamin, Joben Preet Bhinder

## EINLEITUNG

Dies ist das Protokoll zum Semesterprojekt von Software Engineering 2.

Bei dem Projekt sollten wir eine Java-FX Applikation erstellen, bei der der Benutzer
Touren erstellen, löschen, bearbeiten und exportieren kann. Die Touren werden in einer
Postgres Datenbank gespeichert. Außerdem sollte die Applikation mit einem HTTP/REST-based
Server verbunden sein, der die Datenbankanfragen verarbeitet.

Im folgenden Protokoll werden die einzelnen Packages und deren Klassen beschrieben, sowie
die Funktionalität der Applikation.

Wir dürfen unseren TourPlanner präsentieren: **Dora the Explorer!**

![Dora the Explorer](Application/src/main/resources/at/technikum/planner/images/dora.png)

## APP ARCHITEKTUR
Wir verwenden Layered Architecture - dabei wird das Projekt in verschiedene Module
(Application, Business, Data Access) aufgeteilt. Die einzelnen Module sind voneinander
entkoppelt - dies dient dazu, dass die einzelnen Module unabhängig voneinander entwickelt
werden können. Dadurch wird die Wartbarkeit und die Erweiterbarkeit des Projekts verbessert.

Jede Schicht hat klare Abhängigkeiten zu den darüber und darunter liegenden Schichten.
Dadurch entsteht eine klare Struktur und eine klare Kommunikation zwischen den Schichten. 
Änderungen in einer Schicht haben in der Regel keine Auswirkungen auf andere Schichten, 
solange die Schnittstellen zwischen den Schichten stabil bleiben.

### Application
Das Application Module ist das oberste Modul, welches die anderen Module verwendet.
Es enthält die Main Klasse, welche die Applikation startet. In dieser Schicht haben wir das
'architectural pattern' MVVM (Model-View-ViewModel) verwendet, damit die Business
Logic der Anwendung sauber von dem User-Interface getrennt werden kann. 

Diese Schicht ist gegliedert in:

#### - Model
Das Model enthält die Klassen, welche die Daten der Applikation repräsentieren.
Diese Klassen sind die _Tour_, die _TourLog_ und die _Adress_ Klasse.

Das Model enthält die Daten und die Methoden, um auf diese Daten zuzugreifen und sie zu manipulieren. 
Es bildet die Grundlage für die Anwendungslogik und stellt die Schnittstelle zwischen der View und dem ViewModel dar.

#### - View
Dieses Package enthält die Controller, welche die einzelnen Views steuern. Die
Controller sind mit den einzelnen Views verknüpft und steuern die Interaktion
mit dem User. Zudem enthält es ein Unterpackage _Dialogs_, welches die Dialoge
der Applikation enthält, bei denen der User mit der Applikation interagieren kann.

Die View ist für die Anzeige der Daten zuständig und reagiert auf Benutzerereignisse, leitet diese an das ViewModel 
weiter und aktualisiert die Darstellung entsprechend.

#### - ViewModel
Das ViewModel ist die Schnittstelle zwischen dem Model und der View. Es enthält
die Logik der Applikation, welche die Daten aus dem Model verarbeitet und an die
View weitergibt. 

Das ViewModel enthält die Präsentationslogik und stellt die Daten bereit, 
die von der View angezeigt werden sollen.

### Business
Das Business Module enthält die Business Logic der Applikation. Es enthält die
Klassen _PDFServiceImpl_ für das Generieren von PDFs und _RouteServiceImpl_ für
das Berechnen der Route.

### Data Access
Das Data Access Module enthält die Klassen, welche die Datenbankanfragen verarbeiten.
Es dient für die Kommunikation mit der Datenbank und stellt die Schnittstelle zwischen dem Business Layer
und der eigentlichen Datenbank dar.

## USE CASE DIAGRAM
![Use Case Diagram](Application/src/main/resources/at/technikum/planner/images/UseCase_Tourplanner.jpg)

## UX - WIREFRAME 
![Wireframe](Application/src/main/resources/at/technikum/planner/images/Wireframe_Tourplanner.jpg)
Tatsächlich haben wir uns relativ genau an das Wireframe gehalten. Wir haben
uns an die Positionierung der einzelnen Elemente gehalten und auch die Farben
sind relativ ähnlich. Allerdings haben wir uns dazu entschieden, einen Dark-Mode
einzuimplementieren, da wir der Meinung sind, dass dieser besser aussieht.

## LIBRARY DECISIONS


## DESIGN PATTERNS
Zum einen verwenden wir das **MVVM** Pattern, um die Business Logic von dem User-Interface
zu trennen. Außerdem verwenden wir das **Factory** Pattern, um die einzelnen Services zu erstellen.
Es ist ein Entwurfsmuster, das zur Erzeugung von Objekten verwendet wird. 
Es gehört zur Kategorie der Erzeugungsmuster (Creational Patterns).
Das Factory Pattern ermöglicht die Abstraktion der Objekterzeugung, indem es eine gemeinsame Schnittstelle für die
Erstellung von Objekten definiert, anstatt sie direkt im Code zu instanziieren.
(siehe Klasse _ControllerFactory_)

Zudem verwenden wir das Oberserver Pattern, um die Views zu aktualisieren, wenn sich die Daten ändern.
Das Observer Pattern ist ein Entwurfsmuster, das aus einem Objekt, dem sogenannten Subjekt, und einer Liste von
abhängigen Objekten, den sogenannten Beobachtern, besteht. Ein Beobachter kann sich bei einem Subjekt registrieren,
um über Änderungen informiert zu werden. (siehe Klasse _TourLogViewModel_)

## UNIT TESTS



## UNIQUE FEATURES
Zum einen haben wir wie bereits erwähnt einen Dark-Mode implementiert.

Zudem haben wir aber auch eine Wetter-API eingebunden, die das Wetter der
jeweiligen Tour anzeigt.

## ZEIT MANAGEMENT (+tracked time)
Wir haben zwei Wochen vor Abgabe intensiv begonnen, an dem Projekt zu arbeiten.
Seit Projektbeginn haben wir jeden Tag ca. 4-6 Stunden an dem Projekt gearbeitet.

Geschätzter Zeitaufwand: 80 Stunden

## LESSONS LEARNED
Wir haben einige Sachen mitnehmen können, vor allem was die Planung der Software
betrifft. Wir haben gelernt, dass es sehr wichtig ist, sich vorher genau zu überlegen,
welche Anforderungen zu erfüllen sind, um Missverständnisse zu vermeiden und eine klare Richtung
für das Projekt zu haben.

Wir haben uns auch nochmals sehr viel mit dem MVVM-Pattern beschäftigt und haben und haben das Design sowie die 
Architektur im Voraus geplant, da eine solide Architektur das Fundament für eine skalierbare und wartbare Applikation bietet.

Auch haben wir das gemeinsame Arbeiten mit Git verbessert und haben gelernt, wie man
mit Git effizient im Team arbeitet.

## GIT
Da unser GitHub Repository, das wir für unser Projekt verwendet haben, 
auf privat gestellt ist, haben wir Sie als contributor eingeladen. 
Sie müssten eine Mail bekommen haben und können die Einladung akzeptieren.

https://github.com/siyar00/TourPlanner.git