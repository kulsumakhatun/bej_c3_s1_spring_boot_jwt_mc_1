package com.niit.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.domain.Customer;
import com.niit.domain.Product;
import com.niit.exception.CustomerAlreadyExistException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.ProductNotFoundException;
import com.niit.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;

    Customer customer;
    Product product;
    @Autowired
    MockMvc mockMvc;

    List<Customer> customerList;
    @BeforeEach
    public void setUp(){
        this.product = new Product(1,"Fan","Stand fan");
        this.customer = new Customer(101,"Kulsuma","123456789",this.product);
       this.customerList = new ArrayList<>();
        customerList.add(customer);
        this.product = new Product(2,"Light","Tube Light");
        this.customer = new Customer(201,"Rokeya","123456789",this.product);
        customerList.add(customer);
         mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void saveCustomerSuccess() throws Exception {
        when(customerService.addCustomer(any())).thenReturn(customer);
       mockMvc.perform(post("/customer/api/customer").
                       contentType(MediaType.APPLICATION_JSON).
                       content(convertJsonToString(customer))).
               andExpect(status().isCreated()).
               andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void saveCustomerfailure() throws Exception {
        when(customerService.addCustomer(any())).thenThrow(CustomerAlreadyExistException.class);
        mockMvc.perform(post("/customer/api/customer").
                        contentType(MediaType.APPLICATION_JSON).
                        content(convertJsonToString(customer))).
                andExpect(status().isConflict()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllCustomerSucess() throws Exception {

        when(customerService.getAllCustomer()).thenReturn(customerList);
        mockMvc.perform(get("/customer/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJsonToString(customer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllCustomerFailure() throws Exception {
        when(customerService.getAllCustomer()).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/customer/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJsonToString(customer)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCustomerByProductNameSuccess() throws Exception {
        when(customerService.getAllCustomerByProductName(anyString())).thenReturn(customerList);
        mockMvc.perform(get("/customer/api/products/Fan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJsonToString(customer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCustomerByProductNameFailure() throws Exception {
        when(customerService.getAllCustomerByProductName(anyString())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(get("/customer/api/products/Fan")
                .contentType(MediaType.APPLICATION_JSON).content(convertJsonToString(customer)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }




    @Test
    public void deteleCustomerByIdSucess() throws Exception {
        String msg ="Customer Deleted";
        when(customerService.deleteCustomer(101)).thenReturn(msg);
        mockMvc.perform(delete("/customer/api/customer/101").
                contentType(MediaType.APPLICATION_JSON).content(convertJsonToString(customer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deteleCustomerByIdFailure() throws Exception {
        String msg ="Customer Deleted";
        when(customerService.deleteCustomer(101)).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(delete("/customer/api/customer/101").
                        contentType(MediaType.APPLICATION_JSON).content(convertJsonToString(customer)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCustomerByProductNameSucess() throws ProductNotFoundException {
//        when(customerService.getAllCustomerByProductName("Fan")).thenReturn()
    }


    private static String convertJsonToString(final Object ob) {
        String result;
        ObjectMapper mapper=new ObjectMapper();
        try {
            String jsoncontent= mapper.writeValueAsString(ob);
            result=jsoncontent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result="Json parser error";
        }
        return result;
    }


    @AfterEach
    public void tearDown(){
        this.customer =null;
        this.product =null;
    }


}
