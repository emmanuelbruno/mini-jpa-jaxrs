package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MaisonDAO implements AutoCloseable {
    private final EntityManager entityManager = Server.entityManagerFactory.createEntityManager();

    public Maison find(long id) {
        return entityManager.find(Maison.class, id);
    }

    public List<Maison> findAll() {
        return findByNamedQuery("Maison.findAll");
    }

    public List<Maison> findByNamedQuery(String namedQuery) {
        List<Maison> result;
        try (EntityManagerFactory entityManagerFactory =
                     Persistence.createEntityManagerFactory("sensors_pu");
             EntityManager em = entityManagerFactory.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Maison> q = em.createNamedQuery(namedQuery, Maison.class);
            em.getTransaction().commit();
            result = q.getResultList();
        }
        return result;
    }

    public Maison persist(Maison maison) {
        entityManager.getTransaction().begin();
        entityManager.persist(maison);
        entityManager.getTransaction().commit();
        return maison;
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
    }

    public List<Maison> findAllByVetuste(Maison.Vetuste vetuste) {
        List<Maison> result;
        try (EntityManagerFactory entityManagerFactory =
                     Persistence.createEntityManagerFactory("sensors_pu");
             EntityManager em = entityManagerFactory.createEntityManager()) {

            em.getTransaction().begin();
            TypedQuery<Maison> q = em.createNamedQuery("Maison.findByVetuste", Maison.class);
            q.setParameter("vetuste", vetuste);
            em.getTransaction().commit();
            result = q.getResultList();
        }
        return result;
    }
}
