

package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.VBox;

/**
 * @class LibroView
 * @brief Permette la modifica e l'inserimento dei dati a video all'utente per i Libri.
 * @see CrudViewBase
 * @see BaseView
 */

public class LibroView extends DatiBaseView<Libro> {

    
    /** @brief Campo di input per il titolo del libro. */
    private final TextField titoloInput;
    
    /** @brief Campo di input per l'anno di pubblicazione. */
    private final TextField annoInput;
    
    /** @brief Campo di input per il codice ISBN . */
    private final TextField isbnInput;
    
    /** @brief Campo di input per il numero di copie disponibili. */
    private final TextField copieInput;

    
    
    /**
     * @brief Costruttore di base della vista Libro.
     * @post I campi di input sono istanziati e la vista è pronta per l'inizializzazione.
     */
    
    public LibroView() {
        

        super("Libro"); 
        

        this.titoloInput = new TextField();
        this.annoInput = new TextField();
        this.isbnInput = new TextField();
        this.copieInput = new TextField();
        

        // MODIFICA: Crea un box esterno per incorniciare il dettaglio (frame)
        GridPane innerDetailPane = creaPaneDettaglio();
        VBox detailFrame = new VBox(innerDetailPane);
        detailFrame.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: white; -fx-border-radius: 5;");
        
        // MODIFICA: Imposta l'altezza fissa della box a 300px per uguagliare l'altezza della tabella
        detailFrame.setPrefHeight(300); 
        detailFrame.setMaxHeight(300);

        // Aggiunge il frame alla HBox (affiancato alla TableView)
        contentHBox.getChildren().add(detailFrame); 
        

    }
    



    /**
     * @brief Imposta le colonne della tabella per visualizzare gli attributi del Libro.
     * @pre La TableView deve essere stata inizializzata nella classe base.
     * @post La TableView contiene le seguenti colonne 'Titolo', 'Autori', 'Anno', 'ISBN', 'Copie disponibili'.
     */
    
    @Override
    protected void impostaColonneTabella() {

        TableColumn<Libro, String> titoloCol = new TableColumn<>("Titolo");
        titoloCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitolo()));
        
        TableColumn<Libro, String> autoriCol = new TableColumn<>("Autori");
    
        autoriCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(", ", cellData.getValue().getAutori())));

        TableColumn<Libro, Integer> annoCol = new TableColumn<>("Anno");
        annoCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnno()).asObject());

        TableColumn<Libro, String> isbnCol = new TableColumn<>("ISBN");
  
        isbnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        
        
        TableColumn<Libro, Integer> copieCol = new TableColumn<>("Copie disponibili");
        
        copieCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCopieDisponibili()).asObject());

        tableView.getColumns().addAll(titoloCol, autoriCol, annoCol, isbnCol, copieCol);
        tableView.setPlaceholder(new Label("Nessun contenuto nella tabella"));
    }

    
    
    /**
     * @brief Imposta i valori nei campi di input per la modifica o l'inserimento.
     * @param [in] libro L'oggetto Libro i cui dati devono essere mostrati.
     * @post I campi di input riflettono i dati del libro o sono vuoti. 
     * @see CrudViewBase#impostaValoriDefault(T)
     */
    @Override
    protected void impostaValoriDefault(Libro libro) {

        
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

    /**
     * @brief Crea e configura il pannello  per l'inserimento/modifica.
     * @return Il pannello GridPane contenente i controlli di input per l'entità Libro.
     * @post Il pannello viene restituito pronto per essere integrato nella vista principale altrimenti un valore nullo.
     * @see CrudViewBase#creaPaneDettaglio()
     */
    
    @Override
    protected GridPane creaPaneDettaglio() {

        
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        
        // Miglioramento: Imposta una larghezza minima per il pannello di dettaglio
        detailPane.setMinWidth(250);
        
        Label dettagliLabel = new Label("Dettagli Libro");
        dettagliLabel.setStyle("-fx-font-weight: bold;"); // Etichetta in grassetto
        detailPane.add(dettagliLabel, 0, 0, 2, 1);
        
        // Uniformità dei campi di testo
        titoloInput.setPrefWidth(200);
        annoInput.setPrefWidth(200);
        isbnInput.setPrefWidth(200);
        copieInput.setPrefWidth(200);
        
        
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
}