package com.kenzan.hiring.customersapi.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.kenzan.hiring.customersapi.model.Employee;
import com.kenzan.hiring.customersapi.model.EmployeeStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeJpaRepositoryTest{

    @Autowired
    private EmployeeJpaRepository repository;
    
    @Test
    public void when_employee_saved(){
        Employee employee = new Employee();
        employee.setFirstName("Alfredo");
        employee.setMiddleInitial("Soto");
        employee.setLastName("Ruiz");
        employee = repository.save(employee);
        assertFalse("Was not possible to perform the database insert", employee.getId() == null);
    }

    @Test
    public void when_findByEmployeeStatus(){
        Employee employee = new Employee();
        employee.setFirstName("Alfredo");
        employee.setMiddleInitial("Soto");
        employee.setLastName("Ruiz");
        employee.setEmployeeStatus(EmployeeStatus.INACTIVE);
        employee = repository.save(employee);
        List<Employee> inactiveEmployees= repository.findByEmployeeStatus(EmployeeStatus.INACTIVE);
        assertTrue("The list is expected not to be empty", !inactiveEmployees.isEmpty());
        inactiveEmployees= repository.findByEmployeeStatus(EmployeeStatus.ACTIVE);
        assertTrue("The list is expected to be empty", inactiveEmployees.isEmpty());
    }

    @Test
    public void when_findActiveById(){
        Employee employee = new Employee();
        employee.setFirstName("Alfredo");
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        employee = repository.save(employee);
        assertNotNull(employee);
        Employee foundEmployee = repository.findActiveById(employee.getId(), EmployeeStatus.ACTIVE);
        assertNotNull("The search was expected to find the employee",foundEmployee);
    }
}