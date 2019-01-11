package com.kenzan.hiring.customersapi.controller;

import java.util.List;

import com.kenzan.hiring.customersapi.model.Employee;
import com.kenzan.hiring.customersapi.model.EmployeeStatus;
import com.kenzan.hiring.customersapi.repository.EmployeeJpaRepository;
import com.kenzan.hiring.customersapi.util.SecurityAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
/*
    Application's main endpoint. This class holds all the
    resources available within the web application
    The patters used here are:
    MVC
    Factory ( default spring's object creation strategy )
*/ 
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private SecurityAgent securityAgent;

    @GetMapping(value="/all")
    public List<Employee> findAllActive(){
        return employeeJpaRepository.findByEmployeeStatus(EmployeeStatus.ACTIVE);
    }

    @GetMapping(value="/*")
    public List<Employee> findAll(){
        return employeeJpaRepository.findAll();
    }

    @GetMapping(value="/{id}")
    public Employee findById(@PathVariable final long id){
        Employee result = employeeJpaRepository.findActiveById(id, EmployeeStatus.ACTIVE);
        if( result != null ) {
            return result;
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Employee create (@RequestBody final Employee employee){
        Employee result = employeeJpaRepository.save(employee);
        return result;
    }

    
    @DeleteMapping(value="/{id}")
    public void deleteEmployee(@PathVariable final long id, @RequestHeader(name="auth-token", required=true) String authToken){
        if( !securityAgent.isAuthorizedRequest(authToken) ){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        Employee tmpEmployee = employeeJpaRepository.getOne(id);
        tmpEmployee.setEmployeeStatus(EmployeeStatus.INACTIVE);
        employeeJpaRepository.save(tmpEmployee);
    }

    @PutMapping
    public Employee update (@RequestBody final Employee employee){
        Employee result = employeeJpaRepository.save(employee);
        return result;
    
    }
}