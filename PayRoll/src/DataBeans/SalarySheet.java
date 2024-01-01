/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataBeans;

/**
 *
 * @author HASANKA
 */
public class SalarySheet {
    private String empn;
    private String empname;
    private String desigantion;
    private String month;
    private String basic;
    private String normalOt;
    private String doubleOt;
    private String allowance;
    private String attendanceAllowance;
    private String grossSalary;
    private String nonCashBenefits;
    private String medicalSceme;
    private String taxableSalary;
    private String payeTax;
    private String providentFund;
    private String listDedPer;
    private String listDedval;
    private String festDed;
    private String overPayment;
    private String salaryAdvance;
    private String bicyceLoans;
    private String netSalary;
    private String epfPer;
    private String epfVal;
    private String etfPer;
    private String etfVal;

    public SalarySheet(String empn, String empname, String desigantion, String month, String basic, String normalOt, String doubleOt, String allowance, String attendanceAllowance, String grossSalary, String nonCashBenefits, String medicalSceme, String taxableSalary, String payeTax, String providentFund, String listDedPer, String listDedval, String festDed, String overPayment, String salaryAdvance, String bicyceLoans, String netSalary, String epfPer, String epfVal, String etfPer, String etfVal) {
        this.empn = empn;
        this.empname = empname;
        this.desigantion = desigantion;
        this.month = month;
        this.basic = basic;
        this.normalOt = normalOt;
        this.doubleOt = doubleOt;
        this.allowance = allowance;
        this.attendanceAllowance = attendanceAllowance;
        this.grossSalary = grossSalary;
        this.nonCashBenefits = nonCashBenefits;
        this.medicalSceme = medicalSceme;
        this.taxableSalary = taxableSalary;
        this.payeTax = payeTax;
        this.providentFund = providentFund;
        this.listDedPer = listDedPer;
        this.listDedval = listDedval;
        this.festDed = festDed;
        this.overPayment = overPayment;
        this.salaryAdvance = salaryAdvance;
        this.bicyceLoans = bicyceLoans;
        this.netSalary = netSalary;
        this.epfPer = epfPer;
        this.epfVal = epfVal;
        this.etfPer = etfPer;
        this.etfVal = etfVal;
    }
    
    

    /**
     * @return the empn
     */
    public String getEmpn() {
        return empn;
    }

    /**
     * @param empn the empn to set
     */
    public void setEmpn(String empn) {
        this.empn = empn;
    }

    /**
     * @return the empname
     */
    public String getEmpname() {
        return empname;
    }

    /**
     * @param empname the empname to set
     */
    public void setEmpname(String empname) {
        this.empname = empname;
    }

    /**
     * @return the desigantion
     */
    public String getDesigantion() {
        return desigantion;
    }

