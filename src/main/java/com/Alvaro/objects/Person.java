package com.Alvaro.objects;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

@Entity
@Table(name = "PoolPeople")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "listAllUnBathing", query = "SELECT a FROM Person a WHERE a.inPool=false"),
        @NamedQuery(name = "listAllBathing", query = "SELECT a FROM Person a WHERE a.inPool=true")
})
public class Person extends Thread implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private int weight;
    @Transient
    private final Pool pool;
    @Column(name = "inPool")
    private boolean inPool;

    public Person(String name, Pool p, int weight) {
        this.id = -1;
        this.name = name;
        this.pool = p;
        this.weight = weight;
        this.inPool = false;
    }

    public Person() {
        this("", null, -1);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName1() {
        return this.name;
    }

    public void setName1(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setInPool(boolean inPool) {
        this.inPool = inPool;
    }

    public boolean isInPool() {
        return this.inPool;
    }

    @Transient
    @Override
    public void run() {
        while (!inPool) {
            synchronized (this.pool) {
                inPool = this.pool.authorizedBath(this);
                if (!inPool) {
                    try {
                        this.pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            long bathTime = 1000 * 10 + new Random().nextInt(50 - 10 + 1);
            Thread.sleep(bathTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this.pool) {
            this.pool.endBath(this);
            pool.notifyAll();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        if (weight != person.weight) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

}
