package dev.mju.jobport.modules.site.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/")
    public String index() {
        return "pages/common/index";
    }
}
