
package it.unisa.diem.ingegneriadelsoftware.model;

import java.io.Serializable;

/**
 * @class DatiStub
 * @brief Creare un oggetto finto che simula il comportamento di Dati.
 */
public class DatiStub implements InterfaceID, Serializable{
    /**
     * @brief Identificativo dell'oggetto Stub.
     */
    private String id;
    /**
     * @brief Costruttore della classe DatiStub.
     */
    public DatiStub(String id){
        this.id=id;
    }
   /**
     * @brief Restituisce l'identificativo dell'oggetto Stub.
     * @see InterfaceID#getId()
     */
    @Override
    public String getId() {
        return id;
    }
    /**
     * @brief Restituisce una rappresentazione con una stringa dell'oggetto Stub.
     */
    @Override
    public String toString() {
        return "Test{" + "id='" + id + '\'' + '}';
    }
    /**
     * @brief Confronta l'oggetto Stub con un altro oggetto.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((DatiStub) o).id);
    }
    
}
