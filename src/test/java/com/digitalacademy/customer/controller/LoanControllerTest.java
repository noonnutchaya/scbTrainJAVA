package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.api.LoanApi;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock LoanApi loanApi;
    @InjectMocks LoanController loanController ;

    private MockMvc mvc ;
    public static  final String loanPath = "/loan/" ;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }


    @DisplayName("test get loan return loan info")
    @Test void testGetLoanInfo() throws Exception {
        Long reqId = 1L;

        MvcResult mvcResult = mvc.perform(get(loanPath + "/" + reqId))
                .andExpect(status().isOk()).andReturn();

    }

    @DisplayName("test get loan return loan info")
    @Test void testGetLoanInfoNotFound() throws Exception {

        MvcResult mvcResult = mvc.perform(get(loanPath + "/" ))
                .andExpect(status().isNotFound()).andReturn();

    }
}
