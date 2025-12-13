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

// Import relativi ai Model
import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;

// Import relativi ai Repository
import it.unisa.diem.ingegneriadelsoftware.repository.GestoreFile;
import it.unisa.diem.ingegneriadelsoftware.repository.Repository;

// Import relativi ai Service
import it.unisa.diem.ingegneriadelsoftware.service.LibroService;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteService;
import it.unisa.diem.ingegneriadelsoftware.service.PrestitoService;

// Import relativi alle View
import it.unisa.diem.ingegneriadelsoftware.view.LibroView;
import it.unisa.diem.ingegneriadelsoftware.view.UtenteView;
import it.unisa.diem.ingegneriadelsoftware.view.PrestitoView;

// Import relativi ai Controller
import it.unisa.diem.ingegneriadelsoftware.controller.LibroController;
import it.unisa.diem.ingegneriadelsoftware.controller.UtenteController;
import it.unisa.diem.ingegneriadelsoftware.controller.PrestitoController;
import it.unisa.diem.ingegneriadelsoftware.controller.InterfaceController; 

/**
 * @class Main
 * @brief Classe principale che gestisce l'avvio dell'applicazione JavaFX
 * e l'inizializzazione dei componenti MVC.
 */
public class Main extends Application {

    private final String FILE_LIBRI = "libri.txt";
    private final String FILE_UTENTI = "utenti.txt";
    private final String FILE_PRESTITI = "prestiti.txt";

    // Dichiarazione dei Service
    private LibroService libroService;
    private UtenteService utenteService;
    private PrestitoService prestitoService;

    // Dichiarazione dei Model di esempio per i prestiti (Esteso a 10)
    private Libro l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
    private Utente u1, u2, u3, u4, u5, u6, u7, u8, u9, u10;


