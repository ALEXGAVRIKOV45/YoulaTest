import org.aeonbits.owner.ConfigFactory;

public class ConfigManager {
    public static final TestConfigurations TEST_CONFIG;

    public ConfigManager() {
    }

    static {
        TEST_CONFIG = (TestConfigurations) ConfigFactory.create(TestConfigurations.class);
    }
}
