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

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.normal.NormalComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTCRelation;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CTCRelationTests {

    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private CTCRelationRepository relationRepository;

    @Autowired
    private ComplexityClassRepository classRepository;

    @Test
    public void createRelationTest() throws Exception {
        ComplexityClass c1 = new NormalComplexityClass();
        ComplexityClass c2 = new NormalComplexityClass();
        c1.setName("A");
        c2.setName("B");
        classRepository.save(c1);
        classRepository.save(c2);

        this.mockMvc.perform(
            post("/ctc-relations/new/save")
            .param("firstClassId", "" + c1.getId())
            .param("secondClassId", "" + c2.getId())
            .param("relationType", "EQUAL_TO")
            .param("reference", "description")
            .param("redirect", "")
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{id}", c1.getId())
        )
        .andExpect(model().attribute("ctcRelations", hasItem(
            allOf(
                hasProperty("firstClass", hasProperty("id", is(c1.getId()))),
                hasProperty("secondClass", hasProperty("id", is(c2.getId())))
            )
        )))
        .andExpect(status().isOk());
    }

    @Test
    public void selfRelationTest() throws Exception {
        ComplexityClass c = new NormalComplexityClass();
        c.setName("A");
        classRepository.save(c);

        this.mockMvc.perform(
            post("/ctc-relations/new/save")
            .param("firstClassId", "" + c.getId())
            .param("secondClassId", "" + c.getId())
            .param("relationType", "EQUAL_TO")
            .param("reference", "description")
            .param("redirect", "")
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{id}", c.getId())
        )
        .andExpect(model().attribute("ctcRelations", not(hasItem(
            allOf(
                hasProperty("firstClass", hasProperty("id", is(c.getId()))),
                hasProperty("secondClass", hasProperty("id", is(c.getId())))
            )
        ))))
        .andExpect(status().isOk());
    }

    @Test
    public void deleteRelationTest() throws Exception {
        CTCRelation r = new CTCRelation();
        ComplexityClass c1 = new NormalComplexityClass();
        c1.setName("A");
        classRepository.save(c1);
        ComplexityClass c2 = new NormalComplexityClass();
        c2.setName("B");
        classRepository.save(c2);
        r.setFirstClass(c1);
        r.setSecondClass(c2);
        relationRepository.save(r);

        this.mockMvc.perform(
            delete("/ctc-relations/{relationId}/delete", r.getId())
            .param("redirect", "")
        ).andExpect(status().is3xxRedirection());

        this.mockMvc.perform(
            get("/classes/{classId}", c1.getId())
        ).andExpect(model().attribute("ctcRelations", not(hasItem(
            hasProperty("id", is(r.getId()))
        ))));
    }
}
