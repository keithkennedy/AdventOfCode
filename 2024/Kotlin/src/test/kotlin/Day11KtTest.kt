import kotlin.test.Test
import kotlin.test.assertEquals

class Day11KtTest {
    private val solution: Day11KtSolution = Day11KtSolution()
    @Test
    fun blinkRuleOneTest() {
        assertEquals(listOf(1L), solution.blink(0))
    }

    @Test
    fun blinkRuleTwoTest1() {
        assertEquals(listOf(1L, 0L), solution.blink(10))
    }
    @Test
    fun blinkRuleTwoTest2() {
        assertEquals(listOf(33L, 45L), solution.blink(3345))
    }
    @Test
    fun blinkRuleTwoTest3() {
        assertEquals(listOf(10L, 0L), solution.blink(1000))
    }

    @Test
    fun blinkRuleThreeTest() {
        assertEquals(listOf(10120L), solution.blink(5))
    }
}