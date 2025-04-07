package com.roberto.meadow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.roberto.meadow.ui.theme.*

@Composable
fun HeaderButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    enabled: Boolean = true,
    size: Dp = 32.dp,
    backgroundColor: Color = Neutral200,
    disabledBackgroundColor: Color = Neutral200.copy(alpha = 0.4f)
) {
    val bgColor = if (enabled) backgroundColor else disabledBackgroundColor

    Box(
        modifier = Modifier
            .size(size)
            .background(color = bgColor, shape = AppShapes.extraLarge),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier.size(size * 0.6f) // control icon size
        ) {
            icon()
        }
    }
}

@Composable
fun HeaderBar(
    modifier: Modifier = Modifier,
    startContent: @Composable () -> Unit = {},
    titleContent: @Composable () -> Unit = {},
    endContent: @Composable () -> Unit = {},
    systemBarsPadding: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = White)
            .drawBehind {
                drawLine(
                    color = Neutral200,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 3.dp.toPx()
                )
            }
            .padding(horizontal = 12.dp)
            .then(
                if (systemBarsPadding)
                    Modifier.padding(WindowInsets.systemBars.asPaddingValues())
                else
                    Modifier.padding(top = 8.dp, bottom = 8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            startContent()
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            titleContent()
        }
        Box(contentAlignment = Alignment.CenterEnd) {
            endContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderBarPreview() {
    HeaderBar(
        startContent = {
            HeaderButton(
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.Black,
                        modifier = Modifier.size(19.dp)
                    )
                }
            )
        },
        titleContent = {
            Text("Example", style = MaterialTheme.typography.titleLarge)
        },
        endContent = {
            HeaderButton(
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier.size(19.dp)
                    )
                },
                enabled = false // test disabled mode
            )
        }
    )
}
