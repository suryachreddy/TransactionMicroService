package com.sbi.transactions.repository;

import com.sbi.transactions.model.BalanceEnquiryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BalanceEnquiryRepositoryTest {

    @Mock
    private BalanceEnquiryRepository repository;

    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByAccountNumber() {
        long accountNumber = 12345L;
        BalanceEnquiryEntity entity = new BalanceEnquiryEntity();
        entity.setAccountNumber(accountNumber);

        when(repository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(entity));

        Optional<BalanceEnquiryEntity> result = repository.findByAccountNumber(accountNumber);

        assertTrue(result.isPresent());
        assertEquals(accountNumber, result.get().getAccountNumber());
    }

    @Test
    public void testFindByEmail() {
        BalanceEnquiryEntity entity = new BalanceEnquiryEntity();
        entity.setEmail(EMAIL);

        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(entity));

        Optional<BalanceEnquiryEntity> result = repository.findByEmail(EMAIL);

        assertTrue(result.isPresent());
        assertEquals(EMAIL, result.get().getEmail());
    }

    @Test
    public void testDeleteByEmail() {
        doNothing().when(repository).deleteByEmail(EMAIL);
        repository.deleteByEmail(EMAIL);

        verify(repository, times(1)).deleteByEmail(EMAIL);
    }
}
