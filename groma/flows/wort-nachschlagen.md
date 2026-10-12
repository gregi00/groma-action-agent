---
type: Groma Flow
title: Wort nachschlagen
groma:
  id: wort-nachschlagen
---

Eine Person sucht ein Wort. Der Webcontroller delegiert an den Anwendungsfall, der über den Repository-Port den Persistenzadapter und dessen Spring-Data-Implementierung erreicht.

## Steps

| From | To | Action |
| --- | --- | --- |
| [Wortsuche-Nutzer](../actors/wortsuche-nutzer.md) | [Wörterbuch-Webzugriff](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragcontroller.md) | Suchanfrage absenden |
| [Wörterbuch-Webzugriff](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragcontroller.md) | [Wörterbuch-Anwendungsfälle](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragservice.md) | Suche ausführen |
| [Wörterbuch-Anwendungsfälle](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragservice.md) | [Wörterbuch-Repository-Port](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragrepository.md) | Normalisiertes Wort nachschlagen |
| [Wörterbuch-Repository-Port](../systems/wortsuche/containers/wortsuche-webanwendung/components/worteintragrepository.md) | [Wörterbuch-Persistenz](../systems/wortsuche/containers/wortsuche-webanwendung/components/woerterbuch-persistenz.md) | Persistenzadapter aufrufen |
