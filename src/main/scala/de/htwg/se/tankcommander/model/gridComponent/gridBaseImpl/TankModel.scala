package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

case class TankModel() {
  val tankBaseDamage: Int = 10
  val accuracy: Int = 100
  val maxHp = 100
  val posC: (Int, Int) = (0, 0)
  val facing: String = "up"
  val hp: Int = maxHp
  val currentHitChance: Int = 0

  /*    def this(x: Int, y: (Int, Int), z: String) {
        this()
        hp = x
        posC = y
        facing = z
  }

  def deepClone (): TankModel = {
    val tankClone = new TankModel
    tankClone.hp = this.hp
    tankClone.posC = this.posC
    tankClone.facing = this.facing
    tankClone
  }*/
}


