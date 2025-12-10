/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;

import java.io.Serializable;

/**
 *
 * @author danie
 */
public class DatiStub implements InterfaceID, Serializable{
    private String id;
    
    public DatiStub(String id){
        this.id=id;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Test{" + "id='" + id + '\'' + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((DatiStub) o).id);
    }
    
}
