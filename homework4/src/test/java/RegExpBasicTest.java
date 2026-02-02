import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listeners(BaseTest.MethodOrder.class)
@Feature("Базовые задачи по регулярным выражениям")
public class RegExpBasicTest extends BaseTest {

    @BeforeClass(description = "Загрузка файла regexp-additional.properties")
    public void init() {
        loadProperties("regexp-basic.properties");
    }

    @DataProvider
    public Object[][] dp1() {
        return new Object[][]{
                {"abcdefg"},
                {"abcde"},
                {"abc"},
        };
    }

    @Story("Task 01")
    @Test(dataProvider = "dp1")
    public void test01(String value) {
        String regex = System.getProperty("task1");
        assertNotEmpty(regex);
        Assert.assertTrue(String.format("В строке '%s' должно быть найдено регулярное выражение '%s'", value, regex),
                Pattern.compile(regex).matcher(value).find());
    }

    private void assertNotEmpty(String regex) {
        Assert.assertNotNull("В файле с ответами отсутствует регулярное выражение для этой задачи", regex);
        Assert.assertFalse("Регулярное выражение не должно быть пустым", regex.isEmpty());
    }

    @DataProvider
    public Object[][] dp2() {
        return new Object[][]{
                {"abc123xyz"},
                {"define \"123\""},
                {"var g = 123;"},
        };
    }

