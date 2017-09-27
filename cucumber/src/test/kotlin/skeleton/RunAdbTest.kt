@file:JvmName("RunAdbTest")

package skeleton

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(plugin = arrayOf(
        "pretty",
        "junit:build/test-results/output.xml"
))
class RunAdbTest
