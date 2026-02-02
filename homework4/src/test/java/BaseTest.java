import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseTest {
    public static class MethodOrder implements IMethodInterceptor {
        @Override
        public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
            List<IMethodInstance> ordered = new ArrayList<>(methods);
            ordered.sort(Comparator.comparing(o -> o.getMethod().getMethodName()));
            return ordered;
        }
    }

    protected void loadProperties(String fileName) {
        try {
            String text = new BufferedReader(
                    new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName), StandardCharsets.UTF_8))
                    .lines()
                    .map(line -> line
                            .replace("\\", "\\\\")
                            .replace("\\x", "\\\\\\x"))
                    .collect(Collectors.joining("\n"));
            System.getProperties().load(new ByteArrayInputStream(text.getBytes()));
        } catch (Exception e) {
            throw new AssertionError("Не удалось загрузить файл src/main/resources/" + fileName + ": " + e.getMessage());
        }
    }
}
