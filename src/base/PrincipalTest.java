package base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PrincipalTest {

    @ParameterizedTest
    @MethodSource("provide")
    void testIntentarAbrirCompuertas(boolean tienePermiso, boolean compuertasVerificadas, boolean expected) {
        boolean result = Principal.intentarAbrirCompuertas(tienePermiso, compuertasVerificadas);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provide() {
        return Stream.of(
            Arguments.of(true, true, true),
            Arguments.of(true, false, false),
            Arguments.of(false, false, false),
            Arguments.of(false, false, false)
        );
    }
}
