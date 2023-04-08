package org.example.employees;

import java.util.HashMap;
import java.util.Map;

public class Employees {
    private String name;
    private  boolean entitlement;
    private static Map<String, Boolean> employees = new HashMap<>();

    public Employees(){
    }
    public void addEmployee(String name, Boolean entitlement){
    employees.put(name, entitlement);
    }

    public boolean isEntitlement() {
        return entitlement;
    }

    public void setEntitlement(boolean entitlement) {
        this.entitlement = entitlement;
    }

    public static boolean getEntitlement(String employee){
        return employees.get(employee);
    }
}


