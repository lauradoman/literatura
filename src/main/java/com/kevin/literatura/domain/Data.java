package com.kevin.literatura.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kevin.literatura.domain.entities.libro.DataBook;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
        @JsonAlias("results")
        List<DataBook> bookList
) {
}
