package uk.doh.oht.frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.doh.oht.frontend.domain.CaseDefinition;
import uk.doh.oht.frontend.service.RetrieveRinaDataService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Controller
@Slf4j
@RequestMapping("/view")
public class ViewController {
    private final RetrieveRinaDataService retrieveRinaDataService;

    @Inject
    public ViewController(final RetrieveRinaDataService retrieveRinaDataService) {
        this.retrieveRinaDataService = retrieveRinaDataService;
    }

    @GetMapping
    public ModelAndView getAllCases() {
        final List<CaseDefinition> allCases = retrieveRinaDataService.getAllCases();
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("allCases", allCases);
        return modelAndView;
    }

    @GetMapping(value = "case/{caseId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCase(@PathVariable("caseId") final String caseId,
                          final Model model) {
        model.addAttribute("case", retrieveRinaDataService.getCase(caseId));
        return "caseResult :: resultsList";
    }

    @GetMapping(value = "case/{caseId}/{documentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getDocument(@PathVariable("caseId") final String caseId,
                              @PathVariable("documentId") final String documentId,
                              final Model model) {
        model.addAttribute("document", retrieveRinaDataService.getDocument(caseId, documentId));
        return "documentResult :: resultsList";
    }
}
