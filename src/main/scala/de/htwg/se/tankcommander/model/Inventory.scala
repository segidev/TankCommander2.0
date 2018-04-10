package de.htwg.se.tankcommander.model

//TODO Define a class containing all needed values and methods for a Inventory-object
class Inventory {
  private var Items = new Array[PowerUPs](3)

  def addItemToInventory(itemToAdd: PowerUPs): Unit = {
    if (checkIfNotFull()) {
      Items(Items.length + 1) = itemToAdd;
    } else {
      print("Inventory full, can not pick up Item")
    }
  }

  //checks if Inventory is full
  def checkIfNotFull(): Boolean = {
    if (Items.length < 3) {
      return true
    }
    false
  }
}


