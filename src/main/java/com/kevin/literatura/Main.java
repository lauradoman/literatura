package com.kevin.literatura;

import com.kevin.literatura.domain.entities.autor.Autor;
import com.kevin.literatura.domain.entities.autor.AutorRepository;
import com.kevin.literatura.domain.entities.autor.DatosListarAutor;
import com.kevin.literatura.domain.entities.autor.DatosRegistroAutor;
import com.kevin.literatura.domain.entities.libro.DatosLibroDetalle;
import com.kevin.literatura.domain.entities.libro.LibroRepository;
import com.kevin.literatura.domain.helpers.ConvertirDatos;
import com.kevin.literatura.domain.entities.libro.Libro;
import com.kevin.literatura.domain.entities.libro.DataBook;
import com.kevin.literatura.domain.Data;
import com.kevin.literatura.domain.services.ConsultaApi;
import jakarta.transaction.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class Main {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsultaApi consultaApi = new ConsultaApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvertirDatos convertirDatos = new ConvertirDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Main(AutorRepository autorRepository,LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void MostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {


            String menu = """
                    \n
                    \n
                    ########################################
                    ################  MENU  ################
                    ########################################
                                       \s
                    #########  INGRESE UNA OPCION  #########
                                       \s
                                       \s
                    1 -> Buscar Libro
                    2 -> Listar Libros Registrados
                    3 -> Listar Autores Registrados
                    4 -> Listar Autores Vivos En Un Determinado Año
                    5 -> Listar Libros por idioma
                    6 -> Ver estadisticas sobre la descarga de los libros registrados
                                       \s
                    0 -> Cerrar el programa
                   \s""";

            System.out.println(menu);

            try{
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 0:
                        System.out.println("... Cerrando la aplicacion");
                        break;
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnUnAnioDetermiando();
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        estadisticas();
                        break;
                    default:
                        System.out.println("Error opcion invalida, escoja una opcion valida");
                        break;
                }
            }catch (Exception e){
                System.out.println(e);
                System.out.println("Error: Opcion invalida, escoja una opcion valida");
                teclado.nextLine();
            }
        }
    }



    private void getDatos() {
        var json = consultaApi.requestData(URL_BASE);
        System.out.println(json);
    }


    @Transactional
    private void buscarLibro() throws Exception {

        System.out.println("""
                           Ingrese el nombre del libro
                           """);
        String libro = teclado.nextLine();

        if(libro == null){
            throw new Exception("el libro no puede ser nulo");
        }

        var libroParam = URLEncoder.encode(libro, StandardCharsets.UTF_8);

        var json = consultaApi.requestData(URL_BASE + "?search=" + libroParam);

        Data data = convertirDatos.obtenerDatos(json, Data.class);

        Optional<DataBook> dataBook = data.bookList().stream().findFirst();

        if (dataBook.isPresent()) {
            var datosLibroDetalle = dataBook.map(DatosLibroDetalle::new);
            DatosLibroDetalle datosLibro = datosLibroDetalle.get();

            System.out.println(
                    "\n" +
                            "*********** RESULTS ************ \n" +
                            "***********   FOR   ************ \n" +
                            "***********  " + libroParam + "  ************\n" +
                            "\n" +
                            "\n" +
                            "Titulo    : " + datosLibro.titulo() + "\n" +
                            "Autor     : " + datosLibro.autor() + "\n" +
                            "Idioma    : " + datosLibro.idioma() + "\n" +
                            "Descargas : " + datosLibro.descargas()
            );




            if (!libroRepository.existsByTitulo(datosLibro.titulo())) {
                Autor autor;
                if(!autorRepository.existsByName(datosLibro.autor())){
                    var datosAutor = new DatosRegistroAutor(dataBook.get().autor());
                    autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                }else{
                    autor = autorRepository.getByName(datosLibro.autor());
                }
                libroRepository.save(new Libro(datosLibro, autor));
            } else {
                System.out.println(
                        "\n\n************************************************************\n" +
                                "***********  " + libroParam + " ya esta registrado en la DB  ************ \n" +
                                "************************************************************\n"
                );
            }
        } else {
            System.out.println(
                    "\n\n************************************************************************************\n" +
                            "***********  no se encontro ningun libro con el nombre de :" + libroParam + "  ************ \n" +
                            "************************************************************************************\n"
            );
        }
    }

    public void listarLibrosRegistrados() {
        List<DatosLibroDetalle> libros = libroRepository.findAll().stream().map(DatosLibroDetalle::new).collect(Collectors.toList());

        System.out.println(
                """

                        ********************************************************\s
                        ***********   LISTA DE LIBROS REGISTRADOS   ************\s
                        *********************************************************

                        """
        );

        libros.forEach(l ->
                System.out.println(
                        "\n" +
                                "------- LIBRO -------" +
                                "\n" +
                                "Titulo    : " + l.titulo() + "\n" +
                                "Autor     : " + l.autor() + "\n" +
                                "Idioma    : " + l.idioma() + "\n" +
                                "Descargas : " + l.descargas()
                )
        );

    }

    public void listarAutoresRegistrados(){

        List<DatosListarAutor> autores = autorRepository.findAll().stream().map(a -> {
            return new DatosListarAutor(
                    a.getName(),
                    a.getFechaNacimiento(),
                    a.getFechaFallecimiento(),
                    libroRepository.getAllLibrosByAutor(a.getId())
            );
        }).collect(Collectors.toList());

        autores.forEach(a ->
                System.out.println(
                        "\n" +
                                "------- AUTOR -------" +
                                "\n" +
                                "Autor                  : " + a.name() + "\n" +
                                "Fecha de Nacimiento    : " + a.fechaNacimiento() + "\n" +
                                "Fecha de Fallecimiento : " + a.fechaFallecimiento() + "\n" +
                                "Libros                 : " + a.libros()
                )
        );
    }

    public void listarAutoresVivosEnUnAnioDetermiando(){

        System.out.println("Ingrese el año para determinar los actores que estuvieron vivos");
        var year = teclado.nextInt();
        teclado.nextLine();

        List<DatosListarAutor> autores = autorRepository.findAllAutoresAliveInYear(year)
                .stream()
                .map(a -> {
                    return new DatosListarAutor(
                            a.getName(),
                            a.getFechaNacimiento(),
                            a.getFechaFallecimiento(),
                            libroRepository.getAllLibrosByAutor(a.getId())
                    );
                }).collect(Collectors.toList());

        System.out.printf("""
                ##########################################
                #####  AUTORES VIVOS EN EL AÑO %d  #####
                ##########################################
                """, year);

        autores.forEach(a ->
                System.out.println(
                        "\n" +
                                "------- AUTOR -------" +
                                "\n" +
                                "Autor                  : " + a.name() + "\n" +
                                "Fecha de Nacimiento    : " + a.fechaNacimiento() + "\n" +
                                "Fecha de Fallecimiento : " + a.fechaFallecimiento() + "\n" +
                                "Libros                 : " + a.libros()
                )
        );
    }

    private void listarLibrosPorIdioma() {
        System.out.println(
                """
                    Seleccione el idioma de los libros a buscar

                    es -> español
                    en -> ingles
                    pt -> portugues
                    fr -> franses

                """
        );
        var idioma = teclado.nextLine();

        var libros = libroRepository.findAllByIdioma(idioma).stream()
                .map(l -> {
                    return new DatosLibroDetalle(
                            l.getTitulo(),
                            l.getAutor().getName(),
                            l.getIdioma(),
                            l.getDescargas()
                    );
                }).collect(Collectors.toList());

        if (!libros.isEmpty()){

            libros.forEach(l ->
                    System.out.println(
                            "\n" +
                                    "------- LIBRO -------" +
                                    "\n" +
                                    "Titulo    : " + l.titulo() + "\n" +
                                    "Autor     : " + l.autor() + "\n" +
                                    "Idioma    : " + l.idioma() + "\n" +
                                    "Descargas : " + l.descargas()
                    )
            );
        }else {
            System.out.printf(
                    """
                    ---------------------------------------------------------------
                    No se encontro ningun libro del idioma "%s" registrado en la BD
                    ---------------------------------------------------------------
                    """, idioma);
        }
    }

    private void estadisticas(){
        System.out.println("""
                ##########################################
                ############  ESTADISTICAS  ##############
                ##########################################
                """);

        List<DatosLibroDetalle> libros = libroRepository.findAll().stream().map(DatosLibroDetalle::new).collect(Collectors.toList());

        try {
            DoubleSummaryStatistics est = libros.stream()
                    .filter(l -> l.descargas() > 0)
                    .collect(Collectors.summarizingDouble(DatosLibroDetalle::descargas));

            DecimalFormat df = new DecimalFormat("#");

            System.out.println(
                    "\n" +
                            "------- ESTADISTICAS DE DESCARGAS -------" +
                            "\n" +
                            "\n" +
                            "Num de Registros : " + est.getCount() + "\n" +
                            "Total general    : " + df.format(est.getSum()) + "\n" +
                            "Min              : " + df.format(est.getMin()) + "\n" +
                            "Max              : " + df.format(est.getMax()) + "\n" +
                            "AVG              : " + df.format(est.getAverage())
            );
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }
}
