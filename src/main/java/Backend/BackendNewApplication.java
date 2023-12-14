package Backend;

import Backend.Entities.DJ;
import Backend.Entities.Performance;
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
    public void run(String... args) throws Exception {

        djRepository.save(new DJ("Macklemore", "Pop"));
        djRepository.save(new DJ("Jay-Z", "Rap"));
        djRepository.save(new DJ("Korn", "Metal"));

        performanceRepository.save(new Performance("Glastonbury", "Pop"));
        performanceRepository.save(new Performance("Fuizenfest", "Metal"));
        performanceRepository.save(new Performance("Underground", "Rap"));


        for (DJ dj : djRepository.findAll()){
            System.out.println("DJ ID: " + dj.getId()
            + ", DJ name: " + dj.getName()
            + ", DJ genre: " + dj.getGenre());
        }

        for (Performance performance : performanceRepository.findAll()){
            System.out.println("Performance ID: " + performance.getId()
                    + ", Performance name: " + performance.getName()
                    + ", Performance genre: " + performance.getGenre());
        }

    }
}