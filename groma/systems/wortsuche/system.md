---
type: C4 System
title: Wortsuche
description: Verwaltet Wörterbucheinträge und ihre Texte.
status: draft
groma:
  id: wortsuche
  technology: Java 21, Spring Boot, Maven
---

Die Anwendung bietet das Speichern, Suchen und Löschen von Wörterbucheinträgen. Das Spring-Boot-Webmodul nimmt Benutzeranfragen entgegen; die Application-Schicht validiert und normalisiert Eingaben und greift über einen Repository-Port auf die Persistenz zu. Die Maven-Module trennen Webzugriff, Anwendungsfälle, Domainmodell und Infrastruktur.
