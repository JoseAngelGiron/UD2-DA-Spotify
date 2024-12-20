package com.github.JoseAngelGiron.model.entity;

import java.util.Objects;

public abstract class SpotifyElement {
    protected int id;
    protected String name;

    public SpotifyElement(){

    }

    public SpotifyElement(int id) {
        this.id = id;
    }

    public SpotifyElement(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpotifyElement(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifyElement that = (SpotifyElement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpotifyElement{" +
                "name='" + name + '\'' +
                '}';
    }
}
