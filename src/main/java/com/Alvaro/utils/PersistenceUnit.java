package com.Alvaro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class PersistenceUnit {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceUnit.class);
    private static EntityManagerFactory emf = null;
    private static EntityManager manager = null;

    /**
     * Solo se llama una vez para conectar con la base de datos
     */
    public static void init() {
        try {
            emf = Persistence.createEntityManagerFactory("ApplicationH2");
        } catch (PersistenceException e) {
            logger.error("Sin nombre de proveedor de persistencia para EntityManager con H2");
        }
    }

    /**
     * Este método es para crear unidades de persistencia
     *
     * @return entitymanager para la unidad de persistencia
     */
    public static EntityManager createEM() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("ApplicationH2");
            } catch (PersistenceException e) {
                logger.error("Sin nombre de proveedor de persistencia para EntityManager con H2");
            }
        }
        if (manager == null)
            manager = emf.createEntityManager();
        return manager;
    }

    /**
     * Cerramos la conexión con la base de datos
     */
    private static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }

    /**
     * Cerramos el EntityManager
     */
    public static void closeEM() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }

    /**
     * Este método sólo se llama una vez al final del programa
     */
    public static void closeAllConnections() {
        closeEM();
        closeEntityManagerFactory();
    }
}

