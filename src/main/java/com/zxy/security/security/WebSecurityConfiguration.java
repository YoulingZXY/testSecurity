package com.zxy.security.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private DataSource dataSource;
	
	/**定义认证用户信息获取来源，密码校验规则等*/ 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		/**从内存中获取，用户名，密码，角色*/
//		auth.inMemoryAuthentication().withUser("lalala").password("123456").roles("User");
		
		/**
		 * 演示加密算法，不安全。
		 */
//		UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		
		/**
		 *密码加密工具，必须在数据入库前对密码进行加密，
		 *因为在登陆校验时会把用户输入的密码进行加密算法转换后，再与数据库中已经加密好的密码进行比对。
		 */
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		auth
		.jdbcAuthentication()
			.dataSource(dataSource);
//			.withDefaultSchema()
//			.withUser("yayaya").password(encoder.encode("123456")).roles("admin")
//			.and()
//			.withUser("dadada").password(encoder.encode("654321")).roles("admin")
//			.and()
//			.withUser("lalala").password(encoder.encode("123456")).roles("user")
//			.and()
//			.withUser("hahaha").password(encoder.encode("654321")).roles("user");
		
			
	}
	
	/**定义安全策略*/
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		
		http.authorizeRequests()
//		.antMatchers("/login").permitAll()		//定义括号内的请求不需要验证  
		.anyRequest().authenticated()				//其余的所有请求都需要验证
		.and()
		.csrf().disable() 									//关闭CSRF攻击防护功能
		.formLogin()										//使用form表单登录 
		.loginPage("/login").permitAll()			//登陆页面不需要验证
		.and()
		.logout()											//登出
		.logoutSuccessUrl("/login");				//跳转到登录页面
	}
	
}
