package net.cityxen.cxnbbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import net.cityxen.cxnbbs.dao.SecurityRepository;

@SpringBootApplication
public class Application 
{
	@Autowired 
	SecurityRepository securityRepository;
	
	
	public static void main( String[] args )
    {
		SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
    }
    
    @Bean
    public CommandLineRunner startBBS(TaskExecutor executor) {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {
                executor.execute(new BBSRunner());
            }
        };
    }
}
