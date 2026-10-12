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
        when(repository.findeNachWort("hallo")).thenReturn(Optional.empty());
        when(repository.speichern(eintrag)).thenReturn(new WortEintrag(1L, "Hallo", "Eine Begrüßung"));

        WortEintrag gespeichert = service.hinzufuegen(" Hallo ", " Eine Begrüßung ");

        assertEquals(1L, gespeichert.id());
        verify(repository).speichern(eintrag);
    }

    @Test
    void verhindertDoppelteEintraege() {
        when(repository.findeNachWort("hallo")).thenReturn(Optional.of(
                new WortEintrag(1L, "Hallo", "Eine Begrüßung")));

        assertThrows(EintragExistiertBereitsException.class,
                () -> service.hinzufuegen("HALLO", "Noch eine Begrüßung"));
    }

    @Test
    void aktualisiertEintragOhneDoppelteWorterfassung() {
        WortEintrag vorhandenerEintrag = new WortEintrag(1L, "Hallo", "Eine Begrüßung");
        WortEintrag aktualisierterEintrag = new WortEintrag(1L, "Hallo", "Neue Begrüßung");

        when(repository.findeNachId(1L)).thenReturn(Optional.of(vorhandenerEintrag));
        when(repository.findeNachWort("hallo")).thenReturn(Optional.of(vorhandenerEintrag));
        when(repository.speichern(aktualisierterEintrag)).thenReturn(aktualisierterEintrag);

        assertEquals(aktualisierterEintrag, service.aktualisieren(1L, " Hallo ", " Neue Begrüßung "));
        verify(repository).speichern(aktualisierterEintrag);
    }

    @Test
    void findetKeineEintraegeBeiLeeremSuchwort() {
        assertEquals(Optional.empty(), service.suchen("  "));
        verify(repository, org.mockito.Mockito.never()).findeNachWort("  ");
    }

    @Test
    void normalisiertSuchwortVorDemRepositoryAufruf() {
        when(repository.findeNachWort("hallo")).thenReturn(Optional.of(
                new WortEintrag(1L, "Hallo", "Eine Begrüßung")));

        assertEquals(Optional.of(new WortEintrag(1L, "Hallo", "Eine Begrüßung")),
                service.suchen(" HALLO "));
        verify(repository).findeNachWort("hallo");
    }
}
