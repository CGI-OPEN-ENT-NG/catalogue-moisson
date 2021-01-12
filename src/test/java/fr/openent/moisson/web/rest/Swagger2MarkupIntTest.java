package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;

import io.swagger.models.Swagger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.staticdocs.SwaggerResultHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoissoncatalogueApp.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev,s2m")
public class Swagger2MarkupIntTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .build();
    }

    @Test
    public void convertSwaggerToJSON() throws Exception {
        this.mockMvc.perform(get("/v2/api-docs")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(SwaggerResultHandler.outputDirectory("target/swagger")
            .build())
            .andExpect(status().isOk());
    }
}
