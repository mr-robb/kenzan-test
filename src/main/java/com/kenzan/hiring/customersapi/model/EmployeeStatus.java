package com.kenzan.hiring.customersapi.model;

public enum EmployeeStatus {
 ACTIVE(1),INACTIVE(0);
 
 private int status;

 private EmployeeStatus(int status){
     this.status = status;
 }
 
}