package com.codewiz.java22;

import java.math.BigInteger;

public class StatementsBeforeSuper {
}




class Person {
    private String name;
    public Person(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

class Employee extends Person {
    private String employeeId;
    public Employee(String name, String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        super(name);
        this.employeeId = employeeId;
    }

    private static String validateEmpId(String employeeId,String name) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}

