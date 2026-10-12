# Wortsuche

Eine kleine Spring-Boot-Webanwendung, in der Wörter mit einem zugehörigen Text gespeichert, gesucht und gelöscht werden können. Die Suche ignoriert Groß- und Kleinschreibung. Einträge werden in einer lokalen H2-Dateidatenbank gespeichert.

## Starten

Voraussetzung: Java 21 oder neuer und Maven.

```bash
mvn spring-boot:run -pl wortsuche-web -am
```

Danach ist die Anwendung unter [http://localhost:8080](http://localhost:8080) erreichbar. Die Datenbankdatei wird im Verzeichnis `data/` angelegt.

## Playwright-E2E-Tests

Die Browser-Tests starten die Anwendung automatisch mit einer flüchtigen H2-Datenbank. Zum lokalen Ausprobieren:

```bash
npm ci
npx playwright install chromium
npm run test:e2e
```

Der HTML-Bericht wird in `playwright-report/` erzeugt. Bei Pull Requests führt GitHub Actions die Tests automatisch aus, zeigt die Ergebnisse in der Workflow-Zusammenfassung an und hängt den ausführlichen Bericht als `playwright-report`-Artefakt an.

## Modulaufteilung

- `wortsuche-domain`: Fachliches Modell ohne Framework-Abhängigkeiten.
- `wortsuche-application`: Anwendungslogik und Repository-Port.
- `wortsuche-infrastructure`: JPA/H2-Persistenz und Adapter für den Repository-Port.
- `wortsuche-web`: Spring-Boot-Einstiegspunkt, Web-Controller und Thymeleaf-Oberfläche.

Die Abhängigkeiten zeigen nach innen: Die Domain kennt keine anderen Module; die Infrastruktur implementiert den von der Anwendung definierten Port.
