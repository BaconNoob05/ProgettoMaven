package it.unisa.diem.ingegneriadelsoftware;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @class Main
 * @brief Classe principale per l'avvio dell'applicazione Biblioteca.
 * @details Si occupa di inizializzare l'architettura MVC,
 * configurando i repository, i service e le viste, per poi avviare l'interfaccia grafica JavaFX.
 */
public class Main extends Application {

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * @details Costruisce l'intera gerarchia delle dipendenze:
     * Repository, Service, View e Controller.
     * Infine, assembla la scena principale e la mostra a video.
     * @param [in] primaryStage Lo stage principale fornito da JavaFX.
     * @pre Il sistema deve avere i permessi di lettura/scrittura sui file dati.
     * @post L'applicazione grafica viene mostrata all'utente.
     */
    @Override
    public void start(Stage primaryStage) { }

    /**
     * @brief Metodo principale di ingresso .
     * @param [in] args Argomenti da riga di comando.
     * @post L'applicazione viene lanciata.
     */
    public static void main(String[] args) { }
}
