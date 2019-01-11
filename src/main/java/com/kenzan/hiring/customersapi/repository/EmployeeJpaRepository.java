package com.kenzan.hiring.customersapi.repository;

import java.util.List;

import com.kenzan.hiring.customersapi.model.Employee;
import com.kenzan.hiring.customersapi.model.EmployeeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

/*
    This Repository will allow us to interact with Employee table, by default
    it includes functionality defined by spring within JpaRepository, so that
    we are reusing a lot of code and only need to care about custom queries
    we might need
*/
@Component
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>{

    public List<Employee> findByEmployeeStatus(EmployeeStatus employeeStatus);

    @Query("SELECT e FROM Employee e WHERE e.id = :id AND e.employeeStatus = :employeeStatus")
    public Employee findActiveById( @Param("id") Long id, @Param("employeeStatus") EmployeeStatus employeeStatus );

}