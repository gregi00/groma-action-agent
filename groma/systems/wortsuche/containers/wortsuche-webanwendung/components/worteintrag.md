---
type: C4 Component
title: Wörterbucheintrag
status: stable
groma:
  id: worteintrag
  parent: wortsuche-webanwendung
  code:
    - scanner: java
      file: wortsuche-domain/src/main/java/de/groma/wortsuche/domain/WortEintrag.java
      symbol: WortEintrag
  technology: Java record
description: Domänenmodell eines Wortes mit zugehörigem Text.
---

Der unveränderliche Domain-Record enthält ID, Wort und Text und schützt die fachliche Invariante, dass Wort und Text nicht leer sein dürfen.
