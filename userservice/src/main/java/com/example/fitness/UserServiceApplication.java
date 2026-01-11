package com.example.fitness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//eureka ---> cloud server  h
// user,Activity etc --->> service h
// there are some configuration required in yml file checkout that
// 1-->ham phle eureka discovery client dependency inject krenge pom.xml me
// 2-->fir eureka ke pom.xml se dependency management lenge aur dependencies aur build ke beech em insert krenge user ke pom.xml me
// 3-->then cloud server version lenge usko java version ke neeche insert krenge sath me

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
