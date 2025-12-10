package it.unisa.diem.ingegneriadelsoftware.service;
import it.unisa.diem.ingegneriadelsoftware.model.*;
import it.unisa.diem.ingegneriadelsoftware.repository.InterfaceRepository;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;



public class PrestitoServiceTest {
        
        private PrestitoService prestitoService;
        private InterfaceRepositoryStub<Prestito> stubRepositoryPrestito;
        private LibroServiceStub stubLibroService;
        private UtenteStub utenteCliente;
        private UtenteStub utenteStaff;
        private LibroStub libroDisponibile;
        private LibroStub libroEsaurito;
        private LocalDate dataCorrente;

        @BeforeEach
        void setup() {
            stubRepositoryPrestito = new InterfaceRepositoryStub<>(Prestito.class);
            InterfaceRepositoryStub<Libro> libroRepoStub = new InterfaceRepositoryStub<>(Libro.class); 
            stubLibroService = new LibroServiceStub(libroRepoStub); 
            prestitoService = new PrestitoService(stubRepositoryPrestito, stubLibroService);

            utenteCliente = new UtenteStub("A");
            utenteStaff = new UtenteStub("B");
            libroDisponibile = new LibroStub("Titolo1", "Autore1", 2); 
            libroEsaurito = new LibroStub("Titolo2", "Autore2", 0); 
            
            dataCorrente = LocalDate.now();
            
            stubRepositoryPrestito.salva(new PrestitoStub(utenteCliente, libroDisponibile, dataCorrente.plusDays(7), null));
            stubRepositoryPrestito.salva(new PrestitoStub(utenteStaff, libroDisponibile, dataCorrente.plusDays(10), null));
            stubRepositoryPrestito.salva(new PrestitoStub(utenteCliente, libroEsaurito, dataCorrente.plusDays(3), dataCorrente.minusDays(1)));
        }

        @Test
        void registraPrestito_decrementaCopieELoSalva() {
            LibroStub nuovoLibro = new LibroStub("TestLibro", "TestAutore", 1);
            int copieIniziali = nuovoLibro.getCopieDisponibili();
            
            prestitoService.registraPrestito(utenteCliente, nuovoLibro, dataCorrente.plusDays(14));
            
            List<Prestito> prestiti = stubRepositoryPrestito.findAll();
            assertEquals(4, prestiti.size());
            assertEquals(copieIniziali - 1, nuovoLibro.getCopieDisponibili()); 
        }

        @Test
        void registraPrestito_fallisceSeLibroNonDisponibile() {
            int copiePrecedenti = libroEsaurito.getCopieDisponibili();
            
            assertThrows(RuntimeException.class, () -> { 
                prestitoService.registraPrestito(utenteCliente, libroEsaurito, dataCorrente.plusDays(14));
            });
            
            assertEquals(copiePrecedenti, libroEsaurito.getCopieDisponibili());
            assertEquals(3, stubRepositoryPrestito.findAll().size());
        }

        @Test
        void registraRestituzione_incrementaCopieEChiudePrestito() {
            Prestito prestitoAttivo = stubRepositoryPrestito.cercaGenerico("attivi").get(0);
            Libro libroRilevato = prestitoAttivo.getLibro(); 
            int copiePrecedenti = ((LibroStub) libroRilevato).getCopieDisponibili();
            
            prestitoService.registraRestituzione(prestitoAttivo, dataCorrente);
            
            assertEquals(dataCorrente, prestitoAttivo.getDataEffettiva());
            assertEquals(copiePrecedenti + 1, ((LibroStub) libroRilevato).getCopieDisponibili());
        }

        @Test
        void registraRestituzione_fallisceSePrestitoGiaChiuso() {
            Prestito prestitoChiuso = stubRepositoryPrestito.findAll().get(2);
            LocalDate dataEffettivaIniziale = prestitoChiuso.getDataEffettiva();
            Libro libroRilevato = prestitoChiuso.getLibro();
            int copiePrecedenti = ((LibroStub) libroRilevato).getCopieDisponibili();

            assertThrows(RuntimeException.class, () -> { 
                prestitoService.registraRestituzione(prestitoChiuso, dataCorrente.plusDays(1));
            });
            
            assertEquals(dataEffettivaIniziale, prestitoChiuso.getDataEffettiva());
            assertEquals(copiePrecedenti, ((LibroStub) libroRilevato).getCopieDisponibili());
        }

        @Test
        void listaPrestitiAttivi_restituisceSoloQuelliAperti() {
            List<Prestito> prestitiAperti = prestitoService.listaPrestitiAttivi();
            
            assertNotNull(prestitiAperti);
            assertEquals(2, prestitiAperti.size());
            assertTrue(prestitiAperti.stream().allMatch(p -> p.getDataEffettiva() == null));
        }

        @Test
        void listaPrestitiAttivi_restituisceListaVuotaSeNonCiSonoAttivi() {
            InterfaceRepositoryStub<Prestito> repoVuoto = new InterfaceRepositoryStub<>(Prestito.class);
            PrestitoService serviceVuoto = new PrestitoService(repoVuoto, stubLibroService);
            
            List<Prestito> prestitiAperti = serviceVuoto.listaPrestitiAttivi();
            
            assertNotNull(prestitiAperti);
            assertTrue(prestitiAperti.isEmpty());
        }
    }
