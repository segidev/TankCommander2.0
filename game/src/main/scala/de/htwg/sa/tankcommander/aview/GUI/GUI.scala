package de.htwg.sa.tankcommander.aview.GUI

import com.google.inject.Inject
import de.htwg.sa.tankcommander.controller.controllerComponent.controllerImpl.Controller
import java.awt.event.{WindowAdapter, WindowEvent}
import javax.swing.{JFrame, WindowConstants}

import scala.swing.Dimension
import scalafx.application.Platform

class GUI @Inject()(controller: Controller) extends JFrame {
  val defaultSize: (Int, Int) = (1024, 768)

  setResizable(false)
  setMinimumSize(new Dimension(defaultSize._1, defaultSize._2))
  setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

  // Layout
  val mainPanel: GamePanel = new GamePanel(controller)
  add(mainPanel)

  setLocationRelativeTo(null)
  setVisible(true)

  // Methods
  def close(): Unit = {
    setVisible(false)
    dispose()
  }

  addWindowListener(new WindowAdapter {
    override def windowClosing(e: WindowEvent): Unit = {
      Platform.runLater(mainPanel.exitApplication())
    }
  })
}
