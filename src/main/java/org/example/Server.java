package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import lombok.extern.java.Log;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log
public class Server {
    public static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("sensors_pu");

    /**
     * The constant BASE_URI.
     */
// Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:9998/minijparest";

    /**
     * Main method.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("Adding data in the database");

        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(Maison.builder().name("m1").surface(100).build());
            em.persist(Maison.builder().name("m2").surface(200).build());
            em.persist(Maison.builder().name("m3").surface(75)
                    .vetuste(Maison.Vetuste.ANCIEN).build());

            TypedQuery<Maison> q = em.createNamedQuery("Maison.findAll", Maison.class);

            log.info(q.getResultStream().map(Maison::toString).collect(Collectors.joining(System.getProperty("line.separator"))));

            em.getTransaction().commit();
        }


        log.info("Rest server starting..." + BASE_URI);
        final HttpServer server = startServer();

        //The server will be shutdown at the end of the program
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        log.info(String.format("Application started.%n" +
                               "Stop the application using CTRL+C"));

        //We wait an infinite time.
        Thread.currentThread().join();
        server.shutdown();
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in demos package and add a logging feature to the server.
        Logger logger = Logger.getLogger(Server.class.getName());
        logger.setLevel(Level.FINE);

        final ResourceConfig rc = new ResourceConfig()
                .packages(true, "org.example")
                .register(new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, null));

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

}
