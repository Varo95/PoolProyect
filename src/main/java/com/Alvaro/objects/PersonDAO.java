package com.Alvaro.objects;

import com.Alvaro.utils.PersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public synchronized static void persist(Person p){
        EntityManager em = PersistenceUnit.createEM();
        em.getTransaction().begin();
        Person a = em.merge(p);
        em.persist(a);
        em.getTransaction().commit();
        PersistenceUnit.closeEM();
    }

    public synchronized static void remove(Person p){
        EntityManager em = PersistenceUnit.createEM();
        em.getTransaction().begin();
        Person a = em.merge(p);
        em.remove(a);
        System.out.println(a.getId()+a.getName1()+a.getWeight());
        em.getTransaction().commit();
        PersistenceUnit.closeEM();
    }

    public static List<Person> listAllUnbathing() {
        EntityManager em = PersistenceUnit.createEM();
        em.getTransaction().begin();
        TypedQuery<Person> q = em.createNamedQuery("listAllUnBathing", Person.class);
        List<Person> result = new ArrayList<>(q.getResultList());
        em.getTransaction().commit();
        PersistenceUnit.closeEM();
        return result;
    }

    public static List<Person> listAllBathing() {
        EntityManager em = PersistenceUnit.createEM();
        em.getTransaction().begin();
        TypedQuery<Person> q = em.createNamedQuery("listAllBathing", Person.class);
        List<Person> result = new ArrayList<>(q.getResultList());
        em.getTransaction().commit();
        PersistenceUnit.closeEM();
        return result;
    }
}
