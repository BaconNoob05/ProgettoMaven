package it.unisa.diem.ingegneriadelsoftware;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;


import it.unisa.diem.ingegneriadelsoftware.model.*;

import it.unisa.diem.ingegneriadelsoftware.repository.*;

import it.unisa.diem.ingegneriadelsoftware.service.*;
import it.unisa.diem.ingegneriadelsoftware.view.*;

import it.unisa.diem.ingegneriadelsoftware.controller.*; 

/**
 * @class Main
 * @brief Classe principale che gestisce l'avvio dell'applicazione JavaFX
 * e l'inizializzazione dei componenti MVC.
 */
public class Main extends Application {

    private final String FILE_LIBRI = "libri.txt";
    private final String FILE_UTENTI = "utenti.txt";
    private final String FILE_PRESTITI = "prestiti.txt";


    private LibroService libroService;
    private UtenteService utenteService;
    private PrestitoService prestitoService;


    private Libro l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
    private Utente u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;


    @Override
    public void start(Stage primaryStage) {
        

        Repository<Libro> libroRepo = new Repository<>(FILE_LIBRI, new GestoreFile<>());
        Repository<Utente> utenteRepo = new Repository<>(FILE_UTENTI, new GestoreFile<>());
        Repository<Prestito> prestitoRepo = new Repository<>(FILE_PRESTITI, new GestoreFile<>());


        libroService = new LibroService(libroRepo);
        utenteService = new UtenteService(utenteRepo);
        prestitoService = new PrestitoService(prestitoRepo, libroService); 


        libroService.setPrestitoService(prestitoService);


        if (libroService.getAll().isEmpty() && utenteService.getAll().isEmpty()) {
            initializeSampleData();
        }



        LibroView libroView = new LibroView();
        UtenteView utenteView = new UtenteView();
        PrestitoView prestitoView = new PrestitoView();


        InterfaceController libroController = new LibroController(libroView, libroService);
        InterfaceController utenteController = new UtenteController(utenteView, utenteService);
        InterfaceController prestitoController = new PrestitoController(prestitoView, prestitoService);

        libroController.init();
        utenteController.init();
        prestitoController.init();

        prestitoView.setLibriList(libroService.getAll());
        prestitoView.setUtentiList(utenteService.getAll());


        TabPane tabPane = new TabPane();

        Tab libroTab = new Tab("Gestione Libri", libroView.getRoot());
        libroTab.setClosable(false);
        
        Tab utenteTab = new Tab("Gestione Utenti", utenteView.getRoot());
        utenteTab.setClosable(false);
        
        Tab prestitoTab = new Tab("Gestione Prestiti", prestitoView.getRoot());
        prestitoTab.setClosable(false);


        prestitoTab.setOnSelectionChanged(event -> {
            if (prestitoTab.isSelected()) {
                prestitoView.setLibriList(libroService.getAll());
                prestitoView.setUtentiList(utenteService.getAll());
                prestitoController.aggiornaVista(); 
            }
        });

        tabPane.getTabs().addAll(libroTab, utenteTab, prestitoTab);

        BorderPane root = new BorderPane(tabPane);
        Scene scene = new Scene(root, 1000, 500);
        
        primaryStage.setTitle("Sistema Gestione Biblioteca");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @brief Metodo privato per l'inizializzazione di dati di esempio.
     * Crea 10 libri, 10 utenti e 10 prestiti (attivi, scaduti e chiusi).
     */
    private void initializeSampleData() {
        System.out.println("Inizializzazione dati di esempio...");


        LocalDate oggi = LocalDate.now();
        LocalDate dataStoricaBase = oggi.minusMonths(1); 
        LocalDate dataStoricaLontana = oggi.minusMonths(5); 


        l1 = new Libro("Il Codice Da Vinci", Arrays.asList("Dan Brown"), 2003, "978-8804526211", 3);
        l2 = new Libro("Guerra e Pace", Arrays.asList("Lev Tolstoj"), 1869, "978-8806208882", 1);
        l3 = new Libro("Fisica per l'università", Arrays.asList("Halliday", "Resnick"), 2018, "978-8808182740", 5);
        l4 = new Libro("Un'estate italiana", Arrays.asList("Marco Rossi"), 2021, "978-8832903247", 2);
        l5 = new Libro("Clean Code", Arrays.asList("Robert C. Martin"), 2008, "978-0132350884", 4);
        l6 = new Libro("Design Patterns", Arrays.asList("Gamma", "Helm", "Johnson", "Vlissides"), 1994, "978-0201633610", 3);
        l7 = new Libro("The Lord of the Rings", Arrays.asList("J.R.R. Tolkien"), 1954, "978-0618574940", 6);
        l8 = new Libro("Java in a Nutshell", Arrays.asList("David Flanagan"), 2020, "978-1492076260", 3);
        l9 = new Libro("L'ombra del vento", Arrays.asList("Carlos Ruiz Zafón"), 2001, "978-8804510746", 2);
        l10 = new Libro("Artificial Intelligence", Arrays.asList("Stuart Russell", "Peter Norvig"), 2023, "978-0134610993", 4);
        
        libroService.salva(l1);
        libroService.salva(l2);
        libroService.salva(l3);
        libroService.salva(l4);
        libroService.salva(l5);
        libroService.salva(l6);
        libroService.salva(l7);
        libroService.salva(l8);
        libroService.salva(l9);
        libroService.salva(l10);
        

        u1 = new Utente("Mario", "Rossi", "M0001", "mario.rossi@studenti.unisa.it");
        u2 = new Utente("Anna", "Bianchi", "A0002", "anna.bianchi@studenti.unisa.it");
        u3 = new Utente("Luca", "Verdi", "L0003", "luca.verdi@studenti.unisa.it");
        u4 = new Utente("Sara", "Neri", "S0004", "sara.neri@studenti.unisa.it");
        u5 = new Utente("Paolo", "Gialli", "P0005", "paolo.gialli@studenti.unisa.it");
        u6 = new Utente("Giulia", "Bruni", "G0006", "giulia.bruni@studenti.unisa.it");
        u7 = new Utente("Davide", "Moretti", "D0007", "davide.moretti@studenti.unisa.it");
        u8 = new Utente("Elena", "Rizzo", "E0008", "elena.rizzo@studenti.unisa.it");
        u9 = new Utente("Andrea", "Ferrari", "A0009", "andrea.ferrari@studenti.unisa.it");
        u10 = new Utente("Chiara", "Gallo", "C0010", "chiara.gallo@studenti.unisa.it");
        
        utenteService.salva(u1);
        utenteService.salva(u2);
        utenteService.salva(u3);
        utenteService.salva(u4);
        utenteService.salva(u5);
        utenteService.salva(u6);
        utenteService.salva(u7);
        utenteService.salva(u8);
        utenteService.salva(u9);
        utenteService.salva(u10);


        l1.decrementaCopie(); 
        libroService.modifica(l1); 
        Prestito p1Attivo = new Prestito(u1, l1, oggi.plusDays(7), dataStoricaBase.plusDays(1)); 
        prestitoService.salva(p1Attivo);
        

        l3.decrementaCopie(); 
        libroService.modifica(l3);
        Prestito p3Attivo = new Prestito(u3, l3, oggi.plusDays(14), dataStoricaBase.plusDays(5)); 
        prestitoService.salva(p3Attivo);
        

        l6.decrementaCopie(); 
        libroService.modifica(l6);
        Prestito p6Attivo = new Prestito(u6, l6, oggi.plusDays(10), dataStoricaBase.plusDays(10)); 
        prestitoService.salva(p6Attivo);
        

        l7.decrementaCopie(); 
        libroService.modifica(l7);
        Prestito p7Attivo = new Prestito(u7, l7, oggi.plusDays(5), dataStoricaBase.plusDays(15)); 
        prestitoService.salva(p7Attivo);
        

        l7.decrementaCopie(); 
        libroService.modifica(l7);
        Prestito p10Attivo = new Prestito(u10, l7, oggi.plusDays(20), dataStoricaBase.plusDays(20));
        prestitoService.salva(p10Attivo);
        

        l2.decrementaCopie(); 
        libroService.modifica(l2); 
        Prestito p2Scaduto = new Prestito(u2, l2, dataStoricaLontana.plusDays(10), dataStoricaLontana); 
        prestitoService.salva(p2Scaduto); 
        

        l8.decrementaCopie(); 
        libroService.modifica(l8); 
        Prestito p8Scaduto = new Prestito(u8, l8, dataStoricaLontana.plusDays(2), dataStoricaLontana.minusDays(5)); 
        prestitoService.salva(p8Scaduto); 

        LocalDate dataPrestitoP4 = oggi.minusMonths(4);
        Prestito p4Chiuso = new Prestito(u4, l4, dataPrestitoP4.plusDays(5), dataPrestitoP4); 
        p4Chiuso.registraRestituzione(dataPrestitoP4.plusDays(4)); 
        prestitoService.salva(p4Chiuso); 
        

        LocalDate dataPrestitoP5 = oggi.minusMonths(3); 
        Prestito p5Chiuso = new Prestito(u5, l5, dataPrestitoP5.plusDays(5), dataPrestitoP5); 
        p5Chiuso.registraRestituzione(dataPrestitoP5.plusDays(10)); 
        prestitoService.salva(p5Chiuso); 


        LocalDate dataPrestitoP9 = oggi.minusDays(1);
        Prestito p9Chiuso = new Prestito(u9, l9, oggi.plusDays(1), dataPrestitoP9); 
        p9Chiuso.registraRestituzione(oggi); 
        prestitoService.salva(p9Chiuso); 
        

        
        System.out.println("Salvataggio finale di tutti gli stati...");
        

        for (Libro l : libroService.getAll()) {
            libroService.modifica(l);
        }
        for (Utente u : utenteService.getAll()) {
            utenteService.modifica(u);
        }
        for (Prestito p : prestitoService.getAll()) {
            prestitoService.modifica(p);
        }

        System.out.println("Dati di esempio caricati e salvati con successo.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}