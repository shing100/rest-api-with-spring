package com.kingname.demorestapi.configs;

import com.kingname.demorestapi.accounts.AccountService;
import com.kingname.demorestapi.common.BaseControllerTest;
import com.kingname.demorestapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() {

    }
}