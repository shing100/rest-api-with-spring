package com.kingname.demorestapi.index;

import com.kingname.demorestapi.common.BaseControllerTest;
import org.junit.Test;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class indexControllerTest extends BaseControllerTest {

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/api"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("_links.events").exists())
                    .andDo(document("index",
                            links(
                                    linkWithRel("events").description("이벤트를 조회할 수 있는 링크")
                            )
                    ));
    }
}
