package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepositories customerRepositories;

    @Autowired
    public CustomerService(CustomerRepositories customerRepositories) {
        this.customerRepositories = customerRepositories;
    }

    /*private final List<Customer> getCustomerDemo() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer() ;
        customer.setId(1L);
        customer.setAge(19);
        customer.setEmail("cccccccc");
        customer.setFirstName("noon");
        customer.setLastName("limp");
        customer.setPhoneNo("01111");
        customerList.add(customer);

        customer = new Customer() ;
        customer.setId(2L);
        customer.setAge(111);
        customer.setEmail("aaaaaa");
        customer.setFirstName("noonnoon");
        customer.setLastName("limpalasook");
        customer.setPhoneNo("9999999");
        customerList.add(customer);

        return customerList ;
    }*/

    // ต่อ db + get ของ
    public List<Customer> getCustomerList() {
        //  return  customerRepositories.findAll();
//        List<Customer> customerList = new ArrayList<>();
//        Customer customer = new Customer() ;
//        customer.setId(1L);
//        customer.setAge(19);
//        customer.setEmail("cccccccc");
//        customer.setFirstName("noon");
//        customer.setLastName("limp");
//        customer.setPhoneNo("01111");
//        customerList.add(customer);
//
//        return customerList ;

//        return getCustomerDemo();

        return  customerRepositories.findAll() ;
    }

    public Customer getCustomer(Long id) {
//        Customer customer = new Customer() ;
//        customer.setId(1L);
//        customer.setAge(19);
//        customer.setEmail("cccccccc");
//        customer.setFirstName("noon");
//        customer.setLastName("limp");
//        customer.setPhoneNo("01111");

//        return getCustomerDemo().get(id) ;
        return  customerRepositories.findAllById(id) ;

    }

    public  List<Customer> getCustomerName(String name) {
//        return  getCustomerDemo().get();
        return customerRepositories.findByFirstName(name);
    }

    public Customer createCustomer(Customer body) {
        return customerRepositories.save(body) ;
    }

//    public Customer updateCustomer(Long id, Customer customer) {
//        Customer customerResp = customerRepositories.findAllById(id) ;
//
////        if (customerResp != null) { return customerRepositories.save(customerResp) ; }
////        else { return null; }
//        return customerRepositories.findAllById(id) != null ? customerRepositories.save(customerResp) : null ;
//    }

    public Customer updateCustomer(Long id,Customer customerReq){
        return customerRepositories.findAllById(id) != null ?
                customerRepositories.save(customerReq) : null;
    }

    public boolean deleteCustomer(Long id) {
        try {
            customerRepositories.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
