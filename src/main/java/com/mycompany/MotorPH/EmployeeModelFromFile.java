/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.MotorPH;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isaac
 */
    public class EmployeeModelFromFile extends EmployeeModel{

        private static final String TXT_FILE_PATH = "D:\\Documents\\MotorPH\\src\\main\\resources\\Data.txt";
        public static final List<Employee> employees;
        
    //INITIALIZE
    static {
            employees = loadEmployees();
    }
    
    
    //LOADS EMPLOYEE DATA
    private static List<Employee> loadEmployees() {
            List<Employee> employee = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new FileReader(TXT_FILE_PATH))) {
                List<String[]> allData = reader.readAll();

                for (String[] employeeData : allData) {
                    employee.add(new Employee(employeeData));
                }
            } catch (IOException | CsvException e) {
                handleException(e);
            }

            return employee;
        }

    
    //PRINTS TARGET EMPLOYEES DETAILS
    public static void printEmployeeDetails(String targetEmployeeId) {

        for (Employee employee : employees) {
            if (employee.getEmployeeNumber().equals(targetEmployeeId)) {
                System.out.println("Employee Details for Employee ID " + targetEmployeeId + ":" + '\n' +
                                   "-------------------------");
                System.out.println(employee.toString(true));
                System.out.println("-------------------------");
                return;
            }
        }

        System.out.println("Employee ID " + targetEmployeeId + " not found.");
    }
    
    //PRINTS EMPLOYEE SELECTION MENU
    public static void printEmpSelectList() {
            System.out.println("""
                   -------------------------
                   |     Employee List     |
                   -------------------------""");

        String format = "%-15s%-20s"; // Adjust the width as needed

        for (Employee employee : employees) {
            System.out.printf(format, employee.getEmployeeNumber(), employee.getLastName());
            System.out.println(); // Print a new line            
        }    
    }
    
    //PRINTS ALL EMPLOYEE DATA (FOR DEBUGGING PURPOSES)
    private static void printCsv() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
    
    //PRINTS STACK TRACE FOR HANDLEEXCEPTIONS
    private static void handleException(Exception e) {
        e.printStackTrace();    
    }
    
    //GETTERS & SETTERS HERE ONWARDS
    /**
     * @return the employees
     */
    @Override
    public List<Employee> getEmployeeModelList(){
        return employees;
    }
}
