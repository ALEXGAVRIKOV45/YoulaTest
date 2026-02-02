import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(BaseTest.MethodOrder.class)
@Feature("Дополнительные задачи по регулярным выражениям")
public class RegExpAdditionalTest extends BaseTest {

    @BeforeClass(description = "Загрузка файла regexp-additional.properties")
    public void init() {
        loadProperties("regexp-additional.properties");
    }

}
