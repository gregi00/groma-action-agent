package de.groma.wortsuche.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wort_eintrag", uniqueConstraints = {
        @UniqueConstraint(name = "uk_wort_eintrag_normalisiert", columnNames = "wort_normalisiert")
})
public class WortEintragEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wort;

    @Column(name = "wort_normalisiert", nullable = false)
    private String wortNormalisiert;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    protected WortEintragEntity() {
    }

    public WortEintragEntity(Long id, String wort, String wortNormalisiert, String text) {
        this.id = id;
        this.wort = wort;
        this.wortNormalisiert = wortNormalisiert;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getWort() {
        return wort;
    }

    public String getWortNormalisiert() {
        return wortNormalisiert;
    }

    public String getText() {
        return text;
    }
}
