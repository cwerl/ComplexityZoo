package de.cwerl.complexityzoo;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ComplexityClassTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ComplexityClassRepository classRepository;

    @Test
	@Transactional
    public void createClassTest() throws Exception {
        this.mockMvc.perform(
            post("/classes/add")
			.param("name", "testName")
			.param("description", "testDescription")
		);
		this.mockMvc.perform(get("/classes"))
		.andExpect(model().attribute("classes", hasItem(
			allOf(
				hasProperty("name", is("testName")),
				hasProperty("description", is("testDescription"))
			)
		)));
    }

	@Test
	@Transactional
    public void deleteClassFilledDatabaseTest() throws Exception {
		ComplexityClass c1 = new ComplexityClass();
		c1.setName("name");
		classRepository.save(c1);

		ComplexityClass c2 = new ComplexityClass();
		c2.setName("name");
		classRepository.save(c2);

        this.mockMvc.perform(
            MockMvcRequestBuilders.delete("/classes/{id}/delete", c1.getId())
		);
		this.mockMvc.perform(get("/classes"))
		.andExpect(model().attribute("classes", not(hasItem(
			hasProperty("id", is(c1.getId()))
		))))
		.andExpect(model().attribute("classes", hasItem(
			hasProperty("id", is(c2.getId()))
		)));
    }
}