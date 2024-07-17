package com.kevin.literatura.domain.entities.autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fecha_nacimiento,
        @JsonAlias("death_year") String fecha_fallecimiento
) {
}
