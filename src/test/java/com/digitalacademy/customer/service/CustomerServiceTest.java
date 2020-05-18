package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepositories;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.digitalacademy.customer.support.CustomerSupportTest.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock private CustomerRepositories customerRepositories ;
    @InjectMocks CustomerService customerService ;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepositories) ;
    }

    public static List<Customer> getCustomer() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("nong");
        customer.setLastName("noon");
        customer.setEmail("111@ku.com");
        customer.setPhoneNo("111111111");
        customer.setAge(20);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("nong2");
        customer.setLastName("noon2");
        customer.setEmail("222@ku.com");
        customer.setPhoneNo("22222222");
        customer.setAge(32);

        return customerList;
    }

    @DisplayName("test get all customer should return list")
    @Test void testGetAllCustomer() {
        when(customerRepositories.findAll()).thenReturn(CustomerSupportTest.getCustomerList());
        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("nong",resp.get(0).getFirstName());
        assertEquals("noon",resp.get(0).getLastName());
        assertEquals("111@ku.com",resp.get(0).getEmail());
        assertEquals("111111111",resp.get(0).getPhoneNo());
        assertEquals(20,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("nong2",resp.get(1).getFirstName());
        assertEquals("noon2",resp.get(1).getLastName());
        assertEquals("222@ku.com",resp.get(1).getEmail());
        assertEquals("22222222",resp.get(1).getPhoneNo());
        assertEquals(32,resp.get(1).getAge().intValue());
    }


    @DisplayName("test get all customer by id should return list")
    @Test void testGetAllCustomerById() {
        Long reqParam = 1L ;
        when(customerRepositories.findAllById(reqParam)).thenReturn(CustomerSupportTest.getCustomerList().get(0));
        Customer resp = customerService.getCustomer(reqParam) ;

        assertEquals(1,resp.getId().intValue());
        assertEquals("nong",resp.getFirstName());
        assertEquals("noon",resp.getLastName());
        assertEquals("111@ku.com",resp.getEmail());
        assertEquals("111111111",resp.getPhoneNo());
        assertEquals(20,resp.getAge().intValue());
    }

    @DisplayName("test get all customer by name should return list")
    @Test void testGetAllCustomerByName() {
        String name = "nong" ;
        when(customerRepositories.findByFirstName(name)).thenReturn(CustomerSupportTest.getCustomerNameNongNoonList());
        List<Customer> resp = customerService.getCustomerName(name);

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("nong",resp.get(0).getFirstName());
        assertEquals("noon",resp.get(0).getLastName());
        assertEquals("111@ku.com",resp.get(0).getEmail());
        assertEquals("111111111",resp.get(0).getPhoneNo());
        assertEquals(20,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("nong2",resp.get(1).getFirstName());
        assertEquals("noon2",resp.get(1).getLastName());
        assertEquals("222@ku.com",resp.get(1).getEmail());
        assertEquals("22222222",resp.get(1).getPhoneNo());
        assertEquals(32,resp.get(1).getAge().intValue());

    }

    @DisplayName("test create customer should return new customer")
    @Test void testCreateCustomer() {
        Customer reqCustomer = new Customer();
        reqCustomer.setFirstName("reqnong");
        reqCustomer.setLastName("reqnoon");
        reqCustomer.setEmail("req111@ku.com");
        reqCustomer.setPhoneNo("req111111111");
        reqCustomer.setAge(22);

        when(customerRepositories.save(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());
        Customer resp = customerService.createCustomer(reqCustomer) ;
        assertEquals(1,resp.getId().intValue());
        assertEquals("reqnong",resp.getFirstName());
        assertEquals("reqnoon",resp.getLastName());
        assertEquals("req111@ku.com",resp.getEmail());
        assertEquals("req111111111",resp.getPhoneNo());
        assertEquals(22,resp.getAge().intValue());

    }

    @DisplayName("test Update customer should return success")
    @Test void testUpdateCustomer() {
        Long reqId = 4L;
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("reqnong");
        reqCustomer.setLastName("reqnoon");
        reqCustomer.setEmail("req111@ku.com");
        reqCustomer.setPhoneNo("req111111111");
        reqCustomer.setAge(22);

        when(customerRepositories.findAllById(reqId)).thenReturn(CustomerSupportTest.getNewCustomer());
        when(customerRepositories.save(reqCustomer)).thenReturn(CustomerSupportTest.getNewCustomer());
        Customer resp = customerService.updateCustomer(reqId,reqCustomer) ;

        assertEquals(1,resp.getId().intValue());
        assertEquals("reqnong",resp.getFirstName());
        assertEquals("reqnoon",resp.getLastName());
        assertEquals("req111@ku.com",resp.getEmail());
        assertEquals("req111111111",resp.getPhoneNo());
        assertEquals(22,resp.getAge().intValue());
    }

    @DisplayName("test Update customer should return fail")
    @Test void testUpdateCustomerFail() {
        Long reqId = 4L;
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("reqnong");
        reqCustomer.setLastName("reqnoon");
        reqCustomer.setEmail("req111@ku.com");
        reqCustomer.setPhoneNo("req111111111");
        reqCustomer.setAge(22);

        when(customerRepositories.findAllById(reqId)).thenReturn(null);
        Customer resp = customerService.updateCustomer(reqId,reqCustomer) ;
        assertEquals(null,resp);
    }

    @DisplayName("Test Delete customer should return success")
    @Test
    void testDeleteCustomer(){
        Long reqId = 1L;
        doNothing().when(customerRepositories).deleteById(reqId);
        boolean resp = customerService.deleteCustomer(reqId);
        assertTrue(resp);
    }
    @DisplayName("Test Delete customer should return success")
    @Test
    void testDeleteCustomerFail(){
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(customerRepositories).deleteById(reqId);
        boolean resp = customerService.deleteCustomer(reqId);
        assertFalse(resp);
    }
}
