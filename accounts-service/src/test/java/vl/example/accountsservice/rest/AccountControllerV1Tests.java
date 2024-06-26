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
import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.service.AccountService;
import vl.example.accountsservice.validator.AccountValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AccountRestControllerV1.class)
class AccountControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountValidator accountValidator;

    @Test
    void givenAccountToCreateDTO_whenCrete_thenSuccessResponse() throws Exception {
        // given
        AccountDTO accountToCreateDTO = AccountDTO.builder()
                .number("TEST_ACCOUNT")
                .build();
        AccountDTO cteatedAccountDTO = AccountDTO.builder()
                .id(1)
                .number("TEST_ACCOUNT")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.doNothing().when(accountValidator).validate(any(ClientDTO.class), any(Errors.class));
        BDDMockito.given(accountService.create(any(AccountDTO.class))).willReturn(cteatedAccountDTO);
        // when
        ResultActions result = mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountToCreateDTO)));
        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number", CoreMatchers.is("TEST_ACCOUNT")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }
}
