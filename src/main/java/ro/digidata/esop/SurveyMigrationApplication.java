package ro.digidata.esop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.digidata.esop.input.ShellRunner;

@SpringBootApplication
public class SurveyMigrationApplication implements CommandLineRunner {
    
    @Autowired
    private ShellRunner runner;
    
    public static void main(String[] args) {
        SpringApplication.run(SurveyMigrationApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        
       runner.handleAllInputs( );
    }
}
