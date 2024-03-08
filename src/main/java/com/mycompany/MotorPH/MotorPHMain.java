package com.mycompany.MotorPH;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MotorPHMain {
    
    private static int platform = 1;
    private static EmployeeModel employeeModel;
    
    private static Scanner sc = new Scanner(System.in);
    private static Scanner inputPlatform = new Scanner(System.in);
    
    static ArrayList<AttendanceRecord> attendanceRecords = AttendanceRecord.getAttendanceRecords();
    
    public static void main(String[] args) {
        getDefaultEmployeeModel();
        menu();
    }
    
    //PRINT MAIN MENU LOOP AFTER EVERY ACTION
    private static void menu(){
        int Resume = 1;  
    do{ 
        System.out.print("""
        ----- Motor PH MENU -----

        1: Show Employee Details
        2: Calculate Gross Wage
        3: Calculate Net Wage
        4: Choose platform
        0: EXIT
        -------------------------
        CHOOSE: """);
        
        String empNum, detailSub;
        String ch = sc.next();
        int month;
 
        switch (ch){
            case "1":
                System.out.print("""
                ----- Motor PH MENU -----

                1: Individual Employee Details
                2: All Employee Details
                -------------------------
                Choose: """);
                detailSub = sc.next();
                System.out.println("-------------------------");
                menu(detailSub);
                break;
                
            case "2":
                System.out.println("-------------------------");
                System.out.print("Enter Employee #: ");              
                empNum = sc.next();
                System.out.println("-------------------------");
                System.out.println("Enter Month: ");
                month = sc.nextInt();
                System.out.println("-------------------------");
                EmployeeSalaryGross.calculateGross(empNum, month);
                EmployeeSalaryGross.printGross();
                break;
                
            case "3":
                System.out.println("-------------------------");
                System.out.print("Enter Employee #: ");              
                empNum = sc.next();
                System.out.println("-------------------------");
                System.out.println("Enter Month: ");
                month = sc.nextInt();
                System.out.println("-------------------------");
                NetWageCalculation.calculateNetWage(empNum, month);
                break;
                
            case "4":
                System.out.println("Choose from which platform to load employee data:");
                System.out.print("""
                ----- Motor PH MENU -----

                1: Load from File
                2: Load from class
                -------------------------
                Choose: """);
                    
                String optionPlatform = inputPlatform.next();
                    
                    switch (optionPlatform) {
                    case "1":
                    platform = 1;
                    break;
                    
                    case "2":
                    platform = 2;
                    break;
                    
                    default:
                    break;
                    }
                    
                getDefaultEmployeeModel();
                break;
                
            case "0":
                System.exit(0);
                break;
            
            default:
                System.out.println("Invalid Input!");
                break;
        }
        
        System.out.println("back to menu? 1 = yes, 0 = no");
        Resume = sc.nextInt();
        }while (Resume != 0);
    }
    
    //OVERLOAD MENU LOOP FOR SUBMENU IN PRINTING EMPLOYEE DETAILS
    private static void menu(String detailSub){       
        switch (detailSub){
            case "1":
                EmployeeModelFromFile.printEmpSelectList();
                System.out.println("-------------------------");
                System.out.print("Enter Employee #: ");              
                String empNum = sc.next();
                System.out.println("-------------------------");
                EmployeeModelFromFile.printEmployeeDetails(empNum);                
                break;
                
            case "2": 
                List<Employee> employees = employeeModel.getEmployeeModelList();
                    
                for (Employee employee : employees) {
                System.out.println(employee);
                }
                
                System.out.println("-------------------------");
                break;                                
        }
    }
    
    //SETS DEFAULT EMPLOYEE MODEL LOADED
    private static void getDefaultEmployeeModel() {
        if (platform == 1) {
            employeeModel = new EmployeeModelFromFile();
        } else {
            employeeModel = new EmployeeModelFromClass();
        }
    }
}

