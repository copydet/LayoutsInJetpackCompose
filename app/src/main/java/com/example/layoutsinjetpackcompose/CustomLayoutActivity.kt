package com.example.layoutsinjetpackcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme
import okhttp3.FormBody

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        //check the composable has a first baseline

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        //height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height){
            //where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

/**

@Composable
fun CustomLayout(
    modifier: Modifier = Modifier,
    //custom layout attributes
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content,
    ){ measurables, constraints ->
        // measure and position children given constrainsts logic here

    }
}

*/

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content,
    ){ measurables, constraints ->
        //measure and position children given constrains logic here
        // Don't constrain child views further, measure them with given constraints
        // List of measured children

        val placeables = measurables.map { measurable ->
            //measure each child
            measurable.measure(constraints)
        }
        //track the y co-ord we have place children up to
        var yPosition = 0

        //set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight){
            //place children in the parent layout
            placeables.forEach{ placeable ->
                //position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                //record the y co-ord place up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun BodyContentTwo(modifier: Modifier = Modifier){
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("place items")
        Text("vertically.")
        Text("we've done it by hand!")
    }
}

@Preview
@Composable
fun BodyContentPreview(){
    LayoutsInJetpackComposeTheme {
        BodyContentTwo()
    }
}

@Preview
@Composable
fun TextWithPaddingTobaselinePreview(){
    LayoutsInJetpackComposeTheme {
        Text("hi there !", Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview(){
    LayoutsInJetpackComposeTheme {
        Text("Hi there ! ", Modifier.padding(top = 32.dp))
    }
}

