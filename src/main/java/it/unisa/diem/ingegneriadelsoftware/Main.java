package it.unisa.diem.ingegneriadelsoftware;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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


/**
 * @class Main
 * @brief Classe principale per l'avvio dell'applicazione Biblioteca.
 * @details Si occupa di inizializzare l'architettura MVC,
 * configurando i repository, i service e le view, per poi avviare l'interfaccia grafica JavaFX.
 */
public class Main extends Application {

    private static final String FILE_LIBRI = "libri.dat";
    private static final String FILE_UTENTI = "utenti.dat";
    private static final String FILE_PRESTITI = "prestiti.dat";

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * @details Costruisce l'intera gerarchia delle dipendenze:
     * Repository, Service, View e Controller.
     * Infine, assembla la scena principale e la mostra a video.
     * @param [in] primaryStage Lo stage principale fornito da JavaFX.
     * @pre Il sistema deve avere i permessi di lettura/scrittura sui file dati.
     * @post L'applicazione grafica viene mostrata all'utente.
     */
    @Override
    public void start(Stage primaryStage) { 

        //Inizializzazione dei repository
        GestoreFile<Libro> gestoreLibri = new GestoreFile<>();
        Repository<Libro> repoLibri = new Repository<>(FILE_LIBRI, gestoreLibri);

        GestoreFile<Utente> gestoreUtenti = new GestoreFile<>();
        Repository<Utente> repoUtenti = new Repository<>(FILE_UTENTI, gestoreUtenti);

        GestoreFile<Prestito> gestorePrestiti = new GestoreFile<>();
        Repository<Prestito> repoPrestiti = new Repository<>(FILE_PRESTITI, gestorePrestiti);

        
        //Inizializzazione dei service
        LibroService libroService = new LibroService(repoLibri);
        UtenteService utenteService = new UtenteService(repoUtenti);
        PrestitoService prestitoService = new PrestitoService(repoPrestiti, libroService);

        //Inizializzazione delle view
        LibroView libroView = new LibroView();
        UtenteView utenteView = new UtenteView();
        PrestitoView prestitoView = new PrestitoView();
        

        //Inizializzazione dei controller
        LibroController libroController = new LibroController(libroView, libroService);
        UtenteController utenteController = new UtenteController(utenteView, utenteService);
        PrestitoController prestitoController = new PrestitoController(prestitoView, prestitoService);

        //Avvio dei controller
        libroController.init();
        utenteController.init();
        prestitoController.init();


        //Configurazione del layout
        TabPane tabPane = new TabPane();
        Tab tabLibri = new Tab("Gestione Libri", libroView.getTableView().getParent()); 
        tabLibri.setClosable(false);

        
        Tab tabUtenti = new Tab("Gestione Utenti", utenteView.getTableView().getParent());
        tabUtenti.setClosable(false);

        
        Tab tabPrestiti = new Tab("Gestione Prestiti", prestitoView.getTableView().getParent());
        tabPrestiti.setClosable(false);

        tabPrestiti.setOnSelectionChanged(event -> {
            if (tabPrestiti.isSelected()) {
                prestitoView.setLibriList(libroService.getAll());
                prestitoView.setUtentiList(utenteService.getAll());
                prestitoController.aggiornaVista(); 
            }
        });
        
        prestitoView.setLibriList(libroService.getAll());
        prestitoView.setUtentiList(utenteService.getAll());

        tabPane.getTabs().addAll(tabLibri, tabUtenti, tabPrestiti);

        //Creazione della scena 
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 900, 600); 

        primaryStage.setTitle("Sistema di gestione di una biblioteca - Ingegneria del Software");
        primaryStage.setScene(scene);

        //Visualizzazione della finestra
        primaryStage.show();
    }
    
        
        
    

    /**
     * @brief Metodo principale di ingresso .
     * @param [in] args Argomenti da riga di comando.
     * @post L'applicazione viene lanciata.
     */
    public static void main(String[] args) { 
         launch(args);
    }
    
}

