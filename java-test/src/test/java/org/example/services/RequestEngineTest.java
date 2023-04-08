package org.example.services;

import org.example.vm.Desktop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RequestEngineTest {
    @Mock
    AuthorisingService authorisingServiceMock;
    @Mock
    SystemBuildService systemBuildServiceMock;

    RequestEngine newRequest;
    Desktop windows;

//    @InjectMocks
//    RequestEngine newRequest = new RequestEngine();

    @BeforeEach
    void setUp() {
        authorisingServiceMock = mock(AuthorisingService.class);
        systemBuildServiceMock = mock(SystemBuildService.class);
        newRequest = new RequestEngine(authorisingServiceMock, systemBuildServiceMock);
        windows = mock(Desktop.class);
        windows = new Desktop("hostname", "Emma", 4, 8,
                1,10, "123");
       // MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {


    }

    @Test
    void createNewRequestDoesNotThrowExceptionsTest() throws UserNotEntitledException, MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(windows.getRequester())).thenReturn(true);
        when(systemBuildServiceMock.createNewMachine(windows)).thenReturn("Windows");


        assertDoesNotThrow(()-> newRequest.createNewRequest(windows));
        assertTrue(newRequest.totalBuildsByUserForDay().size() > 0);
    }

    @Test
    void createNewRequestThrowsMachineNotCreatedExceptionTest() throws MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(windows.getRequester())).thenReturn(true);
        when(systemBuildServiceMock.createNewMachine(windows)).thenReturn(null);
        assertThrows(MachineNotCreatedException.class, ()-> newRequest.createNewRequest(windows));
    }
    @Test
    void createNewRequestMachineThrowsUserNotEntitledExceptionTest() throws UserNotEntitledException, MachineNotCreatedException{
        when(authorisingServiceMock.isAuthorised(windows.getRequester())).thenReturn(false);
        assertThrows(UserNotEntitledException.class, ()-> newRequest.createNewRequest(windows));
    }



    @Test
    void totalBuildsByUserForDay() throws MachineNotCreatedException, UserNotEntitledException {
        createNewRequestDoesNotThrowExceptionsTest();
        verify(systemBuildServiceMock, times(2)).createNewMachine(windows);
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
        assertTrue(newRequest.numberOfMachinesForUserPerMachine("Emma", windows.getName()) == 2);
    }
}