/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import java.util.Collection;

/**
 *
 * @author HASANKA
 */
public class beanFactory {

    public static Collection generateCollection(String empn, String empname, String desigantion, String month, String basic, String normalOt, String doubleOt, String allowance, String attendanceAllowance, String grossSalary, String nonCashBenefits, String medicalSceme, String taxableSalary, String payeTax, String providentFund, String listDedPer, String listDedval, String festDed, String overPayment, String salaryAdvance, String bicyceLoans, String netSalary, String epfPer, String epfVal, String etfPer, String etfVal) {

        java.util.Vector collection = new java.util.Vector();

        collection.add(new SalarySheet(empn, empname,
                desigantion, month, basic,
                normalOt, doubleOt, allowance,
                attendanceAllowance, grossSalary,
                nonCashBenefits, medicalSceme,
                taxableSalary, payeTax,
                providentFund, listDedPer,
                listDedval, festDed,
                overPayment, salaryAdvance,
                bicyceLoans, netSalary,
                epfPer, epfVal,
                etfPer, etfVal));

        return collection;
    }
}
