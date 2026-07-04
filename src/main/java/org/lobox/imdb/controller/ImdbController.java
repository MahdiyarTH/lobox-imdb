package org.lobox.imdb.controller;

import lombok.RequiredArgsConstructor;
import org.lobox.common.data.FileDataReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/imdb")
public class ImdbController {

    private final FileDataReader fileDataReader;

    @GetMapping
    public void test() throws IOException {
        fileDataReader.init();
    }


}
