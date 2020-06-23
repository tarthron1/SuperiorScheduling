package com.cs246.superiorscheduling.model;

public class CompanyFactory {

    public static Company createCompany(String companyName, User manager){
        Company company = new Company(companyName, manager);
        company.setName(companyName);
        manager.addCompany(company);
        return company;
    }
}
