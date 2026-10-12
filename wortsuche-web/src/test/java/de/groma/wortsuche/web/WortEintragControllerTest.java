package de.groma.wortsuche.web;

import de.groma.wortsuche.WortsucheApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = WortsucheApplication.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:wortsuche;DB_CLOSE_DELAY=-1",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        })
@AutoConfigureMockMvc
class WortEintragControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void kannEintragSpeichernSuchenUndLoeschen() throws Exception {
        mockMvc.perform(post("/eintraege")
                        .param("wort", "Zuversicht")
                        .param("text", "Vertrauen in eine gute Zukunft."))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/").param("wort", "ZUversicht"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Vertrauen in eine gute Zukunft.")));

        String seite = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String id = seite.replaceAll("(?s).*action=\"/eintraege/(\\d+)/loeschen\".*", "$1");

        mockMvc.perform(post("/eintraege/{id}/loeschen", id))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/").param("wort", "Zuversicht"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Für dieses Wort ist noch kein Text gespeichert.")));
    }

    @Test
    void verhindertDoppelteEintraegeUnabhaengigVonGrossschreibung() throws Exception {
        String wort = "Playwright-" + UUID.randomUUID();

        mockMvc.perform(post("/eintraege")
                        .param("wort", wort)
                        .param("text", "Erster Text"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/eintraege")
                        .param("wort", wort.toUpperCase(Locale.ROOT))
                        .param("text", "Zweiter Text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("fehler",
                        "Für „" + wort.toUpperCase(Locale.ROOT) + "“ gibt es bereits einen Eintrag."));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Erster Text")))
                .andExpect(content().string(org.hamcrest.Matchers.not(containsString("Zweiter Text"))));
    }

    @Test
    void kannEintragBearbeiten() throws Exception {
        mockMvc.perform(post("/eintraege")
                        .param("wort", "Mut")
                        .param("text", "Alte Beschreibung"))
                .andExpect(status().is3xxRedirection());

        String seite = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String id = seite.replaceAll("(?s).*action=\"/eintraege/(\\d+)/bearbeiten\".*", "$1");

        mockMvc.perform(get("/eintraege/{id}/bearbeiten", id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Änderungen speichern")));

        mockMvc.perform(post("/eintraege/{id}/bearbeiten", id)
                        .param("wort", "Mut")
                        .param("text", "Neue Beschreibung"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("erfolg", "Der Eintrag wurde bearbeitet."));

        mockMvc.perform(get("/").param("wort", "Mut"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Neue Beschreibung")))
                .andExpect(content().string(org.hamcrest.Matchers.not(containsString("Alte Beschreibung"))));
    }
}
