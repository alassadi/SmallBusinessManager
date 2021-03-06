package com.projectcourse2.group11.smallbusinessmanager.model;

import java.util.ArrayList;

public class Accountant extends Person {
    private Employee[] employees;
    private double availableMoney;

    public Accountant(){}
    public Accountant(String ssn, String firstName, String lastName, String phoneNumber, String email, String UID ,Employee[] employees, double availableMoney) {
        super(ssn, firstName, lastName, phoneNumber, email, UID);
        setPosition(Position.ACCOUNTANT);
        this.employees=employees;
        this.availableMoney=availableMoney;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void viewExpenses(){
        //to do
    }
    public void viewIncome(){
        //to do
    }
    public void approvePayment(Expenses expense){
        expense.setApporoved();
    }
    public void reviewContract(){
        //to do
    }
    public void viewProject(){
        //to do
    }
}
