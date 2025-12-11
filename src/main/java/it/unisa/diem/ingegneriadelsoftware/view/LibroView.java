package it.unisa.diem.ingegneriadelsoftware.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.List;
import javafx.scene.control.*;


import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.Arrays;
import javafx.scene.layout.HBox;

/**
 * @class LibroView
 * @brief Permette la modifica e l'inserimento dei dati a video all'utente per i Libri.
 * @see BaseView
 */
public class LibroView extends BaseView<Libro> {
    
    private  TableView<Libro> libriTable;
    private final ObservableList<Libro> dataList;


    private final TextField cercaField;
    private final Button modificaButton;
    private final Button cancellaButton;
    private final Button okButton;
    private final TextField inserisciNuovoCampo; 


    private final TextField titoloInput;
    private final TextField annoInput;
    private final TextField isbnInput;
    private final TextField copieInput;
    private final Label messaggioLabel;
    
 
    public LibroView() {
        this.dataList = FXCollections.observableArrayList();
        this.libriTable = new TableView<>(dataList);
        

        this.cercaField = new TextField();
        this.modificaButton = new Button("Modifica");
        this.cancellaButton = new Button("Cancella");
        this.okButton = new Button("OK");
        this.inserisciNuovoCampo = new TextField("Inserisci nuovo libro"); 

        this.titoloInput = new TextField();
        this.annoInput = new TextField();
        this.isbnInput = new TextField();
        this.copieInput = new TextField();
        this.messaggioLabel = new Label("Pronto.");
        
        // Configurazione colonne TableView
        impostaColonneTabella();
        
        // Listener per la TableView
        impostaListener();


        VBox root = new VBox();
        root.getChildren().addAll(creaTopControls(), libriTable, creaPane());
        
 
    }
 

    private void impostaValoriDefault(Libro libro) {
        if (libro != null) {
            titoloInput.setText(libro.getTitolo());
            annoInput.setText(String.valueOf(libro.getAnno()));
            isbnInput.setText(libro.getId());
            copieInput.setText(String.valueOf(libro.getCopieDisponibili()));
            isbnInput.setEditable(false); 
        } else {
            titoloInput.setText("");
            annoInput.setText("");
            isbnInput.setText("");
            copieInput.setText("");
            isbnInput.setEditable(true);
        }
    }

    private void impostaColonneTabella() {
        
        //Titolo
        TableColumn<Libro, String> titoloCol = new TableColumn<>("Titolo");
        titoloCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitolo()));

        //Autori 
        TableColumn<Libro, String> autoriCol = new TableColumn<>("Autori");
        autoriCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.join(", ", cellData.getValue().getAutori()))
        );

        //Anno
        TableColumn<Libro, Integer> annoCol = new TableColumn<>("Anno");
        annoCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnno()).asObject());

        //ISBN (ID)
        TableColumn<Libro, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        
        //Copie disponibili
        TableColumn<Libro, Integer> copieCol = new TableColumn<>("Copie disponibili");
        copieCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCopieDisponibili()).asObject());

        libriTable.getColumns().addAll(titoloCol, autoriCol, annoCol, isbnCol, copieCol);
        
        //se la lista è vuota
        libriTable.setPlaceholder(new Label("Nessun contenuto nella tabella"));
    }
    

    private void impostaListener() {
        libriTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                impostaValoriDefault(newSelection);
            } else {
                // Pulisce i campi se non c'è selezione
                impostaValoriDefault(null);
            }
        });
    } 
    


    private VBox creaTopControls() {
        Label label=new Label("Gestione libri");
        HBox box=new HBox(new Label("Cerca:"), cercaField, modificaButton, cancellaButton,inserisciNuovoCampo, okButton);

        return new VBox(label,box);
    }
    
    
    
    private GridPane creaPane() {
        
        
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        detailPane.add(new Label("Dettagli Libro"), 0, 0, 2, 1);
        
        
        
        detailPane.add(new Label("Titolo:"), 0, 1);
        detailPane.add(titoloInput, 1, 1);
        
        detailPane.add(new Label("Anno:"), 0, 2);
        detailPane.add(annoInput, 1, 2);
        
        detailPane.add(new Label("ISBN:"), 0, 3);
        detailPane.add(isbnInput, 1, 3);

        detailPane.add(new Label("Copie disponibili:"), 0, 4);
        detailPane.add(copieInput, 1, 4);
        
        detailPane.add(messaggioLabel, 0, 5, 2, 1); 
        
        
        return detailPane;
    }
    
    //Override
    @Override
    public void mostraLista(List<Libro> lista) {
        super.mostraLista(lista);

        dataList.setAll(lista); 
    }

    @Override
    public Libro getElementoSelezionato() {

        return libriTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public String getCampoCerca() {
        return cercaField.getText();
    }

    @Override
    public void mostraMessaggio(String messaggio) {

        messaggioLabel.setText(messaggio);
    }
    
    
    

    /**
     * @brief Recupera i dati inseriti nel form per creare un nuovo libro.
     * @return Un nuovo oggetto Libro popolato con i dati del form, altrimenti restituisce un valore nullo.
     * @pre I campi devono contenere dati validi.
     */
    public Libro getLibroNuovo() {
    
        try {
            String titolo = titoloInput.getText().trim();
            int anno = Integer.parseInt(annoInput.getText().trim());
            String isbn = isbnInput.getText().trim();
            int copie = Integer.parseInt(copieInput.getText().trim());
            
            // Assunzione: Autori gestiti separatamente
            return new Libro(titolo, Arrays.asList("Autore Sconosciuto"), anno, isbn, copie);

        } catch (IllegalArgumentException e) {
            mostraMessaggio("Errore: controllare i campi numerici o i dati obbligatori.");
            return null;
        }
    
    }

    /**
     * @brief Recupera i dati modificati dal form per aggiornare un libro esistente.
     * @return L'oggetto Libro con i dati aggiornati, altrimenti restituisce un valore nullo.
     * @pre Un libro deve essere stato precedentemente selezionato o caricato nel form.
     * @post Viene restituito l'oggetto pronto per essere passato al controller.
     */
    public Libro getLibroModificato() {
    
        Libro libroDaModificare = getElementoSelezionato();
        
        
        if (libroDaModificare == null) {
            mostraMessaggio("Selezionare un libro prima di procedere con la modifica.");
            return null;
        }

        try {
            libroDaModificare.setTitolo(titoloInput.getText().trim());
            libroDaModificare.setAnno(Integer.parseInt(annoInput.getText().trim()));
            libroDaModificare.setCopieDisponibili(Integer.parseInt(copieInput.getText().trim()));

            return libroDaModificare;
        } catch (NumberFormatException e) {
            mostraMessaggio("Errore: Anno e Copie disponibili devono essere numeri validi.");
            return null;
        }
    }
    
    
    
    //metodi per il Controller
    public Button getModificaButton() {
        return modificaButton;
    }

    public Button getCancellaButton() {
        return cancellaButton;
    }

    public Button getOkButton() {
        return okButton;
    }
    
    public TextField getCercaField() {
        return cercaField;
    }

}