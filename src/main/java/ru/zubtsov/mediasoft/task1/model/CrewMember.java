package ru.zubtsov.mediasoft.task1.model;

import java.util.Objects;

public class CrewMember {

    private final long id;
    private final String name;
    private final CrewRole role;

    public CrewMember(long id, String name, CrewRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CrewRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewMember that = (CrewMember) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
