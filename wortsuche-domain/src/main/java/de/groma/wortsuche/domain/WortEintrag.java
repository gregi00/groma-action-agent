package de.groma.wortsuche.domain;

public record WortEintrag(Long id, String wort, String text) {
    public WortEintrag {
        if (wort == null || wort.isBlank()) {
            throw new IllegalArgumentException("Das Wort darf nicht leer sein.");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Der zugehörige Text darf nicht leer sein.");
        }
    }
}
