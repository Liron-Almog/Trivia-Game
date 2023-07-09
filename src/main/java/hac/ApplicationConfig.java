package hac;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.SessionScope;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApplicationConfig {

    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

    @Bean
    @SessionScope
    public GameController sessionBeanControllerGame () {
        return new GameController(manager);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("useradmin")
                .password(bCryptPasswordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(withDefaults())

                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/images/**", "/css/**", "/", "/error","/login","/register","/adduser").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                                .requestMatchers("/shared/**").hasAnyRole("USER", "ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                               .defaultSuccessUrl("/shared/start-game", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedPage("/error")
                )

        ;

        return http.build();

    }


// instead of defining open path in the method above you can do it here:
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico");
    }

}
