
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.main.Job;
import com.parsers.rekrute.RekruteParsers;




public class ParsersTest {

  @Test
    void testParseRemoteWork() {
        // Arrange
        Job job1 = new Job();
        job1.setRemoteWork("Oui, c'est possible");

        Job job2 = new Job();
        job2.setRemoteWork("Hybride");

        Job job3 = new Job();
        job3.setRemoteWork("Non disponible");

        Job job4 = new Job(); // No remoteWork value (null)

        List<Job> jobs = Arrays.asList(job1, job2, job3, job4);

        // Act
        RekruteParsers.parseRemoteWork(jobs);

        // Assert
        assertEquals("oui", job1.getRemoteWork());
        assertEquals("hybride", job2.getRemoteWork());
        assertEquals("non", job3.getRemoteWork());
        assertEquals("non", job4.getRemoteWork());
    }

    @Test
    void testParseActivitySector() {
        // Arrange
        List<Job> jobs = new ArrayList<>();
        
        // Test cases with valid sectors
        // Job job = new Job();
        // job.setActivitySector("Banque, assurance, finances");
        // jobs.add(job);
        // Job job1 = new Job();
        // job1.setActivitySector("Informatique, SSII, Internet");
        // jobs.add(job1);
        
        // // Test cases with lowercase, uppercase, or partial match
        // Job job3 = new Job();
        // job3.setActivitySector("banque");
        // jobs.add(job3);
        // Job job4 = new Job();
        // job4.setActivitySector("informatique");
        // jobs.add(job4);
        
        // Test cases requiring mapping
        Job job5 = new Job();
        job5.setActivitySector("Call center");
        jobs.add(job5);

        // Job job6 = new Job();
        // job6.setActivitySector("BTP");

        // jobs.add(job6);

        // Job job7 = new Job();
        // job7.setActivitySector("Tourisme et restauration");

        // jobs.add(job7);

        // Job job8 = new Job();
        // job8.setActivitySector("Hôtellerie");

        // jobs.add(job8);

        // Job job9 = new Job();
        // job9.setActivitySector("Unknown sector");

        // jobs.add(job9);

        // Job job10 = new Job();
        // job10.setActivitySector("   ");

        // jobs.add(job10);
        
   

        // Expected results
        String[] expected = {
            "Banque, assurance, finances",
            "Informatique, SSII, Internet",
            "Banque, assurance, finances",
            "Informatique, SSII, Internet",
            "Centres d´appel, hotline, call center",
            "BTP, construction",
            "Tourisme, loisirs",
            "Tourisme, loisirs",
            null,
            null
        };

        // Act
        RekruteParsers.parseActivitySector(jobs);
        System.out.println(jobs.get(0).getActivitySector());
        assertEquals("Centres d´appel, hotline, call center", jobs.get(0).getActivitySector());

        // Assert
        // for (int i = 0; i < jobs.size(); i++) {
        //     assertEquals(expected[i], jobs.get(i).getActivitySector(), 
        //         "Failed at index " + i);
        // }
    }
    
    
}
