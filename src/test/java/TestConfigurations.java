import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Reloadable;

@Sources({"file:src/test/resources/test.properties"})
public interface TestConfigurations extends Config, Reloadable {
    @Key("baseUrl")
    @DefaultValue("")
    String baseUrl();

    @Key("waitSelenidetimeout")
    @DefaultValue("4000")
    int waitSelenidetimeout();
}
