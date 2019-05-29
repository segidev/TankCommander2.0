package de.htwg.sa.tankcommander.model.gridComponent.gridBaseImpl

import org.scalatest.{FlatSpec, Matchers}

class ObstacleTest extends FlatSpec with Matchers {
  "Different Obstacles" should "initialize as their actual types" in {
    val aBush = new Bush
    val aHill = new Hill
    val aRock = new Stone
    val aForest = new Forest
    val aWater = new Water
    assert(aBush.name.equals("Gebüsche") & aBush.desc.equals("Gebüsche verringern die Präzision von Schüssen")
      & aBush.passable === true & aBush.shortName.equals("B") & aBush.hitMalus === 10)
    assert(aHill.name.equals("Hügel") & aHill.desc.equals("Ermöglichen direkten Beschuss mapweit")
      & aHill.passable === true & aHill.shortName.equals("H") & aHill.hitMalus === 20)
    assert(aRock.name.equals("Stein") & aRock.desc.equals("Steine dienen als Hinderniss und können weder passiert " +
      "noch durschossen werden")
      & aRock.passable === false & aRock.shortName.equals("S") & aRock.hitMalus === 100)
    assert(aForest.name.equals("Wald") & aForest.desc.equals("Wälder liefern Schutz und verringern die Hitchance des " +
      "Gegners")
      & aForest.passable === true & aForest.shortName.equals("F") & aForest.hitMalus === 10)
    assert(aWater.name.equals("Wasser") & aWater.desc.equals("Wasser kann nicht passiert werden")
      & aWater.passable === false & aWater.shortName.equals("W") & aWater.hitMalus === 0)
    assert(!(aBush eq aBush.deepClone()))
    assert(!(aHill eq aHill.deepClone()))
    assert(!(aRock eq aRock.deepClone()))
    assert(!(aForest eq aForest.deepClone()))
    assert(!(aWater eq aWater.deepClone()))


  }

}
