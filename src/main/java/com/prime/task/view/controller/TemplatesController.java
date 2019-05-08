package com.prime.task.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TemplatesController {

    @RequestMapping(value = "/templates/forms", method = RequestMethod.GET)
    String renderForms() {
        return "forms";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String home() {
        return "index";
    }

}
