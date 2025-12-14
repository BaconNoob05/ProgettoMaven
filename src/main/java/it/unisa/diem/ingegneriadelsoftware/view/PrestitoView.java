package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets; 

/**
 * @class PrestitoView
 * @brief Permette la registrazione e la gestione dei prestiti e delle restituzioni.
 * @see DatiBaseView
 */

public class PrestitoView extends DatiBaseView<Prestito> {
    
    
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

   
    private final ComboBox<Libro> libroComboBox;
    
    private final ComboBox<Utente> utenteComboBox;

    
    private final DatePicker dataPrestitoPicker;
    private final DatePicker dataRestituzionePicker; 
    private final ComboBox<String> statoComboBox; 
    

    private final Button registraPrestitoButton;
    private final Button restituisciLibroButton;
    private final Button annullaSpecifcButton;

    public PrestitoView() {

        super("Prestito"); 
    

        this.libroComboBox = new ComboBox<>();
        this.utenteComboBox = new ComboBox<>();
        this.dataPrestitoPicker = new DatePicker(LocalDate.now()); 
        this.dataRestituzionePicker = new DatePicker(); 
        this.statoComboBox = new ComboBox<>(FXCollections.observableArrayList("In Prestito", "SCADUTO", "Restituito")); 
        this.statoComboBox.setValue("In Prestito"); 
        this.registraPrestitoButton = new Button("Registra Prestito");
        this.restituisciLibroButton = new Button("Restituisci Libro");
        this.annullaSpecifcButton = getAnnullaButton(); 
        this.annullaSpecifcButton.setText("Annulla"); 

        registraPrestitoButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        restituisciLibroButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        this.annullaSpecifcButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");


        GridPane innerDetailPane = creaPaneDettaglio();
        VBox detailFrame = new VBox(innerDetailPane);
        detailFrame.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: white; -fx-border-radius: 5;");
        

        detailFrame.setPrefHeight(350); 
        detailFrame.setMaxHeight(350);


        contentHBox.getChildren().add(detailFrame); 


        tableView.setRowFactory(tv -> new TableRow<Prestito>() {
            @Override
            protected void updateItem(Prestito item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.isScaduto()) {
                    setStyle("-fx-background-color: #ffcccc;"); 
                } else if (item.getDataEffettiva() != null) {
                    setStyle("-fx-background-color: #ccffcc;"); 
                } else {

                    setStyle("");
                }
            }
        });
        
