import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * @author 凛 (https://github.com/RinOrz)
 */
class Tests {

  @Test
  fun deserializeYAML() {
    val source = """
      name: ""
      age: 1
    """.trimIndent()
    println(Yaml.default.decodeFromString<Data>(source))
  }

  @Test
  fun deserializeJSON() {
    val source = """
      {
        "name": "",
        "age": 1
      }
    """.trimIndent()
    println(Json.decodeFromString<Data>(source))
  }
}