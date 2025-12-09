package it.unisa.diem.ingegneriadelsoftware.view;

import javafx.scene.control.*;
import it.unisa.diem.ingegneriadelsoftware.model.Libro;

/**
 * @class LibroView
 * @brief Permette la modifica e l'inserimento dei dati a video all'utente per i Libri.
 * @see BaseView
 */
public class LibroView extends BaseView<Libro> {

    /**
     * @brief Recupera i dati inseriti nel form per creare un nuovo libro.
     * @return Un nuovo oggetto Libro popolato con i dati del form, altrimenti restituisce un valore nullo.
     * @pre I campi devono contenere dati validi.
     */
    public Libro getLibroNuovo() {
    
        return null;
    
    }

    /**
     * @brief Recupera i dati modificati dal form per aggiornare un libro esistente.
     * @return L'oggetto Libro con i dati aggiornati, altrimenti restituisce un valore nullo.
     * @pre Un libro deve essere stato precedentemente selezionato o caricato nel form.
     * @post Viene restituito l'oggetto pronto per essere passato al controller.
     */
    public Libro getLibroModificato() {
    
    
        return null;
    }

}