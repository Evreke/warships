sealed class Player(shipsAmount: Byte)

// TODO Bad design. Shouldn't pass ships amount to player
object EmptyPlayer : Player(0)
class ActualPlayer(val shipsAmount: Byte) : Player(shipsAmount) {
}