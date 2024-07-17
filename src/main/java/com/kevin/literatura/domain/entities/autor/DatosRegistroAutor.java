package com.kevin.literatura.domain.entities.autor;

import com.kevin.literatura.domain.entities.libro.DataBook;

import java.util.List;
import java.util.Optional;

public record DatosRegistroAutor(
        String name,
        String fechaNacimiento,
        String fechaFallecimiento
) {
    public DatosRegistroAutor(List<DataAuthor> autor) {
        this(
            autor.getFirst().nombre(),
            autor.getFirst().fecha_nacimiento(),
            autor.getFirst().fecha_fallecimiento()
        );
    }
}
