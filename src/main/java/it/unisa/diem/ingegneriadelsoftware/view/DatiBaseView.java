
Contenuti in evidenza della cartella
Codice Java per interfacce Grafiche per gestione di Libro, Utente e Prestito mediante classi base.

package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.geometry.Insets; // Aggiunto per Insets

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
    /** @brief Bottone per avviare la modifica dell'elemento . */
    protected final Button modificaButton;
    /** @brief Bottone per avviare la cancellazione dell'elemento . */
    protected final Button cancellaButton;
    /** @brief Bottone di conferma per l' inserimento o la modifica. */
    protected final Button okButton;
    /** @brief Campo di testo per un nuovo inserimento. */
    protected final TextField inserisciNuovoCampo; 
    /** @brief Label utilizzata per far visualizzare messaggi di stato, errore o conferma all'utente. */
    protected final Label messaggioLabel;
    
    
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
        this.modificaButton = new Button("Modifica");
        this.cancellaButton = new Button("Cancella");
        this.okButton = new Button("OK"); 
        this.inserisciNuovoCampo = new TextField("Inserisci nuovo " + entità.toLowerCase()); 
        this.messaggioLabel = new Label("Pronto.");

        this.tableView = new TableView<>(dataList);
        
        // MODIFICA: Imposta l'altezza preferita/massima per limitare a circa 10 righe
        this.tableView.setPrefHeight(300); 
        this.tableView.setMaxHeight(300); 
     
        impostaColonneTabella();
        impostaListener();
        
        // Miglioramento: impostazione minima di larghezza preferita per i pulsanti
        modificaButton.setPrefWidth(80);
        cancellaButton.setPrefWidth(80);
        okButton.setPrefWidth(80);


        this.root = new VBox(10); 
        this.root.setPadding(new Insets(10)); // Padding generale per l'intera vista

        // Configurazione HBox: contiene TableView e Dettaglio (aggiunto nelle sottoclassi)
        this.contentHBox = new HBox(15); // Aumentato lo spazio tra tabella e dettaglio
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
     * @brief Crea il contenitore con i controlli superiori (Cerca, Modifica, Cancella, Inserisci).
     * @param [in] entityName Il nome dell'entità gestita.
     * @return Il VBox contenente i controlli di ricerca e le azioni principali.
     */
    private VBox creaTopControls(String entityName) {
        
        // Prima riga: Cerca
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(new Label("Cerca:"), cercaField);
        HBox.setHgrow(cercaField, Priority.ALWAYS); 
        
        // Seconda riga: Azioni (Modifica, Cancella, ecc.)
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        actionBox.getChildren().addAll(
            modificaButton, 
            cancellaButton,
            inserisciNuovoCampo, 
            okButton
        );
        
        // Contenitore superiore per tutte le azioni
        VBox topContainer = new VBox(5); 
        topContainer.getChildren().addAll(searchBox, actionBox);
        
        return topContainer;
    }
    
    /**
     * @brief Configura i listener principali della vista.
     */
    protected void impostaListener() {
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            impostaValoriDefault(newSelection);
            
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
    }
    

    /** @return Il bottone "Modifica". */
    public Button getModificaButton() { return modificaButton; }
    
    /** @return Il bottone "Cancella". */
    public Button getCancellaButton() { return cancellaButton; }
    
    
    /** @return Il bottone "Conferma". */
    public Button getOkButton() { return okButton; }
    
    /** @return Il campo di testo per la ricerca. */
    public TextField getCercaField() { return cercaField; }
    
    
    /** @return La TableView principale. */
    public TableView<T> getTableView() { return tableView; }
    
    
    
    public Parent getRoot() { 
        return root;
    }
    
    /** @return Il contenitore HBox per affiancare la tabella al pannello di dettaglio. */
    public HBox getContentHBox() { return contentHBox; }
}