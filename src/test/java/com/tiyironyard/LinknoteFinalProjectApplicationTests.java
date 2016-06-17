package com.tiyironyard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LinknoteFinalProjectApplication.class)
@WebAppConfiguration
public class LinknoteFinalProjectApplicationTests {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}


	@Test
	public void testAddNote() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/add-note")
				.param("inputText", "THIS IS A TEST NOTE")
		);

		Assert.assertTrue(noteRepository.count() == 1);
	}





	/**
	 * Given: database, note and tag tables
	 * When: a new note and tag is input
	 * Then: they exist in the database
	 */



}
