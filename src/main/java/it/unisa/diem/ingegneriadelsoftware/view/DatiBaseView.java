package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.geometry.Insets; 

/**
 * @class DatiBaseView
 * @brief Classe base astratta per le View che gestiscono i Dati Libri e i Dati Utenti.
 * @tparam T Il tipo di dato gestito che deve implementare InterfaceID.
 * @see BaseView
 */
public abstract class DatiBaseView<T extends InterfaceID> extends BaseView<T> {


    private final VBox root;
    /** @brief Campo di testo per la ricerca degli elementi. */
    protected final TextField cercaField;
    /** @brief Bottone per avviare la cancellazione dell'elemento . */
    protected final Button cancellaButton;
    /** @brief Bottone di conferma per l' inserimento o la modifica. */
    protected final Button okButton;
    /** @brief Bottone per annullare l'operazione corrente e pulire i campi di dettaglio. */
    protected final Button annullaButton; 
    /** @brief Bottone per annullare la ricerca corrente e mostrare tutti gli elementi. */
    protected final Button annullaCercaButton; 
    /** @brief Label contenente il testo del messaggio. */
    protected final Label messaggioLabel;
    /** @brief Contenitore stilizzato per la visualizzazione dei messaggi di stato, errore o conferma. */
    protected final HBox messaggioBox; 
    
    
    /** @brief Tableview serve per la visualizzazione della tabella degli elementi. */
    protected final TableView<T> tableView;
    
    /** @brief Contenitore HBox per affiancare la tabella al pannello di dettaglio. */
    protected final HBox contentHBox;

    /**
     * @brief Costruttore base della classe .
     * @details Inizializza tutti i componenti e configura la TableView chiamando i metodi astratti tramite la definizione delle colonne.
     * @param [in] entità Nome dell'entità gestita .
     * @post I componenti sono istanziati e la struttura base della vista è assemblata.
     */
    public DatiBaseView(String entità) {
        
  
        this.cercaField = new TextField();
        // MODIFICA: Renaming 'Cancella' to 'Elimina' and setting the red style
        this.cancellaButton = new Button("Elimina"); 
        this.cancellaButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;"); // Red style
        this.okButton = new Button("Salva/Aggiorna"); 
        this.annullaButton = new Button("Annulla"); 
        this.annullaCercaButton = new Button("Annulla Cerca"); 
        
        this.messaggioLabel = new Label("Pronto.");
        this.messaggioBox = new HBox(this.messaggioLabel);
        this.messaggioBox.setAlignment(Pos.CENTER_LEFT);
        this.messaggioBox.setPadding(new Insets(5));
        this.messaggioBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 5; -fx-background-color: #f4f4f4; -fx-border-radius: 3;");


        this.tableView = new TableView<>(dataList);
        
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        // MODIFICA: Aumento altezza TableView a 350px
        this.tableView.setPrefHeight(350); 
        this.tableView.setMaxHeight(350); 
     
        impostaColonneTabella();
        impostaListener();
        
        // Miglioramento: impostazione minima di larghezza preferita per i pulsanti
        cancellaButton.setPrefWidth(80);
        okButton.setPrefWidth(120); 
        annullaButton.setPrefWidth(80); 
        annullaCercaButton.setPrefWidth(100); 
        


        this.root = new VBox(15); // AUMENTO SPAZIO VERTICALE (15px)
        this.root.setPadding(new Insets(10)); 

        // Configurazione HBox: contiene TableView e Dettaglio (aggiunto nelle sottoclassi)
        this.contentHBox = new HBox(15); 
        this.contentHBox.getChildren().add(tableView);
        HBox.setHgrow(tableView, Priority.ALWAYS); 
        
        // La VBox principale contiene i controlli (sopra) e l'HBox (sotto)
        root.getChildren().addAll(creaTopControls(entità), contentHBox);
        VBox.setVgrow(contentHBox, Priority.ALWAYS);

        
    }

    /**
     * @brief Metodo astratto per la configurazione delle colonne della TableView.
     * @pre La TableView deve essere stata inizializzata nel costruttore.
     * @post La TableView contiene le colonne necessarie per l'entità T.
     */
    protected abstract void impostaColonneTabella();
    
    

    /**
     * @brief Metodo astratto per impostare i valori nei campi di ingresso.
     * @param [in] elemento L'oggetto i cui dati devono essere visualizzati nel form.
     * @post I campi di input vengono popolati o svuotati.
     */
    protected abstract void impostaValoriDefault(T elemento);
    

    
    /**
     * @brief Metodo astratto per creare il pannello di dettaglio (form) per inserimento/modifica.
     * @return Il GridPane contenente l'interfaccia per la manipolazione dei dati altrimenti un valore nullo.
     * @post Il pannello di dettaglio viene creato e restituito.
     */
    protected abstract GridPane creaPaneDettaglio();