    @Story("Task 02")
    @Test(dataProvider = "dp2")
    public void test02(String value) {
        String regex = System.getProperty("task2");
        assertNotEmpty(regex);
        Assert.assertTrue(String.format("В строке '%s' должно быть найдено регулярное выражение '%s'", value, regex),
                Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp3() {
        return new Object[][]{
                {"cats.", true},
                {"8967.", true},
                {"?=+!.", true},
                {"abcd1", false},
        };
    }

    @Story("Task 03")
    @Test(dataProvider = "dp3")
    public void test03(String value, boolean isMatch) {
        String regex = System.getProperty("task3");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp4() {
        return new Object[][]{
                {"can", true},
                {"man", true},
                {"fan", true},
                {"dan", false},
                {"ran", false},
                {"pan", false},
        };
    }

    @Story("Task 04")
    @Test(dataProvider = "dp4")
    public void test04(String value, boolean isMatch) {
        String regex = System.getProperty("task4");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp5() {
        return new Object[][]{
                {"hog", true},
                {"dog", true},
                {"bog", false},
        };
    }

    @Story("Task 05")
    @Test(dataProvider = "dp5")
    public void test05(String value, boolean isMatch) {
        String regex = System.getProperty("task5");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp6() {
        return new Object[][]{
                {"Ana", true},
                {"Bob", true},
                {"Cpc", true},
                {"aax", false},
                {"bby", false},
                {"ccz", false},
        };
    }

    @Story("Task 06")
    @Test(dataProvider = "dp6")
    public void test06(String value, boolean isMatch) {
        String regex = System.getProperty("task6");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp7() {
        return new Object[][]{
                {"wazzzzzup", true},
                {"wazzzup", true},
                {"wazup", false},
        };
    }

    @Story("Task 07")
    @Test(dataProvider = "dp7")
    public void test07(String value, boolean isMatch) {
        String regex = System.getProperty("task7");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp8() {
        return new Object[][]{
                {"aaaabcc", true},
                {"aabbbbc", true},
                {"aacc", true},
                {"a", false},
        };
    }

    @Story("Task 08")
    @Test(dataProvider = "dp8")
    public void test08(String value, boolean isMatch) {
        String regex = System.getProperty("task8");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp9() {
        return new Object[][]{
                {"1 file found?", true},
                {"2 files found?", true},
                {"24 files found?", true},
                {"No files found.", false},
        };
    }

    @Story("Task 09")
    @Test(dataProvider = "dp9")
    public void test09(String value, boolean isMatch) {
        String regex = System.getProperty("task9");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp10() {
        return new Object[][]{
                {"1.   abc", true},
                {"2.\tabc", true},
                {"3.           abc", true},
                {"4.abc", false},
        };
    }

    @Story("Task 10")
    @Test(dataProvider = "dp10")
    public void test10(String value, boolean isMatch) {
        String regex = System.getProperty("task10");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp11() {
        return new Object[][]{
                {"Mission: successful", true},
                {"Last Mission: unsuccessful", false},
                {"Next Mission: successful upon capture of target", false},
        };
    }

    @Story("Task 11")
    @Test(dataProvider = "dp11")
    public void test11(String value, boolean isMatch) {
        String regex = System.getProperty("task11");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp12() {
        return new Object[][]{
                {"file_record_transcript.pdf", "file_record_transcript", true},
                {"file_07241999.pdf", "file_07241999", true},
                {"testfile_fake.pdf.tmp", null, false},
        };
    }

    @Story("Task 12")
    @Test(dataProvider = "dp12")
    public void test12(String value, String group, boolean isMatch) {
        String regex = System.getProperty("task12");
        assertNotEmpty(regex);
        Matcher m = Pattern.compile(regex).matcher(value);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, m.find());
        if (isMatch) {
            try {
                Assert.assertEquals("Захваченная группа символов не соответствует ожидаемой.", group, m.group(1));
            } catch (IndexOutOfBoundsException e) {
                throw new AssertionError("Не найдена группа символов");
            }
        }
    }

    @DataProvider
    public Object[][] dp13() {
        return new Object[][]{
                {"Jan 1987", "1987", true},
                {"May 1969", "1969", true},
                {"Aug 2011", "2011", true},
        };
    }

    @Story("Task 13")
    @Test(dataProvider = "dp13")
    public void test13(String value, String group, boolean isMatch) {
        String regex = System.getProperty("task13");
        assertNotEmpty(regex);
        Matcher m = Pattern.compile(regex).matcher(value);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, m.find());
        if (isMatch) {
            try {
                Assert.assertEquals("Захваченная группа символов не соответствует ожидаемой.", value, m.group(1));
            } catch (IndexOutOfBoundsException e) {
                throw new AssertionError("Не найдена группа символов: " + value);
            }
            try {
                Assert.assertEquals("Захваченная группа символов не соответствует ожидаемой.", group, m.group(2));
            } catch (IndexOutOfBoundsException e) {
                throw new AssertionError("Не найдена группа символов " + group);
            }
        }
    }

    @DataProvider
    public Object[][] dp14() {
        return new Object[][]{
                {"1280x720", "1280", "720"},
                {"1920x1600", "1920", "1600"},
                {"1024x768", "1024", "768"},
        };
    }

    @Story("Task 14")
    @Test(dataProvider = "dp14")
    public void test14(String value, String group1, String group2) {
        String regex = System.getProperty("task14");
        assertNotEmpty(regex);
        Matcher m = Pattern.compile(regex).matcher(value);
        Assert.assertTrue(String.format("В строке '%s' должно быть найдено регулярное выражение '%s'", value, regex), m.find());
        try {
            Assert.assertEquals("Захваченная группа символов не соответствует ожидаемой.", group1, m.group(1));
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Не найдена группа символов: " + group1);
        }
        try {
            Assert.assertEquals("Захваченная группа символов не соответствует ожидаемой.", group2, m.group(2));
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Не найдена группа символов " + group2);
        }
    }

    @DataProvider
    public Object[][] dp15() {
        return new Object[][]{
                {"I love cats", true},
                {"I love dogs", true},
                {"I love logs", false},
                {"I love cogs", false},
        };
    }

    @Story("Task 15")
    @Test(dataProvider = "dp15")
    public void test15(String value, boolean isMatch) {
        String regex = System.getProperty("task15");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }

    @DataProvider
    public Object[][] dp16() {
        return new Object[][]{
                {"The quick brown fox jumps over the lazy dog.", true},
                {"There were 614 instances of students getting 90.0% or above.", true},
                {"The FCC had to censor the network for saying &$#*@!.", true},
        };
    }

    @Story("Task 16")
    @Test(dataProvider = "dp16")
    public void test16(String value, boolean isMatch) {
        String regex = System.getProperty("task16");
        assertNotEmpty(regex);
        Assert.assertEquals(String.format("В строке '%s' %sдолжно быть найдено регулярное выражение '%s'", value, (isMatch ? "" : "не "), regex),
                isMatch, Pattern.compile(regex).matcher(value).find());
    }
}
