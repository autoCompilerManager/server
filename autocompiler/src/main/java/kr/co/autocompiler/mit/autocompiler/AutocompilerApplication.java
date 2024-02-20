package kr.co.autocompiler.mit.autocompiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;





@SpringBootApplication
@ComponentScan(basePackages = "kr.co.autocompiler.mit.autocompiler")
public class AutocompilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutocompilerApplication.class, args);

		ServerSocketThread socketThread = new ServerSocketThread();
		socketThread.start();
	}

}
