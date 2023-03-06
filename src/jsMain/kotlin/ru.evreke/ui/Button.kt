package ru.evreke.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun CustomButton(
  text: String,
  action: () -> Unit
) {
  Button({
    title(text)
    onClick {
      action()
    }
  }) {
    Text(text)
  }
}