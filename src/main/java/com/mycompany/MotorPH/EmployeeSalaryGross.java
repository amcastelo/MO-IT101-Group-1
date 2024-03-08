/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.MotorPH;

import java.text.DecimalFormat;

/**
 *  
 * @author Isaac
 */

public class EmployeeSalaryGross extends Employee  {
    
    private static String targEmpID, empName;
    private static long hours;
    private static double gross, Hourly;
    private static double taxableIncome;
    static DecimalFormat df = new DecimalFormat("#.##");
    
    //CONSTRUCTOR
    public EmployeeSalaryGross(String[] data) {
        super(data); // Call the constructor of the superclass (Employee)
    }
    
    //CALCULATES GROSSWAGE
    public static void calculateGross( String targetEmployeeId, int month) {          
            // Read all rows from the txt file
            // Find the hourly rate for the chosen employee ID
            targEmpID = targetEmployeeId;
            for (Employee employee : EmployeeModelFromFile.employees) {
                if (employee.getEmployeeNumber().equals(targetEmployeeId)) {
                    // Assuming the employee ID is in the first column, and hourly rate is in the last column
                    //setHourlyRate(employee.getHourlyRate());
                    setHourly(employee.getHourlyRate());
                    empName = employee.getFirstName() +" " + employee.getLastName();
                    // Remove commas from the hourly rate string
                    setHourly(getHourly());

                    // Check if the hourly rate string is a valid decimal number
                    if (isValidDecimal(Double.toString(getHourly()))) {
                        double HourlyRate = getHourly();
                        long hour = AttendanceRecord.calculateTotalHoursAndPrint(2022, month, targEmpID);
                        double hoursCalculated = HourlyRate * hour;
                        
                        setHours(hour);
                        setGross(hoursCalculated);
                    } else {
                        System.out.println("Invalid hourly rate for Employee ID " + targetEmployeeId + ": " + getHourly());
                        System.out.println(); // Move to the next line for the next row
                    }                                              
                        
                    return; // Exit the loop once the employee is found
                }                               
            }
            
            // If the loop completes without finding the employee ID
            System.out.println("Employee ID " + targetEmployeeId + " not found.");
       
    }
    
    //PRINTS GROSS WAGE
    public static void printGross(){
        System.out.println("""
                ------------------------------------------           
                Employee ID: %s
                Name: %s
                Hourly Rate: $%.2f
                Total Hours: %s
                Gross Wage: $%s
                ------------------------------------------
                """.formatted(getTargEmpID(), 
                    getEmpName(),
                    getHourly(),
                    getHours(), 
                    df.format(gross)
                ));
    }
    
    //CHECKS IF DECIMAL IN HOURLY RATE IS VALID
    private static boolean isValidDecimal(String str) {
        try {
        Double.parseDouble(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
    

    //GETTER & SETTERS HERE ONWARDS
    /**
     * @return the hourlyRateString
     */
    public static double getHourly() {
        return Hourly;
    }


    /**
     * @return the gross
     */
    public static double getGross() {
        return gross;
    }


    /**
     * @param aGross the gross to set
     */
    public static void setGross(double aGross) {
        gross = aGross;
    }

    /**
     * @return the taxableIncome
     */
    public static double getTaxableIncome() {
        return taxableIncome;
    }

    /**
     * @param aTaxableIncome the taxableIncome to set
     */
    public static void setTaxableIncome(double aTaxableIncome) {
        taxableIncome = aTaxableIncome;
    }

    /**
     * @return the targEmpID
     */
    public static String getTargEmpID() {
        return targEmpID;
    }

    /**
     * @return the empName
     */
    public static String getEmpName() {
        return empName;
    }

    /**
     * @return the hours
     */
    public static long getHours() {
        return hours;
    }

    /**
     * @param aHours the hours to set
     */
    public static void setHours(long aHours) {
        hours = aHours;
    }

    /**
     * @param aHourlyRate the Hourly to set
     */
    public static void setHourly(double aHourly) {
        Hourly = aHourly;
    }

   
      
}
