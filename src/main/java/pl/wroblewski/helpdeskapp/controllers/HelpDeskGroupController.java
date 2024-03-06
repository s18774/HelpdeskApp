package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.services.HelpDeskGroupService;

@RestController
@RequestMapping("/api/v1/helpDeskGroup")
@CrossOrigin("http://localhost:3000")
public class HelpDeskGroupController extends BaseController {

    private final HelpDeskGroupService helpDeskGroupService;

    public HelpDeskGroupController(HelpDeskGroupService helpDeskGroupService) {
        this.helpDeskGroupService = helpDeskGroupService;
    }
}
