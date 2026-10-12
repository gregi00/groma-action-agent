package de.groma.wortsuche.application;

import de.groma.wortsuche.domain.WortEintrag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WortEintragServiceTest {
    private final WortEintragRepository repository = mock(WortEintragRepository.class);
    private final WortEintragService service = new WortEintragService(repository);

    @Test
    void speichertBereinigtenEintrag() {
        WortEintrag eintrag = new WortEintrag(null, "Hallo", "Eine Begrüßung");
        when(repository.findeNachWort("Hallo")).thenReturn(Optional.empty());
        when(repository.speichern(eintrag)).thenReturn(new WortEintrag(1L, "Hallo", "Eine Begrüßung"));

        WortEintrag gespeichert = service.hinzufuegen(" Hallo ", " Eine Begrüßung ");

        assertEquals(1L, gespeichert.id());
        verify(repository).speichern(eintrag);
    }

    @Test
    void verhindertDoppelteEintraege() {
        when(repository.findeNachWort("Hallo")).thenReturn(Optional.of(
                new WortEintrag(1L, "Hallo", "Eine Begrüßung")));

        assertThrows(EintragExistiertBereitsException.class,
                () -> service.hinzufuegen("Hallo", "Noch eine Begrüßung"));
    }

    @Test
    void findetKeineEintraegeBeiLeeremSuchwort() {
        assertEquals(Optional.empty(), service.suchen("  "));
        verify(repository, org.mockito.Mockito.never()).findeNachWort("  ");
    }
}
