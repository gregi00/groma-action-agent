---
type: C4 Component
title: Wörterbuch-Webzugriff
status: stable
groma:
  id: worteintragcontroller
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-web/src/main/java/de/groma/wortsuche/web/WortEintragController.java
      symbol: WortEintragController
  technology: Spring MVC, Thymeleaf
description: Verarbeitet Webanfragen für Wörterbucheinträge.
---

Der Spring-MVC-Controller zeigt die Einträge und Suchergebnisse an und nimmt Formularaktionen zum Anlegen und Löschen entgegen. Er delegiert die Fachlogik an den WortEintragService und meldet Ergebnisse über die Weboberfläche zurück.

## Relationships

| Source | Target | Description | Technology |
| --- | --- | --- | --- |
| [wortsuche-web/src/main/java/de/groma/wortsuche/web/WortEintragController.java](../../../../../../wortsuche-web/src/main/java/de/groma/wortsuche/web/WortEintragController.java) | [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java) | Orchestriert Eintragsfälle | Spring dependency injection |
