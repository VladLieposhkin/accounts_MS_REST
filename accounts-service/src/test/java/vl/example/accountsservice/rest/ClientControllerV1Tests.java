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
import vl.example.accountscommon.dto.ClientDTO;
import vl.example.accountscommon.dto.enums.Status;
import vl.example.accountsservice.service.ClientService;
import vl.example.accountsservice.validator.ClientValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ClientRestControllerV1.class)
class ClientControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientValidator clientValidator;

    @Test
    void givenClientToCreateDTO_whenCreate_thenSuccessResponse() throws Exception{
        // given
        ClientDTO clientToCreateDTO = ClientDTO.builder()
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .build();
        ClientDTO createdClientDTO = ClientDTO.builder()
                .id(1)
                .name("TEST_CLIENT")
                .email("TC@mail.com")
                .status(Status.ACTIVE)
                .build();
        BDDMockito.doNothing().when(clientValidator).validate(any(ClientDTO.class), any(Errors.class));
        BDDMockito.given(clientService.create(any(ClientDTO.class))).willReturn(createdClientDTO);
        // when
        ResultActions result = mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientToCreateDTO)));
        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("TEST_CLIENT")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("TC@mail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("ACTIVE")));
    }
}
