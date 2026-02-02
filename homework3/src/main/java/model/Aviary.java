package model;

import animals.Animal;

import java.util.HashMap;

public class Aviary<T extends Animal> {
    private Size size;
    private HashMap<String, T> aviaryMap = new HashMap<>();

    public Aviary(Size size) {
        this.size = size;
    }

    public void addAnimal(T animal) {
        if (size == animal.getSize()) {
            aviaryMap.put(animal.getName(), animal);
            System.out.println("В вольер добавлен " + animal.getName());
        } else {
            throw new WrongSizeException("Вольер не подходит по размеру");
        }
    }

    public boolean removeAnimal(String animalName) {
        return aviaryMap.remove(animalName) != null;
    }

    public T getAnimal(String animalName) {
        return aviaryMap.get(animalName);
    }
}
