package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {

    public static List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>() ;
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("nong");
        customer.setLastName("noon");
        customer.setEmail("111@ku.com");
        customer.setPhoneNo("111111111");
        customer.setAge(20);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("nong2");
        customer.setLastName("noon2");
        customer.setEmail("222@ku.com");
        customer.setPhoneNo("22222222");
        customer.setAge(32);
        customerList.add(customer);

        return customerList;
    }

    public static List<Customer> getCustomerListSameName() {
        List<Customer> customerList = new ArrayList<>() ;
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("nong");
        customer.setLastName("noon");
        customer.setEmail("111@ku.com");
        customer.setPhoneNo("111111111");
        customer.setAge(20);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("nong");
        customer.setLastName("noon2");
        customer.setEmail("222@ku.com");
        customer.setPhoneNo("22222222");
        customer.setAge(32);
        customerList.add(customer);

        return customerList;
    }

    public static List<Customer> getCustomerNameNongNoonList() {
        List<Customer> customerList = new ArrayList<>() ;
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("nong");
        customer.setLastName("noon");
        customer.setEmail("111@ku.com");
        customer.setPhoneNo("111111111");
        customer.setAge(20);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("nong2");
        customer.setLastName("noon2");
        customer.setEmail("222@ku.com");
        customer.setPhoneNo("22222222");
        customer.setAge(32);
        customerList.add(customer);

        return customerList;
    }

    public static Customer getNewCustomer() {
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("reqnong");
        reqCustomer.setLastName("reqnoon");
        reqCustomer.setEmail("req111@ku.com");
        reqCustomer.setPhoneNo("req111111111");
        reqCustomer.setAge(22);
        return reqCustomer;
    }

    public static Customer createNewCustomer() {
        Customer reqCustomer = new Customer();
        reqCustomer.setFirstName("createNew_reqnong");
        reqCustomer.setLastName("createNew_reqnoon");
        reqCustomer.setEmail("createNew_req111@ku.com");
        reqCustomer.setPhoneNo("createNew_111111111");
        reqCustomer.setAge(22);
        reqCustomer.setId(8L);
        return reqCustomer;
    }

    public static Customer responseCreateNewCustomer() {
        Customer reqCustomer = new Customer();
        reqCustomer.setId(8L);
        reqCustomer.setFirstName("response_reqnong");
        reqCustomer.setLastName("response_reqnoon");
        reqCustomer.setEmail("response_req111@ku.com");
        reqCustomer.setPhoneNo("response_111111111");
        reqCustomer.setAge(22);
        return reqCustomer;
    }

    public static Customer getUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Update.nong");
        customer.setLastName("Update.noon");
        customer.setEmail("Update111@ku.com");
        customer.setPhoneNo("Update111111111");
        customer.setAge(20);
        return customer;
    }


}
