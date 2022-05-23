package de.cwerl.complexityzoo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.cwerl.complexityzoo.model.ComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTCRelation;
import de.cwerl.complexityzoo.repository.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CTCRelationTests {

    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private CTCRelationRepository relationRepository;

    @Autowired
    private ComplexityClassRepository classRepository;

    @Test
    @Transactional
    public void createRelationTest() throws Exception {
        ComplexityClass c1 = new ComplexityClass();
        ComplexityClass c2 = new ComplexityClass();
        c1.setName("A");
        c2.setName("B");
        classRepository.save(c1);
        classRepository.save(c2);

        this.mockMvc.perform(
            post("/classes/{firstClassId}/new-relation/save", c1.getId())
            .param("secondClassId", "" + c2.getId())
            .param("type", "EQUAL_TO")
            .param("description", "description")
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{id}", c1.getId())
        )
        .andExpect(model().attribute("relations", hasItem(
            allOf(
                hasProperty("firstClass", hasProperty("id", is(c1.getId()))),
                hasProperty("secondClass", hasProperty("id", is(c2.getId())))
            )
        )))
        .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void selfRelationTest() throws Exception {
        ComplexityClass c = new ComplexityClass();
        c.setName("A");
        classRepository.save(c);

        this.mockMvc.perform(
            post("/classes/{firstClassId}/new-relation/save", c.getId())
            .param("secondClassId", "" + c.getId())
            .param("type", "EQUAL_TO")
            .param("description", "description")
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{id}", c.getId())
        )
        .andExpect(model().attribute("relations", not(hasItem(
            allOf(
                hasProperty("firstClass", hasProperty("id", is(c.getId()))),
                hasProperty("secondClass", hasProperty("id", is(c.getId())))
            )
        ))))
        .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteRelationTest() throws Exception {
        CTCRelation r = new CTCRelation();
        ComplexityClass c1 = new ComplexityClass();
        c1.setName("A");
        classRepository.save(c1);
        ComplexityClass c2 = new ComplexityClass();
        c2.setName("B");
        classRepository.save(c2);
        r.setFirstClass(c1);
        r.setSecondClass(c2);
        relationRepository.save(r);

        this.mockMvc.perform(
            delete("/classes/{classId}/relation/{relationId}/delete", c1.getId(), r.getId())
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{classId}", c1.getId())
        ).andExpect(model().attribute("relations", not(hasItem(
            hasProperty("id", is(r.getId()))
        ))));
    }
}
