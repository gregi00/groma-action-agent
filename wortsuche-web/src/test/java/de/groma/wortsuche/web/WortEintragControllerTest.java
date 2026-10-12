package de.groma.wortsuche.web;

import de.groma.wortsuche.WortsucheApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        String id = seite.replaceAll("(?s).*name=\"id\" value=\"(\\d+)\".*", "$1");

        mockMvc.perform(post("/eintraege/loeschen").param("id", id))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/").param("wort", "Zuversicht"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Für dieses Wort ist noch kein Text gespeichert.")));
    }
}
