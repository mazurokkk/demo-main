package alla.verkhohliadova.demo_car.config;

import alla.verkhohliadova.demo_car.security.JwtConfigure;
import alla.verkhohliadova.demo_car.security.JwtTokenTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
        //http.csrf().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // для незареєстрованих користувачів
                .antMatchers("/newUser").permitAll()
                .antMatchers("/login", "users/registered").permitAll()
                //для зареєстрованих
                .antMatchers(HttpMethod.POST, "/users/in/**").permitAll()
                .antMatchers(HttpMethod.GET, "/favourites/**").permitAll()//hasAnyRole("ADMIN","USER")
                // длф адміна
                .antMatchers(HttpMethod.GET, "/admin/in/**").hasAnyRole("ADMIN")
                //.antMatchers(HttpMethod.POST, "/admin/**").permitAll()
                //.antMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN")
                //.antMatchers(HttpMethod.POST, "/user/login", "/user/register").permitAll()
                .antMatchers(HttpMethod.POST).permitAll() ////////////////////
                //.antMatchers(HttpMethod.GET, "/user/checkToken").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET).permitAll()
                //.antMatchers(HttpMethod.PUT).permitAll()
                .antMatchers(HttpMethod.DELETE, "/delete/**").permitAll()
                //.antMatchers("/img/**").permitAll()
                //.anyRequest().hasAnyRole("ADMIN")
                .and()
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
                .apply(new JwtConfigure(jwtTokenTool));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
