package com.digitalacademy.customer.api;

import com.digitalacademy.customer.controller.LoanController;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceApiTest {
    @Mock RestTemplate restTemplate ;
    @InjectMocks LoanApi loanApi;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.initMocks(this);
        loanApi = new LoanApi(restTemplate);
    }

    public static ResponseEntity<String> prepareResponseSuccess() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"0\",\n" +
                "        \"message\": \"success\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"id\": 1,\n" +
                "        \"status\": \"ok\",\n" +
                "        \"account_payable\": \"111-111-1111\",\n" +
                "        \"account_receivable\": \"222-222-2222\",\n" +
                "        \"principal_amount\": 550000.5\n" +
                "    }\n" +
                "}");
    }

    @DisplayName("test get loan info return loan info")
    @Test void testGetLoanInfo() throws Exception {
        Long reqId = 1L;

        when(restTemplate.exchange(ArgumentMatchers.< RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(this.prepareResponseSuccess());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId) ;

        assertEquals("1", resp.getId().toString());
        assertEquals("ok", resp.getStatus());
        assertEquals("111-111-1111", resp.getAccountPayable());
        assertEquals("222-222-2222", resp.getAccountReceivable());
        assertEquals(550000.5, resp.getPrincipalAmount());

        verify(restTemplate,times(1)).exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any());

    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4002(){
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4002\",\n" +
                "        \"message\": \"info not found\"\n" +
                "    }\n" +
                "}");
    }

    @DisplayName("test get loan ServerError")
    @Test void testGetLoanInfoInternalServerError() throws Exception {
        Long reqId = 2L;
        when(restTemplate.exchange(ArgumentMatchers.< RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(this.prepareResponseEntityLOAN4002());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId) ;

        assertEquals(null, resp.getId());
        assertEquals(null, resp.getStatus());
        assertEquals(null, resp.getAccountPayable());
        assertEquals(null, resp.getAccountReceivable());
        assertEquals(0, resp.getPrincipalAmount());

    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4001(){
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4001\",\n" +
                "        \"message\": \"cannot get loan info\"\n" +
                "    },\n" +
                "    \"data\": \"cannot get loan info\"\n" +
                "}");
    }

    @DisplayName("test get loan LOAN4001")
    @Test void testGetLoanInfoLOAN4001() throws Exception {
        Long reqId = 2L;
        when(restTemplate.exchange(ArgumentMatchers.< RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(this.prepareResponseEntityLOAN4001());

        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId) ;

        assertEquals(null, resp.getId());
        assertEquals(null, resp.getStatus());
        assertEquals(null, resp.getAccountPayable());
        assertEquals(null, resp.getAccountReceivable());
        assertEquals(0, resp.getPrincipalAmount());

    }

    @DisplayName("test get loan throw exception")
    @Test void testGetLoanInfoReturnClientException() throws Exception {
        Long reqId = 3L;

        when(restTemplate.exchange(ArgumentMatchers.< RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(HttpClientErrorException.class);

        Exception thrown = assertThrows(Exception.class, () -> loanApi.getLoanInfo(reqId),"cannot get loan info");

        assertEquals("httpClientErrorException", thrown.getMessage());
        verify(restTemplate,times(1)).exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any());

    }


}
