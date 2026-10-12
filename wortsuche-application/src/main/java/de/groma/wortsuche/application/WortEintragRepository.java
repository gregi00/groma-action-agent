package de.groma.wortsuche.application;

import de.groma.wortsuche.domain.WortEintrag;

import java.util.List;
import java.util.Optional;

public interface WortEintragRepository {
    List<WortEintrag> findeAlle();

    Optional<WortEintrag> findeNachWort(String wort);

    Optional<WortEintrag> findeNachId(Long id);

    WortEintrag speichern(WortEintrag eintrag);

    void loeschen(Long id);
}
