package com.kevin.literatura.domain.entities.libro;

public record DatosLibroDetalle(
        String titulo,
        String autor,
        String idioma,
        Integer descargas
) {
    public DatosLibroDetalle (DataBook dataBook){
        this(
                dataBook.titulo(),
                dataBook.autor().getFirst().nombre(),
                dataBook.idioma().getFirst().split(",")[0],
                dataBook.descargas()
        );
    }

    public DatosLibroDetalle(Libro libro) {
        this(libro.getTitulo(),libro.getAutor().getName(), libro.getIdioma(), libro.getDescargas());
    }
}
