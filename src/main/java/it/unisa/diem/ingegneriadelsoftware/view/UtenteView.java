

package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.List;
import javafx.scene.layout.VBox;

/**
 * @class UtenteView
 * @brief Permette la modifica e l'inserimento dei dati a video all'amministratore per l'entità Utente.
 * @see CrudViewBase
 * @see DatiBaseView
 */
public class UtenteView extends DatiBaseView<Utente> {


    /** @brief Campo di ingresso per il nome a cui è associato l' utente. */
    private final TextField nomeInput;
    /** @brief Campo di input per il cognome utente. */
    private final TextField cognomeInput;
    /** @brief Campo di input per la matricola utente . */
    private final TextField matricolaInput; 
    /** @brief Campo di ingresso per l'indirizzo email a cui è associato l'utente. */
    private final TextField emailInput;
    
    
    /**
     * @brief Costruttore di base della vista Utente.
     * @post I campi di input sono istanziati e la vista è pronta.
     */
    public UtenteView() {
        
    super("Utente"); 
    

    this.nomeInput = new TextField();
    this.cognomeInput = new TextField();
    this.matricolaInput = new TextField(); 
    this.emailInput = new TextField();
    
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
     * @brief Serve per mettere le colonne della tabella per visualizzazione gli attributi dell'Utente.
     * @pre La TableView deve essere inizializzata.
     * @post La TableView contiene le colonne 'Nome', 'Cognome', 'Matricola' e 'Email'.
     */
    @Override
    protected void impostaColonneTabella() {

        
        TableColumn<Utente, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        
        TableColumn<Utente, String> cognomeCol = new TableColumn<>("Cognome");
        cognomeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCognome()));

        TableColumn<Utente, String> matricolaCol = new TableColumn<>("Matricola");
       
        matricolaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        
        
        TableColumn<Utente, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        tableView.getColumns().addAll(nomeCol, cognomeCol, matricolaCol, emailCol);
        tableView.setPlaceholder(new Label("Nessun contenuto nella tabella"));
    }

    /**
     * @brief Mette i valori nei campi di input per la modifica o l'inserimento.
     * @param [in] utente L'oggetto Utente i cui dati devono essere mostrati.
     * @post I campi di input riflettono i dati dell'utente o sono vuoti.
     * @see DatiBaseView#impostaValoriDefault(T)
     */
    @Override
    protected void impostaValoriDefault(Utente utente) {

        
        if (utente != null) {
            nomeInput.setText(utente.getNome());
            cognomeInput.setText(utente.getCognome());
            matricolaInput.setText(utente.getId());
            emailInput.setText(utente.getEmail());
            matricolaInput.setEditable(false);
        } else {
            nomeInput.setText("");
            cognomeInput.setText("");
            matricolaInput.setText("");
            emailInput.setText("");
            matricolaInput.setEditable(true); 
        }
    }

    /**
     * @brief Crea e configura il pannello per l'inserimento/modifica.
     * @return Il pannello GridPane contenente i controlli di input per l'entità Utente.
     * @see DatiBaseView#creaPaneDettaglio()
     */
    @Override
    protected GridPane creaPaneDettaglio() {
        
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        
        // Miglioramento: Imposta una larghezza minima per il pannello di dettaglio
        detailPane.setMinWidth(250);
        
        Label dettagliLabel = new Label("Dettagli Utente");
        dettagliLabel.setStyle("-fx-font-weight: bold;");
        detailPane.add(dettagliLabel, 0, 0, 2, 1);
        
        // Uniformità dei campi di testo
        nomeInput.setPrefWidth(200);
        cognomeInput.setPrefWidth(200);
        matricolaInput.setPrefWidth(200);
        emailInput.setPrefWidth(200);
        
        detailPane.add(new Label("Nome:"), 0, 1);
        detailPane.add(nomeInput, 1, 1);
        
        detailPane.add(new Label("Cognome:"), 0, 2);
        detailPane.add(cognomeInput, 1, 2);
        
        detailPane.add(new Label("Matricola:"), 0, 3);
        detailPane.add(matricolaInput, 1, 3);
        
        

        detailPane.add(new Label("Email:"), 0, 4);
        detailPane.add(emailInput, 1, 4);
        
         detailPane.add(messaggioLabel, 0, 5, 2, 1); // Componente ereditato
        return detailPane;
    }
    

    
    /**
     * @brief Recupera i dati che stanno nel form per la creazione di un nuovo utente.
     * @return Un oggetto Utente con i dati del form, altrimenti restituisce un valore nullo.
     * @pre I campi devono essere compilati.
     */
    public Utente getUtenteNuovo() {
        
        try {
            String nome = nomeInput.getText().trim();
            String cognome = cognomeInput.getText().trim();
            String matricola = matricolaInput.getText().trim();
            String email = emailInput.getText().trim();
            
            return new Utente(nome, cognome, matricola, email);

        } catch (IllegalArgumentException e) {
            mostraMessaggio("Errore: tutti i campi utente sono obbligatori.");
            return null;
        }
    }


   /**
     * @brief i dati modificati per aggiornare un utente esistente vengono recuperati.
     * @return L'oggetto Utente con i dati aggiornati, altrimenti restituisce un valore nullo.
     * @pre Un utente deve essere selezionato dalla tabella.
     * @post Viene restituita l'istanza aggiornata.
     */
    public Utente getUtenteModificato() {
        Utente utenteDaModificare = getElementoSelezionato();
        
        if (utenteDaModificare == null) {
            
            mostraMessaggio("Selezionare un utente prima di procedere con la modifica.");
            return null;
        }

        try {
            
            utenteDaModificare.setNome(nomeInput.getText().trim());
            
            utenteDaModificare.setCognome(cognomeInput.getText().trim());
            
            utenteDaModificare.setEmail(emailInput.getText().trim());
            
            return utenteDaModificare;
        } catch (IllegalArgumentException e) {
             mostraMessaggio("Errore: controllare i dati modificati.");
             return null;
        }
    }
}