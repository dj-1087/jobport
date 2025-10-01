package dev.mju.jobport.domain.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class SampleController {
    @GetMapping(value = "/{path}.html", produces = MediaType.TEXT_HTML_VALUE)
    public String proxyHtml(@PathVariable String path) {

        log.info("path={}", path.split("/")[path.split("/").length - 1]);
        return "samples/" + path.split("/")[path.split("/").length - 1] + ".html";
    }
}