        super.getOkButton().setDisable(true);


       
        pulisciDettagli();
    }
    
    /**
     * @brief Configurazione delle colonne della tabella Prestito.
     */
    
    @Override
    protected void impostaColonneTabella() {
        
        
        TableColumn<Prestito, String> libroCol = new TableColumn<>("Libro");
        
        libroCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitolo()));

        TableColumn<Prestito, String> utenteCol = new TableColumn<>("Utente");
        utenteCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                
            cellData.getValue().getUtente().getCognome()
        ));

        TableColumn<Prestito, String> dataPrestitoCol = new TableColumn<>("Data prestito");
        dataPrestitoCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDataPrestito().format(DATA))
        );
        

        
        TableColumn<Prestito, String> dataRestituzioneCol = new TableColumn<>("Data restituzione");
        dataRestituzioneCol.setCellValueFactory(cellData -> {
            LocalDate data = Optional.ofNullable(cellData.getValue().getDataEffettiva()).orElse(cellData.getValue().getDataPrevista());
            return new SimpleStringProperty(data.format(DATA));
        });

        TableColumn<Prestito, String> statoCol = new TableColumn<>("Stato");
        statoCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataEffettiva() != null) {
                
                return new SimpleStringProperty("Restituito");
                
            } else if (cellData.getValue().isScaduto()) {
                
                return new SimpleStringProperty("SCADUTO");
                
            } else {
                
                return new SimpleStringProperty("In Prestito");
            }
        });

        tableView.getColumns().addAll(libroCol, utenteCol, dataPrestitoCol, dataRestituzioneCol, statoCol);
        
        tableView.setPlaceholder(new Label("Nessun contenuto nella tabella"));
    }

    /**
     * @brief Popola i campi di input con i dati del prestito selezionato.
     * @param prestito L'oggetto Prestito selezionato, può essere null.
     */
    @Override
    protected void impostaValoriDefault(Prestito prestito) {
        if (prestito != null) {

            
            libroComboBox.setDisable(true);
            utenteComboBox.setDisable(true);
            
            dataPrestitoPicker.setDisable(true);

            
            libroComboBox.getSelectionModel().select(prestito.getLibro());
            utenteComboBox.getSelectionModel().select(prestito.getUtente());
            
            dataPrestitoPicker.setValue(prestito.getDataPrestito());
            

            if (prestito.getDataEffettiva() != null) {
                
                dataRestituzionePicker.setValue(prestito.getDataEffettiva());
                dataRestituzionePicker.setDisable(true); 
                statoComboBox.setValue("Restituito");
                restituisciLibroButton.setDisable(true);
                
            } else {

                
                dataRestituzionePicker.setValue(prestito.getDataPrevista()); 
                dataRestituzionePicker.setDisable(false);
                
                statoComboBox.setValue(prestito.isScaduto() ? "SCADUTO" : "In Prestito");
                restituisciLibroButton.setDisable(false);
            }
            
            registraPrestitoButton.setDisable(true); 
            mostraMessaggio("Prestito selezionato: " + prestito.getId());

        } else {
            pulisciDettagli();
        }
    }
    
    /**
     * @brief Crea il pannello dei dettagli Prestito a destra.
     * @return Il GridPane con i campi ComboBox e DatePicker.
     */
    
    @Override
    protected GridPane creaPaneDettaglio() {
        
        
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        
        detailPane.setMinWidth(300);
        
        Label dettagliLabel = new Label("Dettagli Prestito");
        dettagliLabel.setStyle("-fx-font-weight: bold;");
        detailPane.add(dettagliLabel, 0, 0, 2, 1);

        libroComboBox.setPrefWidth(200);
        utenteComboBox.setPrefWidth(200);
        dataPrestitoPicker.setPrefWidth(200);
        dataRestituzionePicker.setPrefWidth(200);
        statoComboBox.setPrefWidth(200);


        detailPane.add(new Label("Libro:"), 0, 1);
        detailPane.add(libroComboBox, 1, 1);
        
        detailPane.add(new Label("Utente:"), 0, 2);
        detailPane.add(utenteComboBox, 1, 2);
        
        detailPane.add(new Label("Data prestito:"), 0, 3);
        detailPane.add(dataPrestitoPicker, 1, 3);
        
        detailPane.add(new Label("Data restituzione:"), 0, 4); 
        detailPane.add(dataRestituzionePicker, 1, 4);
        
        detailPane.add(new Label("Stato:"), 0, 5);
        detailPane.add(statoComboBox, 1, 5);
        

        HBox pulsantiAzione = new HBox(10);
        pulsantiAzione.setAlignment(Pos.CENTER); 
        

        registraPrestitoButton.setPrefWidth(120);
        restituisciLibroButton.setPrefWidth(120);
        getAnnullaButton().setPrefWidth(80); 
        

        pulsantiAzione.getChildren().addAll(registraPrestitoButton, restituisciLibroButton, getAnnullaButton());
        
        

        detailPane.add(getMessaggioBox(), 0, 9, 2, 1); 
        detailPane.add(pulsantiAzione, 0, 12, 2, 1);
        
        
        return detailPane;
    }


    /**
     * @brief Pulisce i campi e li riabilita per l'inserimento di un nuovo prestito.
     */
    
    public void pulisciDettagli() {
        
        libroComboBox.getSelectionModel().clearSelection();
        utenteComboBox.getSelectionModel().clearSelection();
        
        dataPrestitoPicker.setValue(LocalDate.now());
        dataRestituzionePicker.setValue(null);    
        statoComboBox.setValue("In Prestito");
        

        
        libroComboBox.setDisable(false);
        utenteComboBox.setDisable(false);
        
        dataPrestitoPicker.setDisable(true); 
        dataRestituzionePicker.setDisable(false);
        
        registraPrestitoButton.setDisable(false);
        restituisciLibroButton.setDisable(true);
        
        tableView.getSelectionModel().clearSelection();
        mostraMessaggio("Pronto per registrare un nuovo prestito.");
    }
    
    /**
     * @brief Ottiene i dati dal form per la creazione di un nuovo Prestito.
     * @return Un'istanza di Prestito con i dati inseriti (non salvato), altrimenti null.
     */

    public Prestito getPrestitoNuovo() {
        Libro libroSelezionato = libroComboBox.getValue();
        Utente utenteSelezionato = utenteComboBox.getValue();

        LocalDate dataPrevista = dataRestituzionePicker.getValue(); 

        if (libroSelezionato == null || utenteSelezionato == null || dataPrevista == null) {
            mostraMessaggio("Errore: Selezionare Libro, Utente e Data di Restituzione Prevista.");
            return null;
        }

        try {

            
            return new Prestito(utenteSelezionato, libroSelezionato, dataPrevista);
        } catch (IllegalArgumentException e) {
            mostraMessaggio("Errore: Dati inseriti per il prestito non validi.");
            return null;
        }
    }
    
    /**
     * @brief Restituisce la data inserita nel DatePicker per la restituzione effettiva.
     * @return La data selezionata.
     */

    public LocalDate getDataRestituzione() {
        return dataRestituzionePicker.getValue();
    }

    public Button getRegistraPrestitoButton() {
        return registraPrestitoButton;
    }

    public Button getRestituisciLibroButton() {
        return restituisciLibroButton;
    }
    

    public void setLibriList(List<Libro> libri) {
        
        libroComboBox.setItems(FXCollections.observableArrayList(libri));
    }
    
    public void setUtentiList(List<Utente> utenti) {
        
        utenteComboBox.setItems(FXCollections.observableArrayList(utenti));
    }
}