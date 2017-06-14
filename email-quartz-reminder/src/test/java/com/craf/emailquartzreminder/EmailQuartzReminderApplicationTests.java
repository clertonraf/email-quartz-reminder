package com.craf.emailquartzreminder;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class EmailQuartzReminderApplicationTests {

	@Autowired private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }
	
	@Test
	public void contextLoads() throws Exception {
		MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/reminders/v1/1234/1");
		
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.hour", is(22)));
	}

}
