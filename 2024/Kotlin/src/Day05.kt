import java.util.*
import kotlin.collections.ArrayList

fun main() {
    // First page must be printed before second
    class PageOrderingRule(val firstPage: Int, val secondPage: Int)

    fun isPageOrderingValid(beforePage: Int, afterPage: Int, pageOrderingRule: List<PageOrderingRule>): Boolean {
        return pageOrderingRule.any {
            it.firstPage == beforePage && it.secondPage == afterPage
        }
    }


    fun middleElement(pages: List<Int>): Int {
        return pages.size / 2
    }

    fun fixedUpdate(update:List<Int>, pageOrderingRules: List<PageOrderingRule>): List<Int> {
        update.forEachIndexed { index, thisPage ->
            if (index > 0) {
                val beforeRule = update.subList(0, index)
                beforeRule.forEachIndexed { iindex, pageInt ->
                    if (!isPageOrderingValid(pageInt, thisPage, pageOrderingRules)) {
                        Collections.swap(update, index, index - iindex)
                        return update;
                    }
                }

            }
            if (index != update.size) {
                val afterRule = update.subList(index + 1, update.size)
                afterRule.forEachIndexed { iindex, pageInt ->
                    if (!isPageOrderingValid(thisPage, pageInt, pageOrderingRules)) {
                        Collections.swap(update, index, index + 1 + iindex)
                        return update;
                    }
                }
            }
        }
        return update
    }

    fun isUpdateCorrectOrder(update: List<Int>, pageOrderingRules: List<PageOrderingRule>): Boolean {
        update.forEachIndexed { index, thisPage ->
            if (index > 0) {
                val beforeRule = update.subList(0, index)
                beforeRule.forEachIndexed { iindex, pageInt ->
                    if (!isPageOrderingValid(pageInt, thisPage, pageOrderingRules)) {
                        return false
                    }
                }

            }
            if (index != update.size) {
                val afterRule = update.subList(index + 1, update.size)
                afterRule.forEachIndexed { iindex, pageInt ->
                    if (!isPageOrderingValid(thisPage, pageInt, pageOrderingRules)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    val testInput = readInput("Day05_part1")
    val pageOrderingRules = ArrayList<PageOrderingRule>()
    val validMiddlePages = ArrayList<Int>()
    val invalidMiddlePages = ArrayList<Int>()

    for (line in testInput) {
        if (line.isBlank()) continue
        if (line.contains("|")) {
            val split = line.split("|")
            pageOrderingRules.add(PageOrderingRule(
                split[0].toInt(),
                split[1].toInt()
            ))
        }

        if (line.contains(",")) {
            val pageUpdate = line.split(",").map { it.toInt() }
            var isUpdateCorrectOrder = isUpdateCorrectOrder(pageUpdate, pageOrderingRules)
            if (isUpdateCorrectOrder) {
                // Part 1
                validMiddlePages.add(pageUpdate[middleElement(pageUpdate)])
            } else {
                // Part 2
                while (!isUpdateCorrectOrder) {
                    isUpdateCorrectOrder = isUpdateCorrectOrder(fixedUpdate(pageUpdate, pageOrderingRules), pageOrderingRules)
                }
                invalidMiddlePages.add(pageUpdate[middleElement(pageUpdate)])
            }
        }
    }

    println(validMiddlePages.sum())
    println(invalidMiddlePages.sum())
}
