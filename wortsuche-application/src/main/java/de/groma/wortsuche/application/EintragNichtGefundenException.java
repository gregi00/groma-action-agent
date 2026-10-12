package de.groma.wortsuche.application;

public class EintragNichtGefundenException extends RuntimeException {
    public EintragNichtGefundenException() {
        super("Der Eintrag wurde nicht gefunden.");
    }
}
