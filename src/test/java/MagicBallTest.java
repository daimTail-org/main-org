import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MagicBallTest {
    @Test
    public void testLucky() {
        // shake-shake
        Assertions.assertEquals(7, MagicBall.getLucky());
    }
}
