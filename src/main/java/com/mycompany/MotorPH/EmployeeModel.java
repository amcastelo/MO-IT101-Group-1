/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.MotorPH;

import java.util.List;
import java.util.Iterator;
/**
 *
 * @author Isaac
 */
public abstract class EmployeeModel implements Iterable<Employee>{
    
    protected List<Employee> employees;
    
    protected abstract List<Employee> getEmployeeModelList();
    
    @Override
    public Iterator<Employee> iterator() {
        return employees.iterator();
    }
}
