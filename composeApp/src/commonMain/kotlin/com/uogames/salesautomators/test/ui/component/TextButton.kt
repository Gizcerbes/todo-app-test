package com.uogames.salesautomators.test.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

object TextButton {


    @Composable
    operator fun invoke(
        icon: Painter? = null,
        text: String,
        onClick: () -> Unit,
        enabled: Boolean = true,
        shape: Shape = RoundedCornerShape(3.dp),
        backgroundColor: Color = Color.Transparent,
        tintColor: Color = MaterialTheme.colors.onPrimary,
        padding: PaddingValues = PaddingValues(8.dp),
        modifier: Modifier = Modifier
    ){
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
                .clip(shape)
                .background(backgroundColor)
                .clickable(
                    enabled = enabled,
                    onClick = onClick
                )
                .padding(padding)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = tintColor
                )
                Text(
                    text,
                    style = MaterialTheme.typography.button,
                    color = tintColor,
                )
            }
        }
    }


}