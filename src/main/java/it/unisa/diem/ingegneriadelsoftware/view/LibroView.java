package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos; 
import java.util.stream.Collectors; 

/**
 * @class LibroView
 * @brief Permette la modifica e l'inserimento dei dati a video all'utente per i Libri.
 * @see DatiBaseView
 * @see BaseView
 */
public class LibroView extends DatiBaseView<Libro> {
    
    /** 
     * @brief Campo di input per il titolo del libro. 
     */
    private final TextField titoloInput;
    
    /** 
     * @brief Campo di input per l'anno di pubblicazione. 
     */
    private final TextField annoInput;
    
    /** 
     * @brief Campo di input per il codice ISBN. 
     */
    private final TextField isbnInput;
    
    /** 
     * @brief Campo di input per il numero di copie disponibili. 
     */
    private final TextField copieInput;

    /** 
     * @brief Campo di input per la lista degli autori (separati da virgole). 
     */
    private final TextField autoriInput; 
    
    
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
        this.autoriInput = new TextField(); 

        GridPane innerDetailPane = creaPaneDettaglio();
        VBox detailFrame = new VBox(innerDetailPane);
        detailFrame.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: white; -fx-border-radius: 5;");
        detailFrame.setPrefHeight(350); 
        detailFrame.setMaxHeight(350);

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
     * @see DatiBaseView#impostaValoriDefault(T)
     */
    @Override
    protected void impostaValoriDefault(Libro libro) {
        if (libro != null) {
            titoloInput.setText(libro.getTitolo());
            annoInput.setText(String.valueOf(libro.getAnno()));
            isbnInput.setText(libro.getId());
            autoriInput.setText(libro.getAutoriString()); 
            copieInput.setText(String.valueOf(libro.getCopieDisponibili()));
            isbnInput.setEditable(true); 
        } else {
            titoloInput.setText("");
            annoInput.setText("");
            isbnInput.setText("");
            autoriInput.setText(""); 
            copieInput.setText("");
            isbnInput.setEditable(true); 
            
        }
    }

    /**
     * @brief Crea e configura il pannello per l'inserimento o la modifica.
     * @return Il pannello GridPane contenente i controlli di input per l'entità Libro.
     * @post Il pannello viene restituito pronto per essere integrato nella view principale, altrimenti restituisce un valore nullo.
     * @see DatiBaseView#creaPaneDettaglio()
     */
    @Override
    protected GridPane creaPaneDettaglio() {
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        detailPane.setMinWidth(250);
        
        Label dettagliLabel = new Label("Dettagli Libro");
        dettagliLabel.setStyle("-fx-font-weight: bold;");
        detailPane.add(dettagliLabel, 0, 0, 2, 1);
        
        titoloInput.setPrefWidth(200);
        autoriInput.setPrefWidth(200); 
        annoInput.setPrefWidth(200);
        isbnInput.setPrefWidth(200);
        copieInput.setPrefWidth(200);
        
        detailPane.add(new Label("Titolo:"), 0, 1);
        detailPane.add(titoloInput, 1, 1);
        
        detailPane.add(new Label("Autori:"), 0, 2); 
        detailPane.add(autoriInput, 1, 2); 
        
        detailPane.add(new Label("Anno:"), 0, 3); 
        detailPane.add(annoInput, 1, 3); 
        
        detailPane.add(new Label("ISBN:"), 0, 4); 
        detailPane.add(isbnInput, 1, 4); 

        detailPane.add(new Label("Copie disponibili:"), 0, 5); 
        detailPane.add(copieInput, 1, 5); 
        
        detailPane.add(getMessaggioBox(), 0, 8, 2, 1); 

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER); 
        
        getOkButton().setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;"); 
        getAnnullaButton().setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        actionBox.getChildren().addAll(getOkButton(), getAnnullaButton());
        detailPane.add(actionBox, 0,11, 2, 1); 
        