    /**
     * @param desigantion the desigantion to set
     */
    public void setDesigantion(String desigantion) {
        this.desigantion = desigantion;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the basic
     */
    public String getBasic() {
        return basic;
    }

    /**
     * @param basic the basic to set
     */
    public void setBasic(String basic) {
        this.basic = basic;
    }

    /**
     * @return the normalOt
     */
    public String getNormalOt() {
        return normalOt;
    }

    /**
     * @param normalOt the normalOt to set
     */
    public void setNormalOt(String normalOt) {
        this.normalOt = normalOt;
    }

    /**
     * @return the doubleOt
     */
    public String getDoubleOt() {
        return doubleOt;
    }

    /**
     * @param doubleOt the doubleOt to set
     */
    public void setDoubleOt(String doubleOt) {
        this.doubleOt = doubleOt;
    }

    /**
     * @return the allowance
     */
    public String getAllowance() {
        return allowance;
    }

    /**
     * @param allowance the allowance to set
     */
    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    /**
     * @return the attendanceAllowance
     */
    public String getAttendanceAllowance() {
        return attendanceAllowance;
    }

    /**
     * @param attendanceAllowance the attendanceAllowance to set
     */
    public void setAttendanceAllowance(String attendanceAllowance) {
        this.attendanceAllowance = attendanceAllowance;
    }

    /**
     * @return the grossSalary
     */
    public String getGrossSalary() {
        return grossSalary;
    }

    /**
     * @param grossSalary the grossSalary to set
     */
    public void setGrossSalary(String grossSalary) {
        this.grossSalary = grossSalary;
    }

    /**
     * @return the nonCashBenefits
     */
    public String getNonCashBenefits() {
        return nonCashBenefits;
    }

    /**
     * @param nonCashBenefits the nonCashBenefits to set
     */
    public void setNonCashBenefits(String nonCashBenefits) {
        this.nonCashBenefits = nonCashBenefits;
    }

    /**
     * @return the medicalSceme
     */
    public String getMedicalSceme() {
        return medicalSceme;
    }

    /**
     * @param medicalSceme the medicalSceme to set
     */
    public void setMedicalSceme(String medicalSceme) {
        this.medicalSceme = medicalSceme;
    }

    /**
     * @return the taxableSalary
     */
    public String getTaxableSalary() {
        return taxableSalary;
    }

    /**
     * @param taxableSalary the taxableSalary to set
     */
    public void setTaxableSalary(String taxableSalary) {
        this.taxableSalary = taxableSalary;
    }

    /**
     * @return the payeTax
     */
    public String getPayeTax() {
        return payeTax;
    }

    /**
     * @param payeTax the payeTax to set
     */
    public void setPayeTax(String payeTax) {
        this.payeTax = payeTax;
    }

    /**
     * @return the providentFund
     */
    public String getProvidentFund() {
        return providentFund;
    }

    /**
     * @param providentFund the providentFund to set
     */
    public void setProvidentFund(String providentFund) {
        this.providentFund = providentFund;
    }

    /**
     * @return the listDedPer
     */
    public String getListDedPer() {
        return listDedPer;
    }

    /**
     * @param listDedPer the listDedPer to set
     */
    public void setListDedPer(String listDedPer) {
        this.listDedPer = listDedPer;
    }

    /**
     * @return the listDedval
     */
    public String getListDedval() {
        return listDedval;
    }

    /**
     * @param listDedval the listDedval to set
     */
    public void setListDedval(String listDedval) {
        this.listDedval = listDedval;
    }

    /**
     * @return the festDed
     */
    public String getFestDed() {
        return festDed;
    }

    /**
     * @param festDed the festDed to set
     */
    public void setFestDed(String festDed) {
        this.festDed = festDed;
    }

    /**
     * @return the overPayment
     */
    public String getOverPayment() {
        return overPayment;
    }

    /**
     * @param overPayment the overPayment to set
     */
    public void setOverPayment(String overPayment) {
        this.overPayment = overPayment;
    }

    /**
     * @return the salaryAdvance
     */
    public String getSalaryAdvance() {
        return salaryAdvance;
    }

    /**
     * @param salaryAdvance the salaryAdvance to set
     */
    public void setSalaryAdvance(String salaryAdvance) {
        this.salaryAdvance = salaryAdvance;
    }

    /**
     * @return the bicyceLoans
     */
    public String getBicyceLoans() {
        return bicyceLoans;
    }

    /**
     * @param bicyceLoans the bicyceLoans to set
     */
    public void setBicyceLoans(String bicyceLoans) {
        this.bicyceLoans = bicyceLoans;
    }

    /**
     * @return the netSalary
     */
    public String getNetSalary() {
        return netSalary;
    }

    /**
     * @param netSalary the netSalary to set
     */
    public void setNetSalary(String netSalary) {
        this.netSalary = netSalary;
    }

    /**
     * @return the epfPer
     */
    public String getEpfPer() {
        return epfPer;
    }

    /**
     * @param epfPer the epfPer to set
     */
    public void setEpfPer(String epfPer) {
        this.epfPer = epfPer;
    }

    /**
     * @return the epfVal
     */
    public String getEpfVal() {
        return epfVal;
    }

    /**
     * @param epfVal the epfVal to set
     */
    public void setEpfVal(String epfVal) {
        this.epfVal = epfVal;
    }

    /**
     * @return the etfPer
     */
    public String getEtfPer() {
        return etfPer;
    }

    /**
     * @param etfPer the etfPer to set
     */
    public void setEtfPer(String etfPer) {
        this.etfPer = etfPer;
    }

    /**
     * @return the etfVal
     */
    public String getEtfVal() {
        return etfVal;
    }

    /**
     * @param etfVal the etfVal to set
     */
    public void setEtfVal(String etfVal) {
        this.etfVal = etfVal;
    }
}
