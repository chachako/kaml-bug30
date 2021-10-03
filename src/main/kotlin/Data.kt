import kotlinx.serialization.Serializable

/**
 * @author 凛 (https://github.com/RinOrz)
 */
@Serializable
data class Data(
  @Serializable(with = StringOrListSerializer::class)
  val name: List<String>,
  val age: Int
)
