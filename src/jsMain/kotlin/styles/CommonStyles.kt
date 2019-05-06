package styles

import kotlinx.css.*
import styled.StyleSheet

object CommonStyles : StyleSheet("CommonStyles", isStatic = true) {
    val common by css {
        display = Display.flex
        padding = 10.px.toString()
        width = 25.pct
        marginLeft = 37.5.pct
        color = Color("#fff")
        fontSize = 1.3.em
        flexDirection = FlexDirection.row
        alignItems = Align.center
    }

    val box by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
        border = "2px solid #aaa719"
        backgroundColor = Color("#d4d37f")
        marginBottom = 20.px
        padding = 10.px.toString()
        width = 80.pct
        lastChild {
            marginBottom = 0.px
        }
    }

    val btn by css {
        marginRight = 10.px
        padding = 10.px.toString()
        marginTop = 10.px
        cursor = Cursor.pointer
        fontSize = 0.65.em
        color = Color("#fff")
        outline = Outline.none
    }

    val redBtn by css {
        +btn
        backgroundColor = Color("#dda66d")
        border = "2px solid #b6821a"

        hover {
            backgroundColor = Color("#b6821a")
            borderColor = Color("#946912")
        }
    }

    val lightBtn by css {
        +btn
        backgroundColor = Color("#4eafc9")
        border = "2px solid #2a89a2"

        hover {
            backgroundColor = Color("#2a89a2")
            borderColor = Color("#1d6f85")
        }
    }
}