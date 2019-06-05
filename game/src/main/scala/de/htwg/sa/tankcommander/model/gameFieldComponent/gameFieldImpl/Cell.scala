package de.htwg.sa.tankcommander.model.gameFieldComponent.gameFieldImpl

case class Cell(x: Int, y: Int, obstacle: Option[Obstacle]) {

  override def toString: String = obstacle match {
    case Some(o) => o.toString
    case None => "O"
  }

  def consume = {
    print("test")
    this
  }
}
