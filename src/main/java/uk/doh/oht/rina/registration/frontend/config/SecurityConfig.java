package uk.doh.oht.rina.registration.frontend.config;

/**
 * Created by peterwhitehead on 11/05/2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final List<String> NO_SECURITY_URLS = new ArrayList<>(
            Arrays.asList(
                    "/css/*",
                    "/fonts/*",
                    "/images/*",
                    "/javascripts/**",
                    "/js/*",
                    "/stylesheets/*",
                    "/stylesheets/images/*",
                    "/public/**/*",
                    "/public",
                    "/public/**",
                    "/swagger-ui.html",
                    "/v2/**",
                    "/login"
            ));

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String[] unsecuredUrls = getUnsecuredUrls();
        http.authorizeRequests().antMatchers(unsecuredUrls).permitAll().antMatchers("/*/**").hasAnyRole("ADMIN", "USER").and().formLogin().loginPage("/login").defaultSuccessUrl("/home");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ramesh").password("ramesh123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("june").password("june123").roles("USER");
        auth.inMemoryAuthentication().withUser("heather").password("heather123").roles("USER");
    }

    private String[] getUnsecuredUrls() {
        return getEndPoints();
    }

    private String[] getEndPoints() {
        return NO_SECURITY_URLS.toArray(new String[NO_SECURITY_URLS.size()]);
    }
}