    /**
     * @brief Crea il contenitore con i controlli superiori (Cerca, Annulla Cerca e Elimina).
     * @param [in] entityName Il nome dell'entità gestita.
     * @return Il VBox contenente i controlli di ricerca e le azioni principali.
     */
    private VBox creaTopControls(String entityName) {
        
        // Riga unica: Cerca + Annulla Cerca + Elimina
        HBox searchAndActionBox = new HBox(10);
        // Allinea l'HBox a SINISTRA
        searchAndActionBox.setAlignment(Pos.CENTER_LEFT); 
        searchAndActionBox.getChildren().addAll(
            new Label("Cerca:"), 
            cercaField,
            annullaCercaButton,
            cancellaButton // Ora posizionato dopo i controlli di ricerca
        );
        
        // MODIFICA: Limita la larghezza del campo di ricerca
        cercaField.setPrefWidth(250);
        
        // Contenitore superiore unico
        VBox topContainer = new VBox(5); 
        topContainer.getChildren().add(searchAndActionBox);
        
        return topContainer;
    }
    
    /**
     * @brief Configura i listener principali della vista.
     */
    protected void impostaListener() {
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            impostaValoriDefault(newSelection);
            // Forza lo stato per il nuovo inserimento quando la selezione è nulla
            if (newSelection == null) {
                okButton.setText("Salva Nuovo");
            } else {
                okButton.setText("Aggiorna");
            }
        });
    }

    /**
     * @brief Restituisce l'elemento selezionato nella TableView.
     * @return L'oggetto T selezionato, altrimenti restituisce un valore nullo.
     * @see BaseView#getElementoSelezionato()
     */
    @Override
    public T getElementoSelezionato() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    /**
     * @brief Ottiene la stringa di ricerca corrente dal campo di ingresso.
     * @return Il testo inserito nel cercaField, altrimenti restituisce una stringa vuota.
     * @see BaseView#getCampoCerca()
     */
    @Override
    public String getCampoCerca() {
        return cercaField.getText();
    }

    /**
     * @brief Mostra un messaggio di stato, errore o conferma all'utente.
     * @param [in] messaggio Il testo da visualizzare.
     * @see BaseView#mostraMessaggio(String)
     */
    @Override
    public void mostraMessaggio(String messaggio) {
        messaggioLabel.setText(messaggio);
        
        // MODIFICA: Implementa la colorazione della box in base al messaggio
        if (messaggio != null && messaggio.toLowerCase().contains("errore")) {
            messaggioLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            messaggioBox.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-padding: 5; -fx-background-color: #ffe0e0; -fx-border-radius: 3;");
        } else {
            // Messaggi di successo o stato generico (testo nero, box neutra)
            messaggioLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
            messaggioBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 5; -fx-background-color: #f4f4f4; -fx-border-radius: 3;");
        }
    }
    
    /**
     * @brief Resetta il form di dettaglio, svuotando i campi e deselezionando la riga in tabella.
     * @post Il form è pronto per un nuovo inserimento.
     */
    public void pulisciDettagli() {
        tableView.getSelectionModel().clearSelection();
        impostaValoriDefault(null);
        mostraMessaggio("Pronto per un nuovo inserimento.");
    }

    
    /** @return Il bottone "Cancella" (ora "Elimina"). */
    public Button getCancellaButton() { return cancellaButton; }
    
    
    /** @return Il bottone "Salva/Aggiorna" (ex OK). */
    public Button getOkButton() { return okButton; }
    
    /** @return Il bottone per annullare i campi di dettaglio. */
    public Button getAnnullaButton() { return annullaButton; }
    
    /** @return Il bottone per annullare la ricerca. */
    public Button getAnnullaCercaButton() { return annullaCercaButton; }
    
    /** @return Il campo di testo per la ricerca. */
    public TextField getCercaField() { return cercaField; }
    
    
    /** @return La TableView principale. */
    public TableView<T> getTableView() { return tableView; }
    
    /** @return Il contenitore della label per il messaggio. */
    public HBox getMessaggioBox() { return messaggioBox; }
    
    
    public Parent getRoot() { 
        return root;
    }
    
    /** @return Il contenitore HBox per affiancare la tabella al pannello di dettaglio. */
    public HBox getContentHBox() { return contentHBox; }
}