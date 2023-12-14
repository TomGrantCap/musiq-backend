package Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendNewApplication implements CommandLineRunner {

    @Autowired
    private DJRepository djRepository;

    public static void main(String... args) {SpringApplication.run(BackendNewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DJ dj1 = new DJ("jey-dizzle", "hiphop");
        DJ jayZ = new DJ("Jay-Z", "Rap");
        DJ korn = new DJ("Korn", "Metal");
        djRepository.save(dj1);
        djRepository.save(jayZ);
        djRepository.save(korn);

        DJ foundDJ = djRepository.findById(1L).get();

        System.out.println(dj1.toString());
        System.out.println(foundDJ);

    }
}