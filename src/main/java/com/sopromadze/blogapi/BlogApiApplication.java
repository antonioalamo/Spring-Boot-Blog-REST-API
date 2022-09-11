package com.sopromadze.blogapi;

import com.sopromadze.blogapi.security.JwtAuthenticationFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.TimeZone;
import static springfox.documentation.builders.PathSelectors.regex;


@SpringBootApplication
@EntityScan(basePackageClasses = { BlogApiApplication.class, Jsr310Converters.class })

/**
 *
 */
@Configuration
@EnableSwagger2
public class BlogApiApplication extends SpringBootServletInitializer {

	private final String AUTH_SERVER ="https://libertychile.auth0.com/oauth";
	private final String CLIENT_ID = "XtPP5DZ8cqW7QU5BdGFxmoz0Ebxi3ju3";
	private final String CLIENT_SECRET = "F7t9pDXj9ohge0qgdq3o8ROyUXeBDFYw39JWkD_YxyOC8uacKThyiIKMVYy81pYh";


	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Bean
    public Docket swaggerApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sopromadze.blogapi"))
				// .apis(RequestHandlerSelectors.any())
                .paths(regex("/.*"))
                .build()
				.securitySchemes(Arrays.asList(securityScheme()))
		 		.securityContexts(Arrays.asList(securityContext()))
                .apiInfo(new ApiInfoBuilder().version("1.0").title("API").description("Documentation API V1").build());
    }

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new AuthorizationCodeGrantBuilder()
			.tokenEndpoint(new TokenEndpoint( AUTH_SERVER + "/token", "oauthtoken"))
			.tokenRequestEndpoint(
			  new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
			.build();
	
		SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
			.grantTypes(Arrays.asList(grantType))
			.scopes(Arrays.asList(scopes()))
			.build();
		return oauth;
	}


	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopes = { 
		  new AuthorizationScope("read", "for read operations"), 
		  new AuthorizationScope("write", "for write operations"), 
		  new AuthorizationScope("foo", "Access foo API") };
		return scopes;
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
		  .securityReferences(
			Arrays.asList(new SecurityReference("spring_oauth", scopes())))
		  .forPaths(PathSelectors.regex("/.*"))
		  .build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