    @Override
    public void start(Stage primaryStage) {
        
        // 1. Inizializzazione Repository e Gestori IO
        Repository<Libro> libroRepo = new Repository<>(FILE_LIBRI, new GestoreFile<>());
        Repository<Utente> utenteRepo = new Repository<>(FILE_UTENTI, new GestoreFile<>());
        Repository<Prestito> prestitoRepo = new Repository<>(FILE_PRESTITI, new GestoreFile<>());

        // 2. Inizializzazione Service
        libroService = new LibroService(libroRepo);
        utenteService = new UtenteService(utenteRepo);
        prestitoService = new PrestitoService(prestitoRepo, libroService); 

        // Iniezione circolare: LibroService ha bisogno di PrestitoService per l'eliminazione (IF-1.1.2)
        libroService.setPrestitoService(prestitoService);

        // ** AGGIUNTA LOGICA PER DATI ESEMPIO **
        // Se i repository sono vuoti, aggiunge i dati di esempio.
        if (libroService.getAll().isEmpty() && utenteService.getAll().isEmpty()) {
            initializeSampleData();
        }


        // 3. Inizializzazione View
        LibroView libroView = new LibroView();
        UtenteView utenteView = new UtenteView();
        PrestitoView prestitoView = new PrestitoView();

        // 4. Inizializzazione Controller
        InterfaceController libroController = new LibroController(libroView, libroService);
        InterfaceController utenteController = new UtenteController(utenteView, utenteService);
        InterfaceController prestitoController = new PrestitoController(prestitoView, prestitoService);
        
        // Avvio dei controller per popolare le tabelle con i dati iniziali
        libroController.init();
        utenteController.init();
        prestitoController.init();

        // Inizializza i dati delle ComboBox di PrestitoView
        prestitoView.setLibriList(libroService.getAll());
        prestitoView.setUtentiList(utenteService.getAll());

        // 5. Configurazione delle Tab
        TabPane tabPane = new TabPane();

        Tab libroTab = new Tab("Gestione Libri", libroView.getRoot());
        libroTab.setClosable(false);
        
        Tab utenteTab = new Tab("Gestione Utenti", utenteView.getRoot());
        utenteTab.setClosable(false);
        
        Tab prestitoTab = new Tab("Gestione Prestiti", prestitoView.getRoot());
        prestitoTab.setClosable(false);

        // Aggiunge la logica per aggiornare le ComboBox quando si passa alla tab Prestiti
        prestitoTab.setOnSelectionChanged(event -> {
            if (prestitoTab.isSelected()) {
                prestitoView.setLibriList(libroService.getAll());
                prestitoView.setUtentiList(utenteService.getAll());
                prestitoController.aggiornaVista(); 
            }
        });

        tabPane.getTabs().addAll(libroTab, utenteTab, prestitoTab);

        // 6. Configurazione della Stage
        BorderPane root = new BorderPane(tabPane);
        Scene scene = new Scene(root, 1000, 450);
        
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

        // 1. Creazione e salvataggio 10 LIBRI
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
        
        // 2. Creazione e salvataggio 10 UTENTI
        u1 = new Utente("Mario", "Rossi", "M0001", "mario.rossi@example.com");
        u2 = new Utente("Anna", "Bianchi", "A0002", "anna.bianchi@example.com");
        u3 = new Utente("Luca", "Verdi", "L0003", "luca.verdi@example.com");
        u4 = new Utente("Sara", "Neri", "S0004", "sara.neri@example.com");
        u5 = new Utente("Paolo", "Gialli", "P0005", "paolo.gialli@example.com");
        u6 = new Utente("Giulia", "Bruni", "G0006", "giulia.bruni@example.com");
        u7 = new Utente("Davide", "Moretti", "D0007", "davide.moretti@example.com");
        u8 = new Utente("Elena", "Rizzo", "E0008", "elena.rizzo@example.com");
        u9 = new Utente("Andrea", "Ferrari", "A0009", "andrea.ferrari@example.com");
        u10 = new Utente("Chiara", "Gallo", "C0010", "chiara.gallo@example.com");
        
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

        // 3. Creazione e salvataggio 10 PRESTITI
        LocalDate oggi = LocalDate.now();
        
        // P1: Attivo, non scaduto (scade tra 7 giorni) - (u1, l1)
        prestitoService.registraPrestito(u1, l1, oggi.plusDays(7));
        
        // P3: Attivo, non scaduto (scade tra 14 giorni) - (u3, l3)
        prestitoService.registraPrestito(u3, l3, oggi.plusDays(14));
        
        // P6: Attivo, non scaduto (u6, l6)
        prestitoService.registraPrestito(u6, l6, oggi.plusDays(10));
        
        // P7: Attivo, non scaduto (u7, l7)
        prestitoService.registraPrestito(u7, l7, oggi.plusDays(5));
        
        // P10: Attivo, non scaduto (u10, l7) - Usa lo stesso libro di P7 
        prestitoService.registraPrestito(u10, l7, oggi.plusDays(20));
        
        // --- Prestiti manuali (per stati speciali) ---
        
        // P2: Attivo, SCADUTO (Manuale: bypassa la validazione data di registraPrestito) (u2, l2)
        l2.decrementaCopie(); 
        libroService.modifica(l2); 
        Prestito p2Scaduto = new Prestito(u2, l2, oggi.minusDays(3)); 
        prestitoService.salva(p2Scaduto); 
        
        // P8: Attivo, SCADUTO (Manuale) (u8, l8)
        l8.decrementaCopie(); 
        libroService.modifica(l8); 
        Prestito p8Scaduto = new Prestito(u8, l8, oggi.minusDays(1)); 
        prestitoService.salva(p8Scaduto); 
        
        // P4: Chiuso/Restituito in tempo (Simulazione storica) (u4, l4)
        // Per i prestiti chiusi, assumiamo che le copie siano già state re-incrementate.
        Prestito p4Chiuso = new Prestito(u4, l4, oggi.plusDays(5)); 
        p4Chiuso.registraRestituzione(oggi.minusDays(1)); 
        prestitoService.salva(p4Chiuso); 
        
        // P5: Chiuso/Restituito in ritardo (Simulazione storica) (u5, l5)
        Prestito p5Chiuso = new Prestito(u5, l5, oggi.minusDays(10)); 
        p5Chiuso.registraRestituzione(oggi); 
        prestitoService.salva(p5Chiuso); 

        // P9: Chiuso/Restituito in tempo (u9, l9)
        Prestito p9Chiuso = new Prestito(u9, l9, oggi.plusDays(1)); 
        p9Chiuso.registraRestituzione(oggi); 
        prestitoService.salva(p9Chiuso); 
        
        // --- Salvataggio Finale Esplicito per Garantire la Persistenza ---
        
        System.out.println("Salvataggio finale di tutti gli stati...");
        
        // Chiamiamo il metodo modifica su tutti gli oggetti per forzare la scrittura su file 
        // anche per gli elementi la cui modifica non è avvenuta tramite i servizi standard.
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