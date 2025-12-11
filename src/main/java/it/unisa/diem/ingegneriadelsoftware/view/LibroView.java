package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Libro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Arrays;
import java.util.List;

/**
 * @class LibroView
 * @brief Permette la modifica e l'inserimento dei dati a video all'utente per i Libri.
 * @see CrudViewBase
 */
public class LibroView extends DatiBaseView<Libro> {

    // Campi Dettagli Libro (Specifici)
    private final TextField titoloInput;
    private final TextField annoInput;
    private final TextField isbnInput;
    private final TextField copieInput;

    public LibroView() {
        
        
        super("Libro"); 

        
        
        
        this.titoloInput = new TextField();
        this.annoInput = new TextField();
        this.isbnInput = new TextField();
        this.copieInput = new TextField();
    }


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

    @Override
    protected GridPane creaPaneDettaglio() {

        
        
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
        
        detailPane.add(messaggioLabel, 0, 5, 2, 1); // Componente ereditato
        return detailPane;
    }
    

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