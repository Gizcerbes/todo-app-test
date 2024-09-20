package com.uogames.salesautomators.test.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toLocalDateTime
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toText
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringArrayResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.task_statuses

object TaskCard {


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    operator fun invoke(
        task: Task,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Card(
            onClick = onClick,
            modifier = modifier,
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = task.status.toText())
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = task.location, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    val ldt =
                        task.endDateAndTimeUTC.toLocalDateTime(TimeZone.currentSystemDefault())
                    Text(
                        text = ldt.toText(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }

    @Composable
    private fun TaskStatus.toText(): String {
        return stringArrayResource(Res.array.task_statuses)[this.ordinal]
    }


}