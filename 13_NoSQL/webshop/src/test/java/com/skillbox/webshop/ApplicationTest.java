package com.skillbox.webshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
abstract class ApplicationTest {

	/** Web application context. */
	@Autowired
	protected WebApplicationContext ctx;

	/** Mock mvc. */
	protected MockMvc mockMvc;

	/** Object mapper. */
	@Autowired
	protected ObjectMapper mapper;

	/**
	 * Create mock mvc.
	 */
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(ctx)
				.build();
	}



}
