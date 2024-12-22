package servicelog.log.service_log.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import servicelog.controller.LogController;
import servicelog.model.ShuffleRequest;
import servicelog.service.LogService;

import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LogControllerTests {
    private static final String LOG_URL = "/api/log";

    @Mock
    private LogService logService;

    @InjectMocks
    private LogController logController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build();
    }

    @Test
    void shouldLogRequestAndReturnOkWhenValidRequestProvided() throws Exception {
        ShuffleRequest request = new ShuffleRequest(List.of(1, 2, 3, 4));

        mockMvc.perform(post(LOG_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shuffledArray", hasSize(4)))
                .andExpect(jsonPath("$.shuffledArray[0]").value(1));

        verify(logService, times(1)).logRequest(any(ShuffleRequest.class));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidRequestProvided() throws Exception {
        String invalidRequest = """
                                {
                                    "shuffledArray": 0
                                }
                                """;

        mockMvc.perform(post(LOG_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(logService);
    }

    @Test
    void shouldLogMessageWhenRequestIsProcessed() throws Exception {
        ShuffleRequest request = new ShuffleRequest(List.of(5, 6, 7));

        mockMvc.perform(post(LOG_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(logService).logRequest(request);
    }
}

