package ru.gogagum.microservices.task2.Dao;

import java.util.Date;

/**
 * Пример сложного объекта для хранения.
 *
 */
public class Student extends HasId {
    private final String hometown;
    private final Date birthDate;
    private final boolean hasDormitory;
    private final double averageScore;

    public Student(String hometown,
                   Date birthDate, boolean hasDormitory, double averageScore) {
        this.hometown = hometown;
        this.birthDate = birthDate;
        this.hasDormitory = hasDormitory;
        this.averageScore = averageScore;
    }

    public String getHometown() {
        return hometown;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isHasDormitory() {
        return hasDormitory;
    }

    public double getAverageScore() {
        return averageScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ru.mipt1c.homework.tests.task1.Student)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ru.mipt1c.homework.tests.task1.Student student = (ru.mipt1c.homework.tests.task1.Student) o;

        if (isHasDormitory() != student.isHasDormitory()) {
            return false;
        }
        if (Double.compare(student.getAverageScore(), getAverageScore()) != 0) {
            return false;
        }
        if (getHometown() != null ? !getHometown().equals(student.getHometown()) : student.getHometown() != null) {
            return false;
        }
        return getBirthDate() != null ? getBirthDate().equals(student.getBirthDate()) : student.getBirthDate() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (getHometown() != null ? getHometown().hashCode() : 0);
        result = 31 * result + (getBirthDate() != null ? getBirthDate().hashCode() : 0);
        result = 31 * result + (isHasDormitory() ? 1 : 0);
        temp = Double.doubleToLongBits(getAverageScore());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


}