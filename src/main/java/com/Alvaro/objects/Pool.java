package com.Alvaro.objects;

import java.util.ArrayList;
import java.util.List;

public class Pool {

    private static final int MAX_WEIGHT = 300;
    private static final int MAX_PEOPLE = 4;
    private int weight = 0;
    public final List<Person> bathingPeople = new ArrayList<>();
    public final List<Person> onGrass = new ArrayList<>();

    public synchronized boolean authorizedBath(Person person) {
        boolean result;
        if (this.weight + person.getWeight() <= Pool.MAX_WEIGHT && this.bathingPeople.size() < Pool.MAX_PEOPLE) {
            this.onGrass.remove(person);
            person.setInPool(true);
            this.bathingPeople.add(person);
            this.weight += person.getWeight();
            result = true;
        } else {
            person.setInPool(false);
            this.onGrass.add(person);
            this.bathingPeople.remove(person);
            result = false;
        }
        return result;
    }

    public synchronized void endBath(Person person) {
        this.weight -= person.getWeight();
        this.bathingPeople.remove(person);
    }
}
