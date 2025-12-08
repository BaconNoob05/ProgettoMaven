package it.unisa.diem.ingegneriadelsoftware.view;
import javafx.scene.control.*;
import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import java.time.LocalDate;

/**
 * @class PrestitoView
 * @brief Permette la visualizzazione e l'inserimento dei dati a video all'utente per Prestiti.
 * @see BaseView
 */
public class PrestitoView extends BaseView<Prestito> {

   
    /**
     * @brief Recupera i dati inseriti per la creazione di un nuovo prestito.
     * @return Un oggetto Prestito contenente i dati di ingresso, altrimenti restituisce un valore nullo.
     * @pre I campi Matricola e ISBN devono essere compilati.
     */
    public Prestito getPrestitoNuovo() { 
    
        return null;
    }

    /**
     * @brief Ottiene la data di restituzione selezionata dall'utente.
     * @return La data selezionata, altrimenti restituisce un valore nullo.
     * @pre Il componente DatePicker deve essere inizializzato.
     * @post Lo stato del DatePicker rimane invariato.
     */
    public LocalDate getDataRestituzione() { 
    
        return null;
    }

    
}