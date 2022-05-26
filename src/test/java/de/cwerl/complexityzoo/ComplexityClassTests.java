package de.cwerl.complexityzoo;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.model.NormalComplexityClass;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ComplexityClassTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ComplexityClassRepository classRepository;

	// Attribute keywords
	private static final String ATTR_CLASSLIST = "classes";
	private static final String ATTR_CLASS = "class";
	private static final String ATTR_ID = "id";
	private static final String ATTR_CLASSNAME = "name";
	private static final String ATTR_DESCR = "description";

	@Test
	@Transactional
	public void viewClassTest() throws Exception {
		ComplexityClass c = new NormalComplexityClass();
		c.setName("A");
		classRepository.save(c);

		this.mockMvc.perform(
			get("/classes/{id}", c.getId()))
			.andExpect(status().isOk());
	}

    @Test
	@Transactional
    public void createClassTest() throws Exception {
        this.mockMvc.perform(
            post("/classes/new/save")
			.param(ATTR_CLASSNAME, "name")
			.param(ATTR_DESCR, "description")
		);
		this.mockMvc.perform(get("/classes"))
		.andExpect(model().attribute(ATTR_CLASSLIST, hasItem(
			allOf(
				hasProperty(ATTR_CLASSNAME, is("name")),
				hasProperty(ATTR_DESCR, is("description"))
			)
		)))
		.andExpect(status().isOk());
    }

	@Test
	@Transactional
    public void deleteClassFilledDatabaseTest() throws Exception {
		ComplexityClass c1 = new NormalComplexityClass();
		c1.setName("name1");
		classRepository.save(c1);

		ComplexityClass c2 = new NormalComplexityClass();
		c2.setName("name2");
		classRepository.save(c2);

        this.mockMvc.perform(
            MockMvcRequestBuilders.delete("/classes/{id}/edit/delete", c1.getId())
		).andExpect(status().is(302));

		this.mockMvc.perform(get("/classes"))
		.andExpect(model().attribute(ATTR_CLASSLIST, not(hasItem(
			hasProperty(ATTR_ID, is(c1.getId()))
		))))
		.andExpect(model().attribute(ATTR_CLASSLIST, hasItem(
			hasProperty(ATTR_ID, is(c2.getId()))
		)))
		.andExpect(status().isOk());
    }

	@Test
	@Transactional
	public void searchClassTest() throws Exception {
		ComplexityClass c1 = new NormalComplexityClass();
		c1.setName("A");
		classRepository.save(c1);

		ComplexityClass c2 = new NormalComplexityClass();
		c2.setName("B");
		classRepository.save(c2);

		this.mockMvc.perform(get("/classes/search?q=A"))
		.andExpect(model().attribute(ATTR_CLASSLIST, hasItem(
			hasProperty(ATTR_CLASSNAME, is(c1.getName()))
		)))
		.andExpect(model().attribute(ATTR_CLASSLIST, not(hasItem(
			hasProperty(ATTR_CLASSNAME, is(c2.getName()))
		))))
		.andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void editClassTest() throws Exception {
		ComplexityClass c = new NormalComplexityClass();
		c.setName("name");
		c.setDescription("old");
		classRepository.save(c);

		this.mockMvc.perform(get("/classes/{id}/edit", c.getId()))
		.andExpect(model().attribute(ATTR_CLASS, allOf(
			hasProperty(ATTR_DESCR, is("old"))
			)));
			
		this.mockMvc.perform(post("/classes/{id}/edit/save", c.getId()).param(ATTR_DESCR, "new"));

		this.mockMvc.perform(get("/classes/{id}", c.getId()))
		.andExpect(model().attribute(ATTR_CLASS, allOf(
			hasProperty(ATTR_DESCR, is("new"))
			)));
	}
}