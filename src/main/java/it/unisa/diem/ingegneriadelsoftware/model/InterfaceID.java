package it.unisa.diem.ingegneriadelsoftware.model;

/**
 * @class InterfaceID
 * @brief Interfaccia funzionale per l'identificazione univoca delle diverse entità del sistema.
 */
public interface InterfaceID {

    /**
     * @brief Restituisce l'identificativo univoco dell'entità.
     * @return Una stringa che rappresenta l'identificativo univoco dell'oggetto altrimenti restituisce un valore nullo.
     */
    String getId();
    
}
