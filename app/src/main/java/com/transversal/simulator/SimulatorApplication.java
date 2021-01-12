package com.transversal.simulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import manager.FireGeneratorThread;;

@SpringBootApplication
@EnableJms
@ComponentScan(basePackages = { "config", "services", "jms", "manager"})
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner SimulatorDemo() {
		return (args) -> {
			Thread fireGeneratorThread = new Thread(new FireGeneratorThread());
			while (true) {
				fireGeneratorThread.start();
				fireGeneratorThread.join();
				System.out.println("Suite");
			}
		};
	}

}
