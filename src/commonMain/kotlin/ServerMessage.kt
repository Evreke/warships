import kotlinx.serialization.Serializable

@Serializable
data class ServerMessage(
  val payload: Payload
)

@Serializable
data class ClientMessage(
  val payload: Payload
) {

}

enum class Type {
  CLIENT,
  SERVER
}

enum class Action {
  INIT,
  JOIN,
}

@Serializable
sealed class Payload

@Serializable
data class ControlPayload(
  val action: Action
) : Payload()

@Serializable
data class DataPayload(
  val board: Board
) : Payload()