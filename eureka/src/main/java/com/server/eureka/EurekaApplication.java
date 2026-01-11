package com.server.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//---------->>>  WHY EUREKA SERVER IS USED  <<<-------------------
//1️⃣ Eureka Server ek central registry hota hai jahan saari microservices apna name, IP aur port register karti hain.
//2️⃣ Eureka Client dusri services ka address dynamically Eureka Server se find karta hai, hard-coded IP use nahi hoti.
//3️⃣ Isse microservices easily communicate, scale aur manage hoti hain bina manual configuration ke.

@SpringBootApplication
@EnableEurekaServer   //1️⃣Ye annotation Spring Boot application ko Eureka Server bana deta hai.,,2️⃣️Bina @EnableEurekaServer ke, app service discovery server ki tarah work nahi karegi.
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

}
