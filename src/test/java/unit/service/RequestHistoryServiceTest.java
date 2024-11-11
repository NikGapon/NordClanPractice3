package unit.service;

import com.nordclan.employees.entity.RequestHistory;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.mapper.RequestHistoryMapper;
import com.nordclan.employees.repository.RequestHistoryRepository;
import com.nordclan.employees.service.RequestHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static com.nordclan.employees.entity.TrainingRequestStatus.AWAITING_PROCESSING;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestHistoryServiceTest {
    @Mock
    private RequestHistoryRepository requestHistoryRepository;

    @Mock
    private RequestHistoryMapper requestHistoryMapper;

    @InjectMocks
    private RequestHistoryService requestHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(requestHistoryService);

    }


    @Test
    void createRequestHist() throws Exception{
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");

        TrainingRequest trainingRequest = new TrainingRequest(
                1L, userId, userId, userId, userId, 1L, AWAITING_PROCESSING, LocalDate.now(),
                LocalDate.now(), LocalDate.now().plusDays(1), false, 1L
        );
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequestId(1L);

        when(requestHistoryRepository.save(any())).thenReturn(requestHistory);

        requestHistoryService.createRequestHist(trainingRequest, userId);
        verify(requestHistoryRepository, times(4)).save(any(RequestHistory.class));
        //verify(requestHistory, times(1)).getRequestId();
        verify(requestHistoryRepository, times(4)).save(argThat(request -> request.getRequestId().equals(1L)));
    }
    @Test
    void createRequestHist_not() {
        // Arrange
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");

        TrainingRequest trainingRequest = new TrainingRequest(
                2L, userId, userId, userId, userId, 1L, AWAITING_PROCESSING, LocalDate.now(),
                LocalDate.now(), LocalDate.now().plusDays(1), false, 1L
        );
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setRequestId(1L);

        when(requestHistoryRepository.save(any())).thenReturn(requestHistory);
        requestHistoryService.createRequestHist(trainingRequest, userId);

        verify(requestHistoryRepository, times(4)).save(any(RequestHistory.class));

        assertThrows(AssertionError.class, () -> {
            verify(requestHistoryRepository, times(4)).save(argThat(request -> request.getRequestId().equals(1L)));
        });
    }

}