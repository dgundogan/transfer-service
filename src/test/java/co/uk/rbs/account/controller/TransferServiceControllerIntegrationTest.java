package co.uk.rbs.account.controller;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferServiceControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenInvalidAccountId_whenCallAddOrderEndPoint_thenReturnsNotFound() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/request.json");

        mockMvc.perform(post("/api/accounts/000-111/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                )).andExpect(status().isNotFound());
    }

    @Test
    public void givenInvalidDestAccountId_whenCallAddOrderEndPoint_thenReturnsNotFound() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/invalid-dest-request.json");

        mockMvc.perform(post("/api/accounts/000-111-222/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                )).andExpect(status().isNotFound());
    }


    @Test
    public void givenInvalidAmount_whenCallAddOrderEndPoint_thenReturnsBadRequest() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/invalid-amount-request.json");

        mockMvc.perform(post("/api/accounts/000-111-222/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                )).andExpect(status().isBadRequest());
    }

    @Test
    public void givenBalanceLessThenAmount_whenCallAddOrderEndPoint_thenReturnsBadRequest() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/balance-less-amount-request.json");

        mockMvc.perform(post("/api/accounts/000-111-222/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                )).andExpect(status().isBadRequest());
    }

    @Test
    public void givenDto_whenCallAddOrderEndPoint_thenReturnsOk() throws Exception {
        Resource resource = new ClassPathResource("/fixtures/request.json");

        mockMvc.perform(post("/api/accounts/000-111-222/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(IOUtils.toString(resource.getInputStream(), "UTF-8")
                )).andExpect(status().isCreated());
    }
}