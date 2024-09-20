package com.uogames.salesautomators.test.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.add_24px
import salesautomator_test.composeapp.generated.resources.check_24px

object RoundedCheckbox {


	@Composable
	operator fun invoke(
		selected: Boolean = false,
		color: Color = Color.Transparent,
		fillColor: Color = Color.Black,
		iconColor: Color = Color.White,
		checkboxSize: Dp = 50.dp,
		finishedListener: (Boolean) -> Unit = {},
		onClickListener: (Boolean) -> Unit = {},
		modifier: Modifier = Modifier.size(checkboxSize)
	) {
		var isSelected by remember { mutableStateOf(selected) }
		val w = animateDpAsState(
			targetValue = if (isSelected) checkboxSize else 1.dp,
			animationSpec = tween(500),
			finishedListener = { finishedListener(isSelected) }
		)

		LaunchedEffect(selected){
			isSelected = selected
		}

		Box(
			contentAlignment = Alignment.Center,
			modifier = modifier
		) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.clip(CircleShape)
					.background(color)
					.clickable {
						isSelected = !isSelected
						onClickListener(isSelected)
					}
					.border(BorderStroke(w.value, fillColor), CircleShape)
					//.padding(w.value)
					//.clip(RoundedCornerShape(1000.dp))
					//.background(fillColor)

			) {

			}
			AnimatedVisibility(
				isSelected,
				exit = scaleOut(),
				enter = scaleIn()
			) {
				Icon(
					painter = painterResource(Res.drawable.check_24px),
					contentDescription = "",
					modifier = Modifier.size(checkboxSize).padding(3.dp),
					tint = iconColor
				)
			}
		}
	}


}


//Row(
//verticalAlignment = Alignment.CenterVertically,
//modifier = Modifier.fillMaxWidth()
//.clip(CircleShape)
//.clickable { selectedIndex = if (selectedIndex == i) -1 else i },
//) {
//	RoundedCheckbox(
//		selected = i == selectedIndex,
//		onClickListener = {
//			selectedIndex = if (it) i else -1
//		},
//		checkboxSize = 45.dp
//	)
//	Spacer(modifier = Modifier.padding(14.dp))
//	Text(
//		text = it.text,
//		color = MaterialTheme.colors.primary
//	)
//}