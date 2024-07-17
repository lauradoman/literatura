package com.kevin.literatura.domain.entities.libro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kevin.literatura.domain.entities.autor.DataAuthor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors")List<DataAuthor> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Integer descargas
        ) {
}
