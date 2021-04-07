package com.nscooper.hsbc.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nscooper.hsbc.library.controllers.dto.BookDto;
import com.nscooper.hsbc.library.controllers.dto.BookRentalFeeDto;
import com.nscooper.hsbc.library.controllers.dto.CustomerDto;
import com.nscooper.hsbc.library.controllers.dto.RentalDto;
import com.nscooper.hsbc.library.services.RentalService;
import com.nscooper.hsbc.library.vo.Book;
import com.nscooper.hsbc.library.vo.BookRentalFee;
import com.nscooper.hsbc.library.vo.Customer;
import com.nscooper.hsbc.library.vo.Rental;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.nscooper.hsbc.library.config.Configuration.DATETIME_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RentalControllerTest<T> {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RentalService rentalServiceMock;
    @Valid
    BookDto bookDto;
    @Valid
    BookRentalFeeDto bookRentalFeeDto;
    @Valid
    CustomerDto customerDto;
    @Valid
    RentalDto rentalDto;

    Book book;
    BookRentalFee bookRentalFee;
    Customer customer;
    Rental rental;

    SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN+":SSS");
    LocalDateTime startDate;
    LocalDateTime endDate;
    UUID agreementReference;

    @Before
    public void setUp() {

        startDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        endDate = startDate.plusDays(3).truncatedTo(ChronoUnit.SECONDS);
        agreementReference = UUID.randomUUID();

        bookDto = new BookDto();
        bookDto.setAuthor("Me");
        bookDto.setIsbn("1234");
        bookDto.setTitle("Hello");
        bookDto.setQuantity(25);

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setIsbn("1234");
        book.setTitle("Hello");
        book.setAuthor("Me");
        book.setTotalStockedCopies(25);
        book.setTotalAvailableCopies(25);

        bookRentalFeeDto = new BookRentalFeeDto();
        bookRentalFeeDto.setIsbn("1234");
        bookRentalFeeDto.setDailyFee("1.25");

        bookRentalFee = new BookRentalFee();
        bookRentalFee.setId(UUID.randomUUID());
        bookRentalFee.setBook(book);
        bookRentalFee.setDailyFee(new BigDecimal(1.25));
        bookRentalFee.setFeeStartDate(startDate);
        bookRentalFee.setFeeEndDate(null);

        customerDto = new CustomerDto();
        customerDto.setFirstName("John");
        customerDto.setLastName("Smith");

        customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Smith");

        rentalDto = new RentalDto();
        rentalDto.setBookAuthor("Me");
        rentalDto.setBookTitle("Hello");
        rentalDto.setNumberOfDaysRental("5");
        rentalDto.setRentalAgreementReference(agreementReference.toString());
        rentalDto.setIsbn("1234");
        rentalDto.setCustomerFirstName("John");
        rentalDto.setCustomerLastName("Smith");

        rental = new Rental();
        rental.setId(UUID.randomUUID());
        rental.setRentalAgreementReference(agreementReference);
        rental.setCustomer(customer);
        rental.setBook(book);
        rental.setRentalStartDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        rental.setAgreedReturnDate(endDate);

        long multiplier = Duration.between( startDate , endDate ).toDays();
        rental.setTotalFee(bookRentalFee.getDailyFee().multiply(new BigDecimal(multiplier)));
    }

    @Test
    public void addBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.addBook(bookDto.getIsbn(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getQuantity())).thenReturn(book);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/addBook")
                        .content(mapper.writeValueAsString(bookDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book response = mapper.readValue(contentAsString, Book.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(book);
    }

    @Test
    public void getBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.getBook(bookDto.getIsbn())).thenReturn(book);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/getBook")
                        .content(mapper.writeValueAsString(bookDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book response = mapper.readValue(contentAsString, Book.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(book);
    }

    @Test
    public void addRentalFee() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.addRentalFee(bookRentalFeeDto.getIsbn(), bookRentalFeeDto.getDailyFee()))
                .thenReturn(bookRentalFee);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/addRentalFee")
                        .content(mapper.writeValueAsString(bookRentalFeeDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        BookRentalFee response = mapper.setDateFormat(dateFormat).readValue(contentAsString, BookRentalFee.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(bookRentalFee);
    }

    @Test
    public void getRentalFee() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        List<BookRentalFee> rentalFees = new ArrayList<>();
        rentalFees.add(bookRentalFee);
        when(rentalServiceMock.getRentalFee(bookRentalFeeDto.getIsbn()))
                .thenReturn(rentalFees);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/getRentalFee")
                        .content(mapper.writeValueAsString(bookRentalFeeDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        BookRentalFee[] responses = mapper.setDateFormat(dateFormat).readValue(contentAsString, BookRentalFee[].class);
        Arrays.asList(responses).forEach(
                response -> {
                    assertThat(response.getFeeEndDate()).isEqualTo(bookRentalFee.getFeeEndDate());
                    assertThat(response.getFeeStartDate()).isEqualTo(bookRentalFee.getFeeStartDate());
                    assertThat(response.getDailyFee()).isEqualTo(bookRentalFee.getDailyFee());
                    assertThat(response.getBook()).isEqualToComparingFieldByFieldRecursively(bookRentalFee.getBook());
                    assertThat(response.getId()).isEqualTo(bookRentalFee.getId());
                });
    }

    @Test
    public void getCurrentRentalFee() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.getCurrentRentalFee(bookRentalFeeDto.getIsbn()))
                .thenReturn(bookRentalFee);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/getCurrentRentalFee")
                        .content(mapper.writeValueAsString(bookRentalFeeDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        BookRentalFee response = mapper.setDateFormat(dateFormat).readValue(contentAsString, BookRentalFee.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(bookRentalFee);
    }

    @Test
    public void addCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.addCustomer(customerDto.getFirstName(),
                customerDto.getLastName())).thenReturn(customer);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/addCustomer")
                        .content(mapper.writeValueAsString(customerDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Customer response = mapper.setDateFormat(dateFormat).readValue(contentAsString, Customer.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(customer);
    }

    @Test
    public void getCustomer() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.getCustomer(customerDto.getFirstName(),
                customerDto.getLastName())).thenReturn(customer);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/getCustomer")
                        .content(mapper.writeValueAsString(customerDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Customer response = mapper.setDateFormat(dateFormat).readValue(contentAsString, Customer.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(customer);
    }

    @Test
    public void addRental() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.rentBook(any(), any(), any())).thenReturn(rental);
        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/addRental")
                        .content(mapper.writeValueAsString(rentalDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Rental response = mapper.setDateFormat(dateFormat).readValue(contentAsString, Rental.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(rental);
    }

    @Test
    public void endRental() throws Exception {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.returnBook(UUID.fromString(rentalDto.getRentalAgreementReference()))).thenReturn(rental);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/endRental")
                        .content(mapper.writeValueAsString(rentalDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Rental response = mapper.setDateFormat(dateFormat).readValue(contentAsString, Rental.class);
        assertThat(response).isEqualToComparingFieldByFieldRecursively(rental);
    }

    @Test
    public void getRentals() throws Exception {

        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        when(rentalServiceMock.getRentals(customer))
                .thenReturn(rentals);

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        when(rentalServiceMock.getRentals(customer)).thenReturn(rentals);

        MvcResult mvcResult =
                mockMvc.perform( MockMvcRequestBuilders.post("/getRentals")
                        .content(mapper.writeValueAsString(rentalDto))
                        .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        Rental[] responses = mapper.setDateFormat(dateFormat).readValue(contentAsString, Rental[].class);
        Arrays.asList(responses).forEach(
                response -> {
                    assertThat(response.getRentalStartDate()).isEqualTo(rental.getRentalStartDate());
                    assertThat(response.getAgreedReturnDate()).isEqualTo(rental.getAgreedReturnDate());
                    assertThat(response.getActualReturnedDate()).isEqualTo(rental.getActualReturnedDate());
                    assertThat(response.getId()).isEqualTo(rental.getId());
                    assertThat(response.getTotalFee()).isEqualTo(rental.getTotalFee());
                    assertThat(response.getRentalAgreementReference()).isEqualTo(rental.getRentalAgreementReference());
                    assertThat(response.getBook()).isEqualToComparingFieldByFieldRecursively(rental.getBook());
                    assertThat(response.getCustomer()).isEqualToComparingFieldByFieldRecursively(rental.getCustomer());
                });
    }

}
