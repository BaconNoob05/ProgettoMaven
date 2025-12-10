/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;

import java.util.Arrays;
import java.util.List;

public class LibroStub extends Libro {

    public LibroStub(String titolo, List<String> autori, int anno, String isbn, int copie) {
        super(titolo, autori, anno, isbn, copie);
    }

    @Override
    public String getId() {
        return "LIBROSTUB";
    }
    
}

