package de.groma.wortsuche.web;

import de.groma.wortsuche.application.EintragExistiertBereitsException;
import de.groma.wortsuche.application.EintragNichtGefundenException;
import de.groma.wortsuche.application.WortEintragService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WortEintragController {
    private final WortEintragService service;

    public WortEintragController(WortEintragService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String startseite(@RequestParam(name = "wort", required = false) String wort, Model model) {
        model.addAttribute("wort", wort == null ? "" : wort);
        model.addAttribute("eintraege", service.alleFinden());
        model.addAttribute("sucheAktiv", wort != null && !wort.isBlank());
        model.addAttribute("suchergebnis", wort == null || wort.isBlank() ? null : service.suchen(wort).orElse(null));
        model.addAttribute("zuBearbeitenderEintrag", null);
        return "index";
    }

    @GetMapping("/eintraege/{id}/bearbeiten")
    public String bearbeiten(@PathVariable("id") Long id, Model model) {
        model.addAttribute("wort", "");
        model.addAttribute("eintraege", service.alleFinden());
        model.addAttribute("sucheAktiv", false);
        model.addAttribute("suchergebnis", null);
        model.addAttribute("zuBearbeitenderEintrag", service.findeNachId(id).orElse(null));
        return "index";
    }

    @PostMapping("/eintraege")
    public String hinzufuegen(@RequestParam("wort") String wort,
                              @RequestParam("text") String text,
                              RedirectAttributes redirectAttributes) {
        try {
            service.hinzufuegen(wort, text);
            redirectAttributes.addFlashAttribute("erfolg", "Der Eintrag wurde gespeichert.");
        } catch (IllegalArgumentException | EintragExistiertBereitsException exception) {
            redirectAttributes.addFlashAttribute("fehler", exception.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/eintraege/{id}/bearbeiten")
    public String aktualisieren(@PathVariable("id") Long id,
                               @RequestParam("wort") String wort,
                               @RequestParam("text") String text,
                               RedirectAttributes redirectAttributes) {
        try {
            service.aktualisieren(id, wort, text);
            redirectAttributes.addFlashAttribute("erfolg", "Der Eintrag wurde bearbeitet.");
        } catch (IllegalArgumentException | EintragExistiertBereitsException | EintragNichtGefundenException exception) {
            redirectAttributes.addFlashAttribute("fehler", exception.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping(value = {"/eintraege/loeschen", "/eintraege/{id}/loeschen"})
    public String loeschen(@PathVariable(name = "id", required = false) Long pathId,
                           @RequestParam(name = "id", required = false) Long requestId,
                           RedirectAttributes redirectAttributes) {
        Long id = pathId != null ? pathId : requestId;
        try {
            service.loeschen(id);
            redirectAttributes.addFlashAttribute("erfolg", "Der Eintrag wurde gelöscht.");
        } catch (EintragNichtGefundenException exception) {
            redirectAttributes.addFlashAttribute("fehler", exception.getMessage());
        }
        return "redirect:/";
    }
}
