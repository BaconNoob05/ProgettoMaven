package it.unisa.diem.ingegneriadelsoftware.controller;
import it.unisa.diem.ingegneriadelsoftware.service.*;


import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibroControllerTest {

  private LibroController controller;
  private LibroService service;
  private LibroViewStub view;
  private RepositoryStub<Libro> repository;

    @BeforeEach
    void setup() {
        repo = new RepositoryStub<>();
        service = new LibroService(repository);
        view = new LibroViewStub();
        controller = new LibroController(view, service);
    }
}



    
