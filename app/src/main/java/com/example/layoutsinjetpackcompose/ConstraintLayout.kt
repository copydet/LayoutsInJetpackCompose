package com.example.layoutsinjetpackcompose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.example.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme

/**
@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout {
        // create refrences for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /*TODO*/ },
            //assign refrence "button" to the button composable
        // and constrain it to the top of the constrain layout
        modifier = Modifier.constrainAs(button){
            top.linkTo(parent.top, margin = 16.dp)
        }
            ) {
            Text("Button")
        }
        //Assign reference "text: to the text composable
        //and constrain it to the bottom of the button composable
        Text("Text", Modifier.constrainAs(text){
            top.linkTo(button.bottom, margin = 16.dp)
            //center text horizontally in the constrainlayout
            centerHorizontallyTo(parent)
        })
    }
}
*/

@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout {
        // create refrences for the composables to constrain
        val (button1,button2, text) = createRefs()

        Button(
            onClick = { /*TODO*/ },
            //assign refrence "button" to the button composable
            // and constrain it to the top of the constrain layout
            modifier = Modifier.constrainAs(button1){
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button1")
        }

        //Assign reference "text: to the text composable
        //and constrain it to the bottom of the button composable
        Text("Text", Modifier.constrainAs(text){
            top.linkTo(button1.bottom, margin = 16.dp)

            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /*TODO*/ },
            //assign refrence "button" to the button composable
            // and constrain it to the top of the constrain layout
            modifier = Modifier.constrainAs(button2){
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button2")
        }
    }
}

@Preview
@Composable
fun ConstraionLayoutContentPreview(){
    LayoutsInJetpackComposeTheme {
        ConstraintLayoutContent()
    }
}



@Composable
fun LargeConstrainLayout(){
    ConstraintLayout {
        val text = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text("This is a very very very very very very very long text",
        Modifier.constrainAs(text){
            linkTo(start = guideline, end = parent.end)

            // to break in the avaivle space
            width = Dimension.preferredWrapContent
            //you can add size
                .atLeast(100.dp)
        })
    }
}

@Preview
@Composable
fun LargeConstrainLayoutPreview(){
    LayoutsInJetpackComposeTheme {
        LargeConstrainLayout()
    }
}

/** Decoupled API
So far, in the examples, constraints have been specified inline, with a modifier in the composable they're applied to. However, there are cases when keeping the constraints decoupled from the layouts they apply to is valuable: the common example is for easily changing the constraints based on the screen configuration or animating between 2 constraint sets.

For these cases, you can use ConstraintLayout in a different way:

Pass in a ConstraintSet as a parameter to ConstraintLayout.
Assign references created in the ConstraintSet to composables using the layoutId modifier.
This API shape applied to the first ConstraintLayout example shown above, optimized for the width of the screen, looks like this:
 */

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}
