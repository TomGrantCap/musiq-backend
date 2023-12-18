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
    public void run(String... args) {

        DJ dj1 = new DJ("Macklemore", "Pop");
        DJ dj2 = new DJ("Jay-Z", "Rap");
        DJ dj3 = new DJ("Korn", "Metal");

        djRepository.save(dj1);
        djRepository.save(dj2);
        djRepository.save(dj3);

        performanceRepository.save(new Performance(dj3,"Glastonbury", "Pop"));
        performanceRepository.save(new Performance(dj1,"Fuizenfest", "Metal"));
        performanceRepository.save(new Performance(dj2,"Underground", "Rap"));

        for (DJ dj : djRepository.findAll()){
            System.out.println(dj.getId() + " "
                    + dj.getName() + " "
                    + dj.getGenre());
        }

        for (Performance performance : performanceRepository.findAll()){
            System.out.println(performance.getId() + " "
                    + performance.getName() + " "
                    + performance.getGenre());
        }

    }
}