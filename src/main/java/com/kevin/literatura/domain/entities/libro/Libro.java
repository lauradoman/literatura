package com.kevin.literatura.domain.entities.libro;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kevin.literatura.domain.entities.autor.Autor;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String titulo;
    private String idioma;
    private Integer descargas;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    @JsonBackReference
    private Autor autor;

    public Libro() {
    }

    public Libro(DataBook dataBook) {
        this.titulo = dataBook.titulo();
        this.autor = new Autor(dataBook.autor().getFirst());
        this.idioma = dataBook.idioma().getFirst();
        this.descargas = dataBook.descargas();
    }

    public Libro(DatosLibroDetalle datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idioma();
        this.descargas = datosLibro.descargas();
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public Autor getAutor() {
        return autor;
    }
}
