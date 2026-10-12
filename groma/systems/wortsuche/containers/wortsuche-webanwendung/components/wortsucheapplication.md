---
type: C4 Component
title: Anwendungsstart
status: stable
groma:
  id: wortsucheapplication
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-web/src/main/java/de/groma/wortsuche/WortsucheApplication.java
      symbol: WortsucheApplication
  technology: Spring Boot
description: Startet den Spring-Boot-Laufzeitkontext.
---

Der Einstiegspunkt startet die Wortsuche als Spring-Boot-Anwendung und aktiviert die komponentenbasierte Zusammensetzung des Webprogramms.
