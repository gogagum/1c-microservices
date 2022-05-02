package ru.gogagum.microservices.task2.Dao;

import com.sun.istack.NotNull;

public class StudentKey implements Comparable<StudentKey> {
    private final int groupId;
    private final String name;

    public StudentKey(int groupId, String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(StudentKey other) {
        if (other == null) {
            throw new IllegalArgumentException("Compare with null");
        }

        int compareGroupId = Integer.compare(this.groupId, other.groupId);

        if (compareGroupId != 0) {
            return compareGroupId;
        } else {
            return this.name.compareTo(other.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ru.mipt1c.homework.tests.task1.StudentKey)) {
            return false;
        }

        ru.mipt1c.homework.tests.task1.StudentKey that = (ru.mipt1c.homework.tests.task1.StudentKey) o;

        if (getGroupId() != that.getGroupId()) {
            return false;
        }
        return getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = getGroupId();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "g" + getGroupId() + "-" + getName();
    }
}
