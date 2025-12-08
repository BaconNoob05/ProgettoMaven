package it.unisa.diem.ingegneriadelsoftware.view;

import javafx.scene.control.*;
import com.tuoprogetto.biblioteca.model.Utente;

/**
 * @class UtenteView
 * @brief Permette la modifica e l'inserimento dei dati a video all'amministratore per l'entità Utente.
 * @see BaseView
 */
public class UtenteView {




public class UtenteView extends BaseView<Utente> {

    

    /**
     * @brief Visualizza i dati inseriti nel form per la creazione di un nuovo utente.
     * @return Un oggetto Utente popolato con i dati del form, altrimenti restituisce un valore nullo.
     * @pre I campi obbligatori devono essere popolati.
     */
    public Utente getUtenteNuovo() { }

    /**
     * @brief Visualizza i dati modificati per aggiornare un utente esistente.
     * @return L'oggetto Utente con i dati aggiornati, altrimenti restituisce un valore nullo.
     * @pre Un utente deve essere stato precedentemente selezionato dalla tabella.
     * @post Viene restituita l'istanza aggiornata.
     */
    public Utente getUtenteModificato() { }

   
}



}
