package de.groma.wortsuche.application;

public class EintragExistiertBereitsException extends RuntimeException {
    public EintragExistiertBereitsException(String wort) {
        super("Für „" + wort + "“ gibt es bereits einen Eintrag.");
    }
}
