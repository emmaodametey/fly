package org.example.services;

import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorisingServiceTest {
    AuthorisingService auth;
    @Mock
    AuthorisingService authorisingServiceMock;



    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        authorisingServiceMock = mock(AuthorisingService.class);
        when(authorisingServiceMock.isAuthorised(anyString())).thenReturn(true);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void isAuthorised() {
        assertDoesNotThrow(()-> auth.isAuthorised("emma"));

    }
}