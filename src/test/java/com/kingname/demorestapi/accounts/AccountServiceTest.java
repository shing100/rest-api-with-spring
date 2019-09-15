package com.kingname.demorestapi.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByUsername() {

        String username = "limgeun@email.com";
        String password = "geun";
        Set roles = new TreeSet();
        roles.add(AccountRole.ADDMIN);
        roles.add(AccountRole.USER);
        //Given
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(roles)
                .build();
        this.accountRepository.save(account);

        //When
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    @Test
    public void findByUsernameFail() {
        String username = "random@email.com";
        try {
            accountService.loadUserByUsername(username);
            fail("supposed to be failed");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage().contains(username));
        }
    }

    @Test
    public void findByUsernameFail_expect() {
        String username = "random@email.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        accountService.loadUserByUsername(username);
    }
}
