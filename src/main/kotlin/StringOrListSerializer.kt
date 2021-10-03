import com.charleskorn.kaml.IncorrectTypeException
import com.charleskorn.kaml.YamlInput
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

/**
 * @author 凛 (https://github.com/RinOrz)
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
object StringOrListSerializer : KSerializer<List<String>> {
  private val listSerializer: KSerializer<List<String>> = ListSerializer(String.serializer())

  override val descriptor: SerialDescriptor = buildSerialDescriptor(
    StringOrListSerializer::class.java.name,
    SerialKind.CONTEXTUAL
  ) {
    element("list", listSerialDescriptor<String>())
    element<String>("string")
  }

  override fun serialize(encoder: Encoder, value: List<String>) = listSerializer.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): List<String> = decoder.fromJson() ?: decoder.fromYaml()

  private fun Decoder.fromJson(): List<String>? {
    val decoder = this as? JsonDecoder ?: return null
    return try {
      listOf(decoder.decodeString())
    } catch (e: Throwable) {
      decoder.decodeSerializableValue(listSerializer)
    }
  }

  private fun Decoder.fromYaml(): List<String> {
    val decoder = (this as YamlInput).beginStructure(descriptor) as Decoder
    return try {
      listOf(decoder.decodeString())
    } catch (e: IncorrectTypeException) {
      decoder.decodeSerializableValue(listSerializer)
    }
  }
}