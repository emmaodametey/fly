package org.example.services;

import org.example.employees.Employees;

public class AuthServ implements  AuthorisingService{

    @Override
    public boolean isAuthorised(String user) {
        return Employees.getEntitlement(user);}
}
