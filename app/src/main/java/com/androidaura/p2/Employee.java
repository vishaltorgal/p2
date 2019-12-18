package com.androidaura.p2;

public class Employee {

    String empid;
    String fname;
    String lname;

public Employee(){   }


public Employee(String empid, String fname, String lname){

        this.empid = empid;
        this.fname = fname;
        this.lname = lname;
        }



public String getEMPID(){
        return this.empid;
        }

public String getFname(){
        return this.fname;
        }

public String getLname(){
        return this.lname;
        }


public void setEMPID(String empid){
        this.empid = empid;
        }

public void setFName(String fname){
        this.fname = fname;
        }

public void setLName(String lname){
        this.lname = lname;
        }

        }