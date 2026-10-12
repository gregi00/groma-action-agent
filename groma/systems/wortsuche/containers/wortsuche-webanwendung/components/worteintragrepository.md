---
type: C4 Component
title: Wörterbuch-Repository-Port
status: stable
groma:
  id: worteintragrepository
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java
      symbol: WortEintragRepository
  technology: Java interface
description: Definiert den Persistenzvertrag für Einträge.
---

Die Application-Schicht hängt von diesem Repository-Port ab, nicht von einer konkreten Datenbank. Der Vertrag umfasst Listen, Wort- und ID-Suche, Speichern und Löschen von Wörterbucheinträgen.

## Relationships

| Source | Target | Description | Technology |
| --- | --- | --- | --- |
| [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java) | [wortsuche-domain/src/main/java/de/groma/wortsuche/domain/WortEintrag.java](../../../../../../wortsuche-domain/src/main/java/de/groma/wortsuche/domain/WortEintrag.java) | Definiert Eintragsoperationen | Java interface |
| [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java) | [wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java](../../../../../../wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java) | Wird zur Laufzeit implementiert | Spring dependency injection |
