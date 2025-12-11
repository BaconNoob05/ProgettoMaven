/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.InterfaceID;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * @class DatiBaseView
 * @brief Classe base astratta le View che gestiscono i Dati (Libri, Utenti).
 * @details Estende BaseView e fornisce i componenti standard dell'interfaccia (ricerca, bottoni di controllo, label messaggio).
 */
public abstract class DatiBaseView<T extends InterfaceID> extends BaseView<T> {


    protected final TextField cercaField;
    protected final Button modificaButton;
    protected final Button cancellaButton;
    protected final Button okButton;
    protected final TextField inserisciNuovoCampo; 
    protected final Label messaggioLabel;
    
    
    protected final TableView<T> tableView;

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
        
        
        VBox root = new VBox();
        root.getChildren().addAll(creaTopControls(entità), tableView, creaPaneDettaglio());
        
    }


    protected abstract void impostaColonneTabella();
    
    

    protected abstract void impostaValoriDefault(T elemento);
    

    
    protected abstract GridPane creaPaneDettaglio();



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
    
    protected void impostaListener() {
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            impostaValoriDefault(newSelection);
            
        });
    }

    @Override
    public T getElementoSelezionato() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public String getCampoCerca() {
        return cercaField.getText();
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        messaggioLabel.setText(messaggio);
    }
    

    public Button getModificaButton() { return modificaButton; }
    public Button getCancellaButton() { return cancellaButton; }
    public Button getOkButton() { return okButton; }
    public TextField getCercaField() { return cercaField; }
    public TableView<T> getTableView() { return tableView; }
}