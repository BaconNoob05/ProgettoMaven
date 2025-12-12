package it.unisa.diem.ingegneriadelsoftware.view;


import it.unisa.diem.ingegneriadelsoftware.model.Prestito;
import java.util.List;
import java.time.LocalDate;

public class PrestitoViewStub extends PrestitoView {

       
        private Prestito prestitoNuovo;
        private Prestito elementoSelezionato;
        private LocalDate dataRestituzione;

        
        public List<Prestito> listaRicevuta;
        public String ultimoMessaggio;

        public PrestitoViewStub() { 
            super(); 
        }

        @Override
        public void mostraLista(List<Prestito> lista) {
            this.listaRicevuta = lista;
        }

        @Override
        public void mostraMessaggio(String messaggio) {
            this.ultimoMessaggio = messaggio;
        }

        @Override
        public Prestito getPrestitoNuovo() { 
            return prestitoNuovo; 
        }

        @Override
        public Prestito getElementoSelezionato() { 
            return elementoSelezionato; 
        }

        @Override
        public LocalDate getDataRestituzione() { return 
            dataRestituzione; 
        }

        // Setter per configurare i test
        public void setPrestitoNuovo(Prestito p) { this.prestitoNuovo = p; }
        public void setElementoSelezionato(Prestito p) { this.elementoSelezionato = p; }
        public void setDataRestituzione(LocalDate d) { this.dataRestituzione = d; }
    }