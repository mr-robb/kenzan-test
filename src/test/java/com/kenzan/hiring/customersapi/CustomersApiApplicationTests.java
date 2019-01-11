package com.kenzan.hiring.customersapi;

import static org.junit.Assert.assertEquals;

import com.kenzan.hiring.customersapi.repository.EmployeeJpaRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomersApiApplicationTests {

	@Autowired
	private EmployeeJpaRepository repository;

	@Test
	public void check_for_initial_database_state(){
		assertEquals(0 , repository.count()); 
	}


}