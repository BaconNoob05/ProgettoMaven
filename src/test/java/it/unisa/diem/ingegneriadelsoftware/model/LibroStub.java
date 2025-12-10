/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.ingegneriadelsoftware.model;

import java.util.Arrays;

class LibroStub extends Libro {

    public LibroStub() {
        super("Fondamenti di Programmazione", Arrays.asList("Francesco Totti"), 2023, "978-8812345678", 1);
    }

    @Override
    public String getId() {
        return "LIBROSTUB";
    }
}

