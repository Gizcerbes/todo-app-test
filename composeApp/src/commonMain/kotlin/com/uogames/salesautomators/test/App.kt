package com.uogames.salesautomators.test

import androidx.compose.runtime.Composable
import com.uogames.salesautomators.test.ui.screen.navigation.ScreenNavigation
import com.uogames.salesautomators.test.ui.theme.AppTheme
import com.uogames.salesautomators.test.util.SizeObserver
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SizeObserver()
    AppTheme {

        ScreenNavigation()

    }
}
