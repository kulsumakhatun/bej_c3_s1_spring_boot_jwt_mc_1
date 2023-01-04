package com.niit.repository;


import com.niit.domain.Customer;
import com.niit.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTest {

    private Customer customer;
    private Product product;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        this.product = new Product(1,"KK","abc");
        this.customer =new Customer(10,"kk","123456",this.product);
    }

    @Test
    public void customerToSave(){
        Customer c1=customerRepository.save(customer);
        Customer customer1= customerRepository.findById(customer.getCustomerId()).get();
        assertEquals(customer,customer1);
    }

    @Test
    public void getAllCustomer(){
        customerRepository.deleteAll();
        customerRepository.insert(customer);
        this.product= new Product(2,"Rk","abcd");
        this.customer =new Customer(2,"Rk","123467890",this.product);
        customerRepository.insert(customer);
        List<Customer> list = customerRepository.findAll();

        assertEquals(2,list.size());
    }

    @Test
    public void deleteCustomer(){
        customerRepository.deleteAll();
        customerRepository.insert(customer);
        Customer  customer1 = customerRepository.findById(customer.getCustomerId()).get();
        customerRepository.delete(customer1);
        assertEquals(Optional.empty(),customerRepository.findById(customer1.getCustomerId()));

    }

    @Test
    public void fetchAllCustomerByProductName(){
        customerRepository.deleteAll();
        customerRepository.insert(customer);
        List<Customer> list=customerRepository.findByCustomerWithProductName(product.getProductName());
        assertEquals(1,list.size());
    }



    @AfterEach
    public void clear(){
        this.product =null;
        this.customer=null;

    }


}
