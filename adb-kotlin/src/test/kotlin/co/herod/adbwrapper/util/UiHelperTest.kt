@file:Suppress("FunctionName")

package co.herod.adbwrapper.util

import org.junit.Assert
import org.junit.Test

class UiHelperTest {

    @Test
    fun extractProperty_hello() {
        val property = UiHelper.extractProperty("hello=\"world\"", "hello")
        Assert.assertTrue(property == "world")
        Assert.assertFalse(property == "hello")
    }

    @Test
    fun extractProperty_text() {
        val property = UiHelper.extractProperty("text=\"hello world\"", "text")
        Assert.assertTrue(property == "hello world")
        Assert.assertFalse(property == "text")
    }

    @Test
    fun extractBoundsInts() {
        val boundsInts = UiHelper.extractBoundsInts("bounds=\"[0,2][4,6]\"")
        Assert.assertTrue(boundsInts.size == 4)
        Assert.assertTrue(boundsInts[0] == 0)
        Assert.assertTrue(boundsInts[1] == 2)
        Assert.assertTrue(boundsInts[2] == 4)
        Assert.assertTrue(boundsInts[3] == 6)
    }

    @Test
    fun uiXmlToNodes() {
    }

    @Test
    fun rawDumpToNodes() {
    }
}