package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.dao.PassengerDAO;
import com.bombanya.javaschool_railway.entities.Passenger;
import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @Mock
    private PassengerDAO daoMock;
    @InjectMocks
    private PassengerService serviceWithMocks;
    private Passenger regularPassenger;

    @BeforeEach
    void setUp() {
        regularPassenger = Passenger.builder()
                .name("Ivan")
                .surname("Ivanov")
                .birthDate(LocalDate.now())
                .passportId("123")
                .id(666)
                .build();
    }

    @Test
    void testGetPassengerCodeValidExistentUser() {
        Mockito.when(daoMock.findByPassport("123"))
                .thenReturn(Optional.of(regularPassenger));
        ServiceAnswer<Integer> result = serviceWithMocks.getPassengerCode(regularPassenger);
        assertTrue(result.isSuccess());
        assertEquals(666, result.getServiceResult());
        Mockito.verify(daoMock, Mockito.only()).findByPassport("123");
    }

    @Test
    void testGetPassengerCodeInvalidUser() {
        Passenger enteredInvalidUser = Passenger.builder()
                .name("Oleg")
                .surname("Olegov")
                .birthDate(LocalDate.now())
                .passportId("123")
                .id(1)
                .build();
        Mockito.when(daoMock.findByPassport("123"))
                .thenReturn(Optional.of(regularPassenger));
        ServiceAnswer<Integer> result = serviceWithMocks.getPassengerCode(enteredInvalidUser);
        assertFalse(result.isSuccess());
    }

    @Test
    void testGetPassengerNewPassenger() {
        Passenger newUser = Passenger.builder()
                .name("Oleg")
                .surname("Olegov")
                .birthDate(LocalDate.now())
                .passportId("1")
                .id(1)
                .build();
        Mockito.when(daoMock.findByPassport("1"))
                .thenReturn(Optional.empty());
        ServiceAnswer<Integer> result = serviceWithMocks.getPassengerCode(newUser);
        assertTrue(result.isSuccess());
        assertEquals(1, result.getServiceResult());
    }
}