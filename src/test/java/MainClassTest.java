import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainClassTest {
    @Test
    public void testHelloWorld() {
        Assertions.assertEquals("halo, World!", MainClass.getHelloWorld());
    }
}
