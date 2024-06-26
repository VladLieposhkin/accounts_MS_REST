package vl.example.accountsservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.Errors;
import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.service.CoinService;
import vl.example.accountsservice.validator.CoinValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CoinRestControllerV1.class)
class CoinControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoinService coinService;

    @MockBean
    private CoinValidator coinValidator;

    @Test
    public void givenCoinToCreateDTO_whenCreate_thenSuccessResponse() throws Exception {
        // given
        CoinDTO coinToCreateDTO = CoinDTO.builder()
                .code("100")
                .name("TEST_COIN")
                .build();
        CoinDTO createdCoinDTO = CoinDTO.builder()
                .id(1)
                .code("100")
                .name("TEST_COIN")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.doNothing().when(coinValidator).validate(any(CoinDTO.class), any(Errors.class));
        BDDMockito.given(coinService.create(any(CoinDTO.class)))
                .willReturn(createdCoinDTO);
        // when
        ResultActions result = mockMvc.perform(post("/api/v1/coins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coinToCreateDTO)));
        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is("100")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("TEST_COIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }
}
