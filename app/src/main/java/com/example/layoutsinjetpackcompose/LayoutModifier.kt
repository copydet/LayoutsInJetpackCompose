package com.example.layoutsinjetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme

@Composable
fun LayoutsCodelabTwo() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContentFour(Modifier.padding(innerPadding))
    }
}

/** Step 1 */

@Stable
fun Modifier.padding(all: Dp) =
    this.then(
        paddingModifier(start = all, top = all, end = all, bottom = all, rtlAware = true)
    )

//Implementation detail
private class paddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean,
): LayoutModifier{

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height){
            if (rtlAware){
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }
}

@Composable
fun BodyContentFour(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .size(200.dp)
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            for (topic in topics){
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}

@Preview
@Composable
fun ChipPreviewTwo() {
    LayoutsInJetpackComposeTheme {
        Chip(text = "Hi there")
    }
}

@Preview
@Composable
fun LayoutsCodelabPreviewTwo() {
    LayoutsInJetpackComposeTheme {
        LayoutsCodelabTwo()
    }
}