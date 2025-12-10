/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;

import java.time.LocalDate;

public class PrestitoStub extends Prestito {
    
    public PrestitoStub(Utente utente, Libro libro, LocalDate dataPrevista, LocalDate dataEffettiva) {
        super(utente, libro, dataPrevista);
        if (dataEffettiva != null) {
            super.registraRestituzione(dataEffettiva);
        }
    }
}

