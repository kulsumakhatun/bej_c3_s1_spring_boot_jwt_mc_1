package com.niit.service;

import com.niit.domain.Customer;
import com.niit.domain.Product;
import com.niit.exception.CustomerAlreadyExistException;
import com.niit.exception.CustomerNotFoundException;
import com.niit.exception.ProductNotFoundException;
import com.niit.repository.CustomerRepository;
import com.niit.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    private Customer customer;
    private Product product;

    List<Customer> customerList;
    List<Product> productList;

    @BeforeEach
    public void setUp() {
        this.customerList = new ArrayList<>();
        this.product = new Product(1, "KK", "abc");
        this.customer = new Customer(10, "kk", "123456", this.product);
        customerList.add(customer);
        this.product = new Product(2, "Pk", "abc");
        this.customer = new Customer(11, "kk", "123456", this.product);
        customerList.add(customer);




    }

    @Test
    public void onSaveOfCustomerReturnCustomerSuccess() throws CustomerAlreadyExistException {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(null));
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.addCustomer(customer));

    }

    @Test
    public void toSaveCustomerReturnCustomerFailure() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(customer));
        assertThrows(CustomerAlreadyExistException.class, () -> customerService.addCustomer(customer));
    }

    @Test
    public void displayingAllTheCustomerSuccess() throws CustomerNotFoundException {
        when(customerRepository.findAll()).thenReturn((customerList));
        assertEquals(customerList.size(), customerService.getAllCustomer().size());
    }

    @Test
    public void displayingAllTheCustomerFailure() {
        customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        assertThrows(CustomerNotFoundException.class, () -> customerService.getAllCustomer());

    }

    @Test
    public void deleteCustomerByIdSuccess() throws CustomerNotFoundException {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(customer));
        String result = customerService.deleteCustomer(customer.getCustomerId());
        assertEquals("Customer Deleted", result);

    }

    @Test
    public void deleteCustomerByIdFailure() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(null));
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void getAllCustomerByProductNameSuccess() throws CustomerNotFoundException{
        customerRepository.deleteAll();
      customerRepository.saveAll(customerList);
        Iterator<Customer> iterator = customerList.iterator();
        List<Customer> filteredCusList = new ArrayList<>();
        while (iterator.hasNext()) {
           Customer customer1 = iterator.next();
            if (customer1.getProduct().getProductName().equals("Pk")){
                filteredCusList.add(customer1);
            }
        }
        when(customerRepository.findAll()).thenReturn(filteredCusList);
        assertEquals( filteredCusList,customerService.getAllCustomer());
    }

    @Test
    public void getAllCustomerByProductNameFailure() {
        customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        assertThrows(CustomerNotFoundException.class, () -> customerService.getAllCustomer());
    }


    @AfterEach
    public void clear() {
        this.product = null;
        this.customer = null;

    }
}



