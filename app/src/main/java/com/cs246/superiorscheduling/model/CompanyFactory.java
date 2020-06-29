package com.cs246.superiorscheduling.model;
// TODO: Is Class Necessary?
public class CompanyFactory {

    public static Company createCompany(String companyName, User manager){
        Company company = new Company(companyName, manager);
        company.setName(companyName);
        manager.addCompany(company);
        return company;
    }
}