        return detailPane;
    }
    
    /**
     * @brief Converte la stringa di autori (separati da virgola) in una lista di stringhe.
     * @param [in] autoriString La stringa contenente gli autori.
     * @return Una lista di autori con gli spazi tagliati.
     */
    private List<String> parseAutori(String autoriString) {
        if (autoriString == null || autoriString.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(autoriString.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }


    /**
     * @brief Recupera i dati inseriti nel form per creare un nuovo libro.
     * @return Un nuovo oggetto Libro popolato con i dati del form, altrimenti restituisce il valore null.
     * @pre I campi devono contenere dati validi.
     */
    public Libro getLibroNuovo() {
        try {
            String titolo = titoloInput.getText().trim();
            String annoText = annoInput.getText().trim();
            String isbn = isbnInput.getText().trim();
            String copieText = copieInput.getText().trim();
            String autoriString = autoriInput.getText().trim(); 

            if (titolo.isEmpty() || isbn.isEmpty() || annoText.isEmpty() || copieText.isEmpty() || autoriString.isEmpty()) {
                mostraMessaggio("Errore: Tutti i campi (Titolo, Autori, Anno, ISBN, Copie) sono obbligatori.");
                return null;
            }

            int anno = Integer.parseInt(annoText);
            int copie = Integer.parseInt(copieText);
            List<String> autori = parseAutori(autoriString); 

            if (copie < 0) {
                 mostraMessaggio("Errore: Le copie disponibili non possono essere negative.");
                 return null;
            }
            if (autori.isEmpty()) { 
                 mostraMessaggio("Errore: Specificare almeno un autore.");
                 return null;
            }

            Libro nuovoLibro = new Libro(titolo, autori, anno, isbn, copie); 

            if (!nuovoLibro.isValido()) {
                mostraMessaggio("Errore: Dati libro non validi (es. anno di pubblicazione futuro o ISBN/Autori non validi).");
                return null;
            }

            return nuovoLibro;
            
        } catch (NumberFormatException e) {
            mostraMessaggio("Errore: Anno e Copie disponibili devono essere numeri interi validi.");
            return null;
        } catch (Exception e) {
             mostraMessaggio("Errore generico durante la creazione del libro: " + e.getMessage());
             return null;
        }
    }


    /**
     * @brief Recupera i dati modificati dal form per aggiornare un libro esistente.
     * @return L'oggetto Libro con i dati aggiornati, altrimenti restituisce il valore null.
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
            String nuovoTitolo = titoloInput.getText().trim();
            String annoText = annoInput.getText().trim();
            String nuovoIsbn = isbnInput.getText().trim(); 
            String copieText = copieInput.getText().trim();
            String autoriString = autoriInput.getText().trim(); 
            
            if (nuovoTitolo.isEmpty() || annoText.isEmpty() || nuovoIsbn.isEmpty() || copieText.isEmpty() || autoriString.isEmpty()) {
                mostraMessaggio("Errore: Tutti i campi (Titolo, Autori, Anno, ISBN, Copie) sono obbligatori.");
                return null;
            }

            int nuovoAnno = Integer.parseInt(annoText);
            int nuoveCopie = Integer.parseInt(copieText);
            List<String> nuoviAutori = parseAutori(autoriString); 
            
            if (nuoveCopie < 0) {
                mostraMessaggio("Errore: Le copie disponibili non possono essere negative.");
                return null;
            }
            if (nuoviAutori.isEmpty()) { 
                mostraMessaggio("Errore: Specificare almeno un autore.");
                return null;
            }

            Libro libroAggiornato = new Libro(nuovoTitolo, nuoviAutori, nuovoAnno, nuovoIsbn, nuoveCopie); 
            

            if (!libroAggiornato.isValido()) {
                mostraMessaggio("Errore: Dati libro non validi (es. anno di pubblicazione futuro o ISBN/Autori non validi).");
                return null;
            }
            
            return libroAggiornato;
            
        } catch (NumberFormatException e) {
            mostraMessaggio("Errore: Anno e Copie disponibili devono essere numeri interi validi.");
            return null;
        } catch (Exception e) {
             mostraMessaggio("Errore generico durante la modifica del libro: " + e.getMessage());
             return null;
        }
    }
}