---
type: C4 Container
title: Wortsuche-Webanwendung
status: stable
groma:
  id: wortsuche-webanwendung
  parent: wortsuche
  technology: Spring Boot 3.4.5, Spring MVC, Thymeleaf, Java 21
description: Spring-Boot-Anwendung mit Weboberfläche für Wörterbucheinträge.
---

Ein deploybares Spring-Boot-Webprogramm. Der Controller rendert die Wortsuche und delegiert Eintragsoperationen an die Application-Schicht. Das Modul startet den Laufzeitkontext; Domain- und Infrastrukturcode werden über Maven-Abhängigkeiten eingebunden.
