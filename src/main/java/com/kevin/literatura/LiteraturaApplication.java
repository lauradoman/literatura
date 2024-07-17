package com.kevin.literatura;

import com.kevin.literatura.domain.entities.autor.AutorRepository;
import com.kevin.literatura.domain.entities.libro.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {


	private final AutorRepository autorRepository;
	private final LibroRepository libroRepository;

	public LiteraturaApplication(AutorRepository autorRepository, LibroRepository libroRepository) {
		this.autorRepository = autorRepository;
		this.libroRepository = libroRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(autorRepository,libroRepository);


		main.MostrarMenu();


	}
}
