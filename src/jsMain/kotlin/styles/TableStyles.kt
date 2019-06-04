package styles

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.TextDecorationStyle
import styled.StyleSheet

object TableStyles : StyleSheet("TableStyles", isStatic = true) {
    val center by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
    }

    val table by css {
        display = Display.table
    }

    val tableRow by css {
        display = Display.tableRow
        backgroundColor = Color("#7FD0E1")
    }

    val tableCell by css {
        display = Display.tableCell
        border = "2px solid #858EDE"
        verticalAlign = VerticalAlign.middle
        padding = "10px"
        color = Color("#fff")
    }

    val bigHeader by css {
        color = Color("#858EDE")
        fontSize = 1.5.em
        textDecoration = TextDecoration(setOf(TextDecorationLine.underline))
    }

    val header by css {
        color = Color("#fff")
        fontSize = 1.3.em
    }
}