package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.service.CustomerServiceTest;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock CustomerService customerService ;
    @InjectMocks CustomerController customerController ;

    private MockMvc mvc ;
    public static  final String urlCustomerList = "/customer/list/" ;
    public static  final String urlCustomer = "/customer/" ;

    @BeforeEach public void SetUp() {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("test get all customer should return customer list")
    @Test void testGetCustomerList() throws Exception {
        when(customerService.getCustomerList()).thenReturn(CustomerSupportTest.getCustomerList());
        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("nong",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("noon",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("111@ku.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("111111111",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals("20",jsonArray.getJSONObject(0).get("age").toString());

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("nong2",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("noon2",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("222@ku.com",jsonArray.getJSONObject(1).get("email"));
        assertEquals("22222222",jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals("32",jsonArray.getJSONObject(1).get("age").toString());
    }

    @DisplayName("test get customer by id should return customer list")
    @Test void testGetCustomerById() throws Exception {
        Long reqId = 1L;
        when(customerService.getCustomer(reqId)).thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",json.get("id").toString());
        assertEquals("reqnong",json.get("firstName"));
        assertEquals("reqnoon",json.get("lastName"));
        assertEquals("req111@ku.com",json.get("email"));
        assertEquals("req111111111",json.get("phoneNo"));
        assertEquals("22",json.get("age").toString());
    }

    @DisplayName("test get customer by id should return customer list not found")
    @Test void testGetCustomerByIdNotFound() throws Exception {
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isNotFound()).andReturn();

    }

    @DisplayName("test get customer by id name return customer ")
    @Test void testGetCustomerByName() throws Exception {
        String reqName = "nong";

        when(customerService.getCustomerName(reqName)).thenReturn(CustomerSupportTest.getCustomerListSameName());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("nong",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("noon",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("111@ku.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("111111111",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals("20",jsonArray.getJSONObject(0).get("age").toString());

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("nong",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("noon2",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("222@ku.com",jsonArray.getJSONObject(1).get("email"));
        assertEquals("22222222",jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals("32",jsonArray.getJSONObject(1).get("age").toString());

    }

    @DisplayName("test get customer by name  should return customer list not found")
    @Test void testGetCustomerByNameNotFound() throws Exception {
        String reqName = "bow";
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isNotFound()).andReturn();
    }

    @DisplayName("Test get customer by name should return not found")
    @Test
    void testGetCustomerByIsEmpty() throws Exception{
        String reqName = "dss";
        when(customerService.getCustomerName(reqName)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(get(urlCustomer +"?name="+ reqName))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("test create customer should  return success ")
    @Test void tesCreateCustomer() throws Exception {
        Customer reqCustomer = CustomerSupportTest.createNewCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false) ;
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.createNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andExpect(status().isCreated()).andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("createNew_reqnong",json.get("firstName"));
        assertEquals("createNew_reqnoon",json.get("lastName"));
        assertEquals("createNew_req111@ku.com",json.get("email"));
        assertEquals("createNew_111111111",json.get("phoneNo"));
        assertEquals("22",json.get("age").toString());
        assertEquals("8",json.get("id").toString());

        Mockito.verify(customerService, Mockito.times(1)).createCustomer(reqCustomer);
    }

    @DisplayName("test create customer empty")
    @Test void tesCreateCustomerWithEmpty() throws Exception {
        Customer reqCustomer = CustomerSupportTest.createNewCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false) ;
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer)).thenReturn(CustomerSupportTest.responseCreateNewCustomer());
        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest()).andReturn();

        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [type 1-100]] "
                ,mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("Test update customer should return SUCCESS")
    @Test
    void testUpdateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getNewCustomer();
        Long reqId = 1L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId, reqCustomer)).thenReturn(CustomerSupportTest.getUpdateCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject json = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("Update.nong",json.get("firstName"));
        assertEquals("Update.noon",json.get("lastName"));
        assertEquals("Update111@ku.com",json.get("email"));
        assertEquals("Update111111111",json.get("phoneNo"));
        assertEquals("20",json.get("age").toString());
        assertEquals("1",json.get("id").toString());
    }

    @DisplayName("test update customer should return id not found")
    @Test void tesUpdateCustomerIdNotFound() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getUpdateCustomer();
        Long reqId = 1L ;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false) ;
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer)).thenReturn(null);
        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/" + reqId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)).andExpect(status().isNotFound()).andReturn();

//        Mockito.verify(customerService, Mockito.times(1)).createCustomer(reqCustomer);
    }

    @DisplayName("test WithPathIsNotSend")
    @Test void tesUpdateCustomerWithPathIsNotSend() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getUpdateCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false) ;
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        MvcResult mvcResult = mvc.perform(put(urlCustomer + "/" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isMethodNotAllowed()).andReturn();
    }

    @DisplayName("test testDeleteCustomer")
    @Test void testDeleteCustomer() throws Exception {
        Long reqId = 4L;

        when(customerService.deleteCustomer(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "/" + reqId )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

    }

    @DisplayName("test DeleteCustomerShouldReturnNotFound")
    @Test void testDeleteCustomerShouldReturnNotFound() throws Exception {
        Long reqId = 4L;

        when(customerService.deleteCustomer(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer + "/" + reqId )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

    }




}
