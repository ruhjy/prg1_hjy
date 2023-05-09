package com.example.demo.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

import jakarta.annotation.*;
import jakarta.servlet.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.s3.*;

@Configuration
@EnableMethodSecurity
public class CustomConfiguration {

	@Value("${aws.accessKey}")
	private String accessKey;

	@Value("${aws.secretAccessKey}")
	private String secretAccessKey;

	@Value("${aws.bucketUrl}")
	private String bucketUrl;

	@Autowired
	private ServletContext application;

	@PostConstruct
	public void init() {
		application.setAttribute("bucketUrl", bucketUrl);
	}

	@Bean
	public S3Client s3Client() {

		AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
		AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

		S3Client s3Client = S3Client.builder()
				.credentialsProvider(provider)
				.region(Region.AP_NORTHEAST_2)
				.build();

		return s3Client;
	}

	// ======= 시큐리티 등록 =======
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
//		httpSecurity.formLogin(Customizer.withDefaults());

		httpSecurity.formLogin().loginPage("/member/login");
		httpSecurity.logout().logoutUrl("/member/logout");

//		httpSecurity.authorizeHttpRequests().requestMatchers("/add").authenticated();
//		httpSecurity.authorizeHttpRequests().requestMatchers("/member/signup").anonymous();
//		httpSecurity.authorizeHttpRequests().requestMatchers("/**").permitAll();
		
		// Expression-Based Access Control
//		httpSecurity.authorizeHttpRequests()
//				.requestMatchers("/add")
//				.access(new WebExpressionAuthorizationManager("isAuthenticated()"));
//
//		httpSecurity.authorizeHttpRequests()
//			.requestMatchers("/member/signup")
//			.access(new WebExpressionAuthorizationManager("isAnonymous()"));
//		
//		httpSecurity.authorizeHttpRequests()
//			.requestMatchers("/**")
//			.access(new WebExpressionAuthorizationManager("permitAll"));
		
		return httpSecurity.build();
	}

}
