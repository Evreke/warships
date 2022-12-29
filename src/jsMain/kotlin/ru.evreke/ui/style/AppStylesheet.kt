/*
 * Copyright (c) 2019-2022. Alexey Antipin and others.
 * https://github.com/Evreke/warships
 *
 * Licensed under the Apache License 2.0
 */

package ru.evreke.ui.style

import org.jetbrains.compose.web.css.*

object AppStylesheet : StyleSheet() {
  val fieldContainer by style {
    display(DisplayStyle.Grid)
    gridTemplateColumns("repeat(10, 25px)")
  }

  val fieldCell by style {
    backgroundColor(Color("aliceblue"))
    width(25.px)
    height(25.px)
    border {
      width = 1.px
      style = LineStyle("solid")
      color = Color("#ECEFFF")
    }
  }

  val shipCell by style {
    backgroundColor(Color("green"))
    width(25.px)
    height(25.px)
    border {
      width = 1.px
      style = LineStyle("solid")
      color = Color("#ECEFFF")
    }
  }

  val prohibitedCell by style {
    backgroundColor(Color("red"))
    width(25.px)
    height(25.px)
    border {
      width = 1.px
      style = LineStyle("solid")
      color = Color("#ECEFFF")
    }
  }
}
