package com.kenzan.hiring.customersapi.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzan.hiring.customersapi.model.Employee;
import com.kenzan.hiring.customersapi.repository.EmployeeJpaRepository;

/*
    This component will be responsible for reading a JSON file
    provided as input, parse it and finally populate the database
    with the entries in such json file
*/ 
@Component
public class DataLoader {

    @Value("${fileToLoad}")
    private String inputFile;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public void loadJsonData(){
        byte[] jsonData;
        try {
            jsonData = Files.readAllBytes(Paths.get(inputFile));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);
            Iterator<JsonNode> elements = rootNode.elements();
            LOG.info("Starting database population");
            SimpleDateFormat format = new SimpleDateFormat( DATE_FORMAT );

            while(elements.hasNext()){
                // Parse entries one by one from JSON input and store every property for future object creation
                JsonNode employeeNode = elements.next();
                JsonNode firstNameNode = employeeNode.path("firstName");
                JsonNode middleInitialNode = employeeNode.path("middleInitial");
                JsonNode lastNameNode = employeeNode.path("lastName");
                JsonNode dateOfBirthNode = employeeNode.path("dateOfBirth");
                JsonNode dateOfEmploymentNode = employeeNode.path("dateOfEmployment");
                
                // Employee creation
                Employee tmpEmployee = new Employee();
                tmpEmployee.setFirstName(firstNameNode.textValue());
                tmpEmployee.setMiddleInitial(middleInitialNode.textValue());
                tmpEmployee.setLastName(lastNameNode.textValue());
                tmpEmployee.setDateOfBirth(format.parse(dateOfBirthNode.textValue()));
                tmpEmployee.setDateOfEmployment(format.parse(dateOfEmploymentNode.textValue()));

                tmpEmployee = employeeJpaRepository.save(tmpEmployee);
                LOG.info("Employee successfully saved into Database with ID:" + tmpEmployee.getId());
            }

        } catch (IOException e) {
            LOG.error("Expcetion found, message:" + e.getMessage());
	    }catch(ParseException ex){
            LOG.error("Expcetion found, message:" + ex.getMessage());
        }
    }
}