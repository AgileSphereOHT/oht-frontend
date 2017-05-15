package uk.doh.oht.frontend.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by peterwhitehead on 04/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SecurityContext securityContextMocked;
    @Mock
    private Authentication authenticationMocked;
    @Mock
    private org.springframework.security.core.userdetails.UserDetails principal;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new HomeController())
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        given(authenticationMocked.getPrincipal()).willReturn(principal);
        given(securityContextMocked.getAuthentication()).willReturn(authenticationMocked);
        SecurityContextHolder.setContext(securityContextMocked);
    }

    @Test
    public void testDisplayRetrieveNextCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(handler().methodName("displayRetrieveNextCase"))
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(status().is2xxSuccessful());
    }

}