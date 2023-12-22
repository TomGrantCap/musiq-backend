package Backend;

import Backend.Repositories.DJRepository;
import Backend.Repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendNewApplication implements CommandLineRunner {

    @Autowired
    private DJRepository djRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    public static void main(String... args) {SpringApplication.run(BackendNewApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}