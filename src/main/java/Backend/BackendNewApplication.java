package Backend;

import Backend.Entities.DJ;
import Backend.Entities.Performance;
import Backend.Repositories.DJRepository;
import Backend.Repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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
        DJ dj2 = new DJ("Korn", "Metal");
        DJ dj3 = new DJ("Jay-Z", "Rap");

        djRepository.save(dj1);
        djRepository.save(dj2);
        djRepository.save(dj3);

        List<DJ> djList1 = new ArrayList<>();
        List<DJ> djList2 = new ArrayList<>();
        List<DJ> djList3 = new ArrayList<>();

        djList2.add(dj2);
        djList1.add(dj1);
        djList3.add(dj3);

        performanceRepository.save(new Performance(djList1,"Glastonbury", "Pop"));
        performanceRepository.save(new Performance(djList2,"Fuizenfest", "Metal"));
        performanceRepository.save(new Performance(djList3,"Underground", "Rap"));

    }
}