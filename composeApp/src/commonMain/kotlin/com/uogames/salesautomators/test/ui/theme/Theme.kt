package com.uogames.salesautomators.test.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		typography = Typography,
		colors = ColorList.COLORS,
		content = content,
	)


}