package de.groma.wortsuche.application;

import de.groma.wortsuche.domain.WortEintrag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class WortEintragService {
    private final WortEintragRepository repository;

    public WortEintragService(WortEintragRepository repository) {
        this.repository = repository;
    }

    public List<WortEintrag> alleFinden() {
        return repository.findeAlle();
    }

    public Optional<WortEintrag> findeNachId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return repository.findeNachId(id);
    }

    public Optional<WortEintrag> suchen(String wort) {
        if (wort == null || wort.isBlank()) {
            return Optional.empty();
        }
        return repository.findeNachWort(normalisiere(wort));
    }

    public WortEintrag hinzufuegen(String wort, String text) {
        String bereinigtesWort = wort == null ? "" : wort.strip();
        String bereinigterText = text == null ? "" : text.strip();
        if (bereinigtesWort.isBlank()) {
            throw new IllegalArgumentException("Bitte gib ein Wort ein.");
        }
        if (bereinigterText.isBlank()) {
            throw new IllegalArgumentException("Bitte gib einen Text ein.");
        }
        if (repository.findeNachWort(normalisiere(bereinigtesWort)).isPresent()) {
            throw new EintragExistiertBereitsException(bereinigtesWort);
        }
        return repository.speichern(new WortEintrag(null, bereinigtesWort, bereinigterText));
    }

    public WortEintrag aktualisieren(Long id, String wort, String text) {
        if (id == null || repository.findeNachId(id).isEmpty()) {
            throw new EintragNichtGefundenException();
        }

        String bereinigtesWort = wort == null ? "" : wort.strip();
        String bereinigterText = text == null ? "" : text.strip();
        if (bereinigtesWort.isBlank()) {
            throw new IllegalArgumentException("Bitte gib ein Wort ein.");
        }
        if (bereinigterText.isBlank()) {
            throw new IllegalArgumentException("Bitte gib einen Text ein.");
        }

        Optional<WortEintrag> vorhandenerEintrag = repository.findeNachWort(normalisiere(bereinigtesWort));
        if (vorhandenerEintrag.isPresent() && !vorhandenerEintrag.get().id().equals(id)) {
            throw new EintragExistiertBereitsException(bereinigtesWort);
        }

        return repository.speichern(new WortEintrag(id, bereinigtesWort, bereinigterText));
    }

    public void loeschen(Long id) {
        if (id == null || repository.findeNachId(id).isEmpty()) {
            throw new EintragNichtGefundenException();
        }
        repository.loeschen(id);
    }

    public static String normalisiere(String wort) {
        return wort.strip().toLowerCase(Locale.ROOT);
    }
}
