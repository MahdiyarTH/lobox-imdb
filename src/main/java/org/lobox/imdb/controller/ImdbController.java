package org.lobox.imdb.controller;

import lombok.RequiredArgsConstructor;
import org.lobox.common.http.PaginatedResponse;
import org.lobox.common.util.HttpUtil;
import org.lobox.imdb.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/imdb")
public class ImdbController {

    private final ProductService productService;

    @GetMapping("same-write-director")
    public PaginatedResponse<String> getProductsTitleWithSameDirectorAndWriter(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return new PaginatedResponse<>(
                productService.findAllSameWriteAndDirectorAlive(
                        HttpUtil.getPage(page),
                        HttpUtil.getSize(size)
                )
        );
    }

    @GetMapping("same-title-for-two-actors")
    public PaginatedResponse<String> getProductsTitleWithSameDirectorAndWriter(
            @RequestParam Long firstActorId,
            @RequestParam Long secondActorId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return new PaginatedResponse<>(
                productService.findAllTitlesWithTwoActors(
                        firstActorId,
                        secondActorId,
                        HttpUtil.getPage(page),
                        HttpUtil.getSize(size)
                )
        );
    }


}
