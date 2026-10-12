---
type: C4 Component
title: Wörterbuch-Anwendungsfälle
status: stable
groma:
  id: worteintragservice
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java
      symbol: WortEintragService
    - scanner: java
      file: wortsuche-application/src/main/java/de/groma/wortsuche/application/EintragExistiertBereitsException.java
      symbol: EintragExistiertBereitsException
    - scanner: java
      file: wortsuche-application/src/main/java/de/groma/wortsuche/application/EintragNichtGefundenException.java
      symbol: EintragNichtGefundenException
  technology: Java
description: Validiert und orchestriert Eintragsoperationen.
---

Der Service implementiert das Auflisten, Suchen, Anlegen und Löschen von Wörterbucheinträgen. Er prüft Eingaben und Duplikate, normalisiert Suchanfragen vor dem Repository-Aufruf und meldet fachliche Fehler über spezifische Ausnahmen.

## Relationships

| Source | Target | Description | Technology |
| --- | --- | --- | --- |
| [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java) | [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java) | Greift auf Einträge zu | Repository-Port |
| [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragService.java) | [wortsuche-domain/src/main/java/de/groma/wortsuche/domain/WortEintrag.java](../../../../../../wortsuche-domain/src/main/java/de/groma/wortsuche/domain/WortEintrag.java) | Verarbeitet Wörterbucheinträge | Java record |
