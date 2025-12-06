import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MIFITest {
    @Test
    void testStringEquality() {
        String expected = "MIFI_GITHUB_ACTIONS_HOME_WORK";
        String actual = "MIFI_GITHUB_ACTIONS_HOME_WORK";
        assertEquals(expected, actual, "Строки должны совпадать");
    }
}