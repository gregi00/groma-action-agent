---
type: C4 Component
title: Wörterbuch-Persistenz
status: stable
groma:
  id: woerterbuch-persistenz
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java
      symbol: WortEintragRepositoryAdapter
    - scanner: java
      file: wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/SpringDataWortEintragRepository.java
      symbol: SpringDataWortEintragRepository
    - scanner: java
      file: wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragEntity.java
      symbol: WortEintragEntity
  technology: Spring Data JPA, Hibernate, H2
description: Passt den Repository-Port an Spring Data JPA und H2 an.
---

Der Infrastrukturadapter bildet Domain-Einträge auf JPA-Entities ab, implementiert den Repository-Port und delegiert Abfragen an Spring Data JPA. Persistiert wird ein normalisierter Wortschlüssel, über den Einträge ohne Beachtung von Groß- und Kleinschreibung gefunden werden.

## Relationships

| Source | Target | Description | Technology |
| --- | --- | --- | --- |
| [wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java](../../../../../../wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java) | [wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java](../../../../../../wortsuche-application/src/main/java/de/groma/wortsuche/application/WortEintragRepository.java) | Implementiert den Persistenzvertrag | Java adapter |
| [wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java](../../../../../../wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/WortEintragRepositoryAdapter.java) | [wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/SpringDataWortEintragRepository.java](../../../../../../wortsuche-infrastructure/src/main/java/de/groma/wortsuche/infrastructure/persistence/SpringDataWortEintragRepository.java) | Delegiert Datenbankabfragen | Spring Data JPA |
