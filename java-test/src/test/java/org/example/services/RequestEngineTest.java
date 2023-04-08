package org.example.services;

import org.example.employees.Employees;
import org.example.vm.Desktop;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RequestEngineTest {
    @Mock
    AuthorisingService authorisingServiceMock;
    @Mock
    Desktop desktop;
    @Mock
    Employees employees;
    @Mock SystemBuildService systemBuildServiceMock;

    @InjectMocks
    RequestEngine newRequest = new RequestEngine();

    @BeforeEach
    void setUp() {
        employees = mock(Employees.class);
        desktop = mock(Desktop.class);
        newRequest.setUserLogonName("Emma");

        MockitoAnnotations.openMocks(this);

        when(employees.isEntitlement()).thenReturn(true);
    }

    @AfterEach
    void tearDown() {


    }

    @Test
    void createNewRequestDoesNotThrowExceptionsTest() throws UserNotEntitledException, MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(newRequest.getUserLogonName())).thenReturn(true);
        when(desktop.getName()).thenReturn("Windows");
        when(systemBuildServiceMock.createNewMachine(desktop)).thenReturn("Windows" +
                "");


        assertDoesNotThrow(()-> newRequest.createNewRequest(desktop));
        assertTrue(newRequest.totalBuildsByUserForDay().size() > 0);
    }

    @Test
    void createNewRequestThrowsMachineNotCreatedExceptionTest() throws MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(newRequest.getUserLogonName())).thenReturn(true);
        when(systemBuildServiceMock.createNewMachine(desktop)).thenReturn(null);
        assertThrows(MachineNotCreatedException.class, ()-> newRequest.createNewRequest(desktop));
    }
    @Test
    void createNewRequestMachineThrowsUserNotEntitledExceptionTest() throws UserNotEntitledException, MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(newRequest.getUserLogonName())).thenReturn(false);
        assertThrows(UserNotEntitledException.class, ()-> newRequest.createNewRequest(desktop));
    }



    @Test
    void totalBuildsByUserForDay() throws MachineNotCreatedException, UserNotEntitledException {
        createNewRequestDoesNotThrowExceptionsTest();
        verify(systemBuildServiceMock, times(2)).createNewMachine(desktop);
        assertTrue(newRequest.totalBuildsByUserForDay().size() == 1);
    }

    @Test
    void totalFailedBuildsForDay() throws MachineNotCreatedException{
        createNewRequestThrowsMachineNotCreatedExceptionTest();
        assertTrue(newRequest.totalFailedBuildsForDay() == 1);

    }

    @Test
    void numberOfMachinesForUserPerMachineTest() throws MachineNotCreatedException, UserNotEntitledException {
        createNewRequestDoesNotThrowExceptionsTest();
        createNewRequestDoesNotThrowExceptionsTest();
        assertTrue(newRequest.numberOfMachinesForUserPerMachine("Emma", desktop.getName()) == 2);
    }
}