package com.craf.emailquartzreminder;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
	public void test01_getReminder() throws Exception {
		MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/reminders/v1/1234/1");
		
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.hour", is(22)));
	}
	
	@Test
	public void test02_schedule() throws Exception {
		String content = "{\"day\": 14,"
						+ "\"emailDestination\": \"clerton.filho@softplan.com.br\","
						+ "\"eventLink\": \"http://www.google.com.br\","
						+ "\"eventName\": \"Event XYZ\","
						+ "\"hour\": 12,"
						+ "\"interval\": 1,"
						+ "\"minute\": 59,"
						+ "\"month\": 6,"
						+ "\"reminderId\": null,"
						+ "\"unit\": \"m\","
						+ "\"year\": 2017}";
		
		MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/reminders/v1/1234/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
		
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
