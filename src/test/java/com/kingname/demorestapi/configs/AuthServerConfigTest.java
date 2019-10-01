package com.kingname.demorestapi.configs;

import com.kingname.demorestapi.accounts.Account;
import com.kingname.demorestapi.accounts.AccountRole;
import com.kingname.demorestapi.accounts.AccountService;
import com.kingname.demorestapi.common.BaseControllerTest;
import com.kingname.demorestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {

        String email = "limgeun@eamil.com";
        String password = "limgeun";

        Account limgeun = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.ADDMIN, AccountRole.USER))
                .build();
        this.accountService.saveAccount(limgeun);

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password")
            )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }
}