/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * @class DatiBaseView
 * @brief Classe base astratta per le View che gestiscono i Dati Libri e i Dati  Utenti.
 * @tparam T Il tipo di dato gestito che deve implementare InterfaceID.
 * @see BaseView
 */
public abstract class DatiBaseView<T extends InterfaceID> extends BaseView<T> {


    private final VBox root;
    /** @brief Campo di testo per la ricerca  degli elementi. */
    protected final TextField cercaField;
    /** @brief Bottone per avviare la modifica dell'elemento . */
    protected final Button modificaButton;
    /** @brief Bottone per avviare la cancellazione dell'elemento . */
    protected final Button cancellaButton;
    /** @brief Bottone di conferma  per l' inserimento o la modifica. */
    protected final Button okButton;
    /** @brief Campo di testo per  un nuovo inserimento. */
    protected final TextField inserisciNuovoCampo; 
    /** @brief Label utilizzata per far visualizzare messaggi di stato, errore o conferma all'utente. */
    protected final Label messaggioLabel;
    
    
    /** @brief Tableview serve per la visualizzazione della tabella degli elementi. */
    protected final TableView<T> tableView;

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
        
        
     
        impostaColonneTabella();
        impostaListener();
        
        

    this.root = new VBox(); 
    
    // RIGA CRITICA: Assicurati che creaPaneDettaglio() NON sia più qui (riga ~68)
    // Era: root.getChildren().addAll(creaTopControls(entità), tableView, creaPaneDettaglio());
    
    // CORREZIONE: Aggiungi solo i controlli superiori e la tabella.
    root.getChildren().addAll(creaTopControls(entità), tableView); 
    
    // L'assemblaggio del pannello Dettaglio (che usa i campi della sottoclasse) verrà fatto dopo.
        
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
     * @brief Crea il contenitore con i controlli superiori .
     * @param [in] entityName Il nome dell'entità da mostrare nel titolo.
     * @return Il VBox contenente i controlli di ricerca e le azioni principali altrimenti un valore nullo .
     * @post Vengono restituiti i controlli pronti per l'inserimento.
     */
    private VBox creaTopControls(String entityName) {
        
        Label label = new Label("Gestione " + entityName);
        HBox box = new HBox(
            new Label("Cerca:"), cercaField, 
            modificaButton, cancellaButton,
            inserisciNuovoCampo, okButton
        );
        box.setSpacing(5);
        return new VBox(label, box);
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
    
    
    
    public Parent getRoot() { // <--- NUOVO METODO PUBBLICO
        return root;
    }
    
}
