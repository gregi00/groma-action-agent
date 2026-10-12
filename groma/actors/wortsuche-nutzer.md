---
type: C4 Actor
title: Wortsuche-Nutzer
description: Verwaltet Wörterbucheinträge über die Webanwendung.
status: stable
groma:
  id: wortsuche-nutzer
---

Eine Person, die Wörter und ihre Texte speichert, nach ihnen sucht und Einträge löscht.

## Relationships

| Source | Target | Description | Technology |
| --- | --- | --- | --- |
| [wortsuche-nutzer](wortsuche-nutzer.md) | [wortsuche-web/src/main/java/de/groma/wortsuche/web/WortEintragController.java](../../wortsuche-web/src/main/java/de/groma/wortsuche/web/WortEintragController.java) | Verwaltet Wörterbucheinträge | Spring MVC, Thymeleaf |
| [wortsuche-nutzer](wortsuche-nutzer.md) | [wortsuche](../systems/wortsuche/system.md) | Nutzt die Suchoberfläche | Weboberfläche |
