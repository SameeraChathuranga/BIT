package lk.PremasiriBrothers.inventorymanagement.config;


import lk.PremasiriBrothers.inventorymanagement.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();*/

        //for developing easy to give permission all link

        http  //To display pdf in same html page i frame
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .authorizeRequests()
                //Always users can access without login
                .antMatchers(
                        "/index",
                        "/favicon.ico",
                        "/img/**",
                        "/css/**",
                        "/js/**",
                        "/fonts/**",
                        "/divTool/**",
                        "/fontawesome/**").permitAll()
                .antMatchers("/login", "/select/**").permitAll()

                //Need to login for access those are
                .antMatchers("/employee/**").hasRole("MANAGER")
                .antMatchers("/employee/**").hasRole("MANAGER")
                .antMatchers("/user/**").hasRole("MANAGER")
                .antMatchers("/user/**").hasAnyRole("MANAGER", "CASHIER")
                .antMatchers("/invoiceProcess/add").hasRole("CASHIER")
                .anyRequest()
                .authenticated()
                .and()
                // Login form
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/mainWindow")
                //Username and password for validation
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                //Logout controlling
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .and()
                .exceptionHandling()
                //Cross site disable
                .and()
                .csrf()
                .disable();
    }
}
