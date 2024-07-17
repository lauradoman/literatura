package com.kevin.literatura.domain.entities.autor;

import java.util.List;

public record DatosListarAutor(
        String name,
        String fechaNacimiento,
        String fechaFallecimiento,
        List<String> libros
) {
}
