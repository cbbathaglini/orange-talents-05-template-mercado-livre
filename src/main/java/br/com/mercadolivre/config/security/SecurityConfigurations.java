package br.com.mercadolivre.config.security;

import br.com.mercadolivre.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
//@Profile(value = {"prod", "test"})
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}


	/*@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("pgcil").secret("{noop}secret").scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(18000);
	}
	*/
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/**")
					.authorizeRequests()
					.antMatchers("/auth/**").permitAll()
					.antMatchers(HttpMethod.GET, "/categorias").permitAll()
					.antMatchers(HttpMethod.POST, "/usuarios").permitAll()
					.antMatchers(HttpMethod.GET, "/categorias/*").permitAll()
					.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
					.antMatchers(HttpMethod.GET, "/ranking-vendedores").permitAll()
					.antMatchers(HttpMethod.POST, "/ranking-vendedores").permitAll()
					.antMatchers(HttpMethod.GET, "/notafiscal").permitAll()
					.antMatchers(HttpMethod.POST, "/notafiscal").permitAll()
					.antMatchers(HttpMethod.DELETE, "/categorias/*").hasRole("MODERADOR")
					.antMatchers(HttpMethod.POST, "/produtos/*").hasRole("MODERADOR")
					.anyRequest().authenticated()
					.and().csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);


	}
	
	
	//Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/**");
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}


	
}
