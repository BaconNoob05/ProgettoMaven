package it.unisa.diem.ingegneriadelsoftware.view;

import it.unisa.diem.ingegneriadelsoftware.model.Utente;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.List;

/**
 * @class UtenteView
 * @brief Permette la modifica e l'inserimento dei dati a video all'amministratore per l'entità Utente.
 * @see CrudViewBase
 */
public class UtenteView extends DatiBaseView<Utente> {


    
    
    private final TextField nomeInput;
    private final TextField cognomeInput;
    private final TextField matricolaInput; 
    private final TextField emailInput;
    
    
    public UtenteView() {
        
        
        super("Utente"); 
        

        this.nomeInput = new TextField();
        this.cognomeInput = new TextField();
        this.matricolaInput = new TextField(); 
        this.emailInput = new TextField();
    }

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

    @Override
    protected GridPane creaPaneDettaglio() {
        
        
        
        GridPane detailPane = new GridPane();
        
        detailPane.setHgap(10);
        detailPane.setVgap(10);
        detailPane.add(new Label("Dettagli Utente"), 0, 0, 2, 1);
        
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