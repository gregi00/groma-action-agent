package de.groma.wortsuche.infrastructure.persistence;

import de.groma.wortsuche.application.WortEintragRepository;
import de.groma.wortsuche.application.WortEintragService;
import de.groma.wortsuche.domain.WortEintrag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WortEintragRepositoryAdapter implements WortEintragRepository {
    private final SpringDataWortEintragRepository repository;

    public WortEintragRepositoryAdapter(SpringDataWortEintragRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WortEintrag> findeAlle() {
        return repository.findAllByOrderByWortNormalisiertAsc().stream()
                .map(WortEintragRepositoryAdapter::abbilden)
                .toList();
    }

    @Override
    public Optional<WortEintrag> findeNachWort(String wort) {
        return repository.findByWortNormalisiert(WortEintragService.normalisiere(wort))
                .map(WortEintragRepositoryAdapter::abbilden);
    }

    @Override
    public Optional<WortEintrag> findeNachId(Long id) {
        return repository.findById(id).map(WortEintragRepositoryAdapter::abbilden);
    }

    @Override
    public WortEintrag speichern(WortEintrag eintrag) {
        WortEintragEntity entity = new WortEintragEntity(
                eintrag.id(),
                eintrag.wort(),
                WortEintragService.normalisiere(eintrag.wort()),
                eintrag.text());
        return abbilden(repository.save(entity));
    }

    @Override
    public void loeschen(Long id) {
        repository.deleteById(id);
    }

    private static WortEintrag abbilden(WortEintragEntity entity) {
        return new WortEintrag(entity.getId(), entity.getWort(), entity.getText());
    }
}
