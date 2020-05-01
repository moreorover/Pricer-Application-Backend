package martin.dev.pricer.migrate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test"})
class MigrationTest {

    @Autowired
    private Migration migration;

    @Test
    public void migrate() {
        migration.migrate();
    }

}