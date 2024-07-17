package com.kevin.literatura.domain.entities.autor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kevin.literatura.domain.entities.libro.Libro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String fechaNacimiento;
    private String fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Libro> libros;

    public Autor(DataAuthor dataAuthor) {
        this.name = dataAuthor.nombre();
        this.fechaNacimiento = dataAuthor.fecha_nacimiento();
        this.fechaFallecimiento = dataAuthor.fecha_fallecimiento();
        this.libros = new ArrayList<>();
    }

    public Autor() {
    }

    public Autor(DatosRegistroAutor datosAutor) {
        this.name = datosAutor.name();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }
}
