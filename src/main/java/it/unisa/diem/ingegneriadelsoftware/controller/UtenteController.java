
package it.unisa.diem.ingegneriadelsoftware.controller;

import it.unisa.diem.ingegneriadelsoftware.view.UtenteView;
import it.unisa.diem.ingegneriadelsoftware.service.UtenteService;
import it.unisa.diem.ingegneriadelsoftware.model.Utente;


/**
 * @class UtenteController
 * @brief Controller specifico per la gestione degli Utenti.
 * @details Estende CrudController per gestire il ciclo di vita degli utenti.
 *
 * @see CrudController
 * @see UtenteView
 */
public class UtenteController extends CrudController<Utente> {

    /**
     * @brief Costruttore.
     * @param view La vista specifica utenti.
     * @param service Il servizio specifico utenti.
     */
    public UtenteController(UtenteView view, UtenteService service){
        super(view,service);
    }

    /**
     * @brief  Interpreta la vista come un'istanza di UtenteView.
     * @return L'istanza di UtenteView associata al controller altrimenti un valore nullo.
     */
    private UtenteView getSpecificView() {
        return (UtenteView) view;
    }
    
    /**
     * @brief Gestisce l'acquisizione dati e il salvataggio di un nuovo utente.
     * @pre I campi obbligatori devono essere compilati.
     * @post L'utente viene salvato nel sistema.
     * @see UtenteView#getUtenteNuovo()
     */
    public void salvaUtente(){
        Utente nuovo = getSpecificView().getUtenteNuovo();
        if (nuovo == null) return; 

        salva(nuovo);
    }

    /**
     * @brief Gestisce l'acquisizione dati e l'aggiornamento di un utente.
     * @see UtenteView#getUtenteModificato()
     */
    public void modificaUtente(){
        Utente modificato = getSpecificView().getUtenteModificato();
        if (modificato == null) return;

        modifica(modificato);
    }

    /**
     * @brief Implementazione del salvataggio utente.
     * @param nuovo L'utente da salvare.
     */
    @Override
    public void salva(Utente nuovo){
        eseguiOperazione(() -> service.salva(nuovo), "Utente inserito correttamente.");
    }

    /**
     * @brief Implementazione  della modifica utente.
     * @param elemento L'utente aggiornato.
     */
    @Override
    public void modifica(Utente elemento){
        eseguiOperazione(() -> service.modifica(elemento), "Dati utente aggiornati correttamente.");
    }
}
