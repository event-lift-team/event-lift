package pl.sda.eventlift.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/events").permitAll()
                .antMatchers("/events-browser").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().permitAll()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("loginEmail")
                .passwordParameter("loginPassword")
                .loginProcessingUrl("/processLogin")
                .failureUrl("/login?error=1")
                .defaultSuccessUrl("/events");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .usersByUsernameQuery(
                        "SELECT s.email, s.password_hash, 1 " +
                                "FROM Stakeholders s " +
                                "WHERE s.email = ?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT s.email, r.role_name, 1 " +
                                "FROM stakeholders s " +
                                "JOIN stakeholders_roles sr ON s.id = sr.stakeholder_id " +
                                "JOIN roles r ON sr.role_id = r.id " +
                                "WHERE s.email = ? "
                )
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource);
    }
}
