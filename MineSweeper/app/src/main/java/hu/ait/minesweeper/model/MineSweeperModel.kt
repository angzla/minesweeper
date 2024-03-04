package hu.ait.mineSweeper.model

data class Field(var type: Int, var minesAround: Int,
                 var isFlagged: Boolean, var wasClicked: Boolean)

object MinesweeperModel {
    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(0, 1, false, false),
            Field(0, 2, false, false),
            Field(0, 2, false, false),
            Field(0, 1, false, false),
            Field(0, 0, false, false)
        ), arrayOf(
            Field(0, 1, false, false),
            Field(1, 1, false, false),
            Field(1, 1, false, false),
            Field(0, 1, false, false),
            Field(0, 0, false, false)
        ), arrayOf(
            Field(0, 1, false, false),
            Field(0, 2, false, false),
            Field(0, 2, false, false),
            Field(0, 1, false, false),
            Field(0, 0, false, false)
        ), arrayOf(
            Field(0, 1, false, false),
            Field(0, 1, false, false),
            Field(0, 1, false, false),
            Field(0, 0, false, false),
            Field(0, 0, false, false)
        ), arrayOf(
            Field(0, 1, false, false),
            Field(1, 0, false, false),
            Field(0, 1, false, false),
            Field(0, 0, false, false),
            Field(0, 0, false, false)
        )
    )

    fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y]


    fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }
    }

    fun getWinner(): Boolean {
        for (i in 0..4) {
            for (j in 0..4) {
                if (fieldMatrix[i][j].type == 1 && fieldMatrix[i][j].isFlagged == false){
                    return false
                }
            }
        }
        return true
    }

    fun getLoser(): Boolean {
        for (i in 0..4) {
            for (j in 0..4) {
                if (fieldMatrix[i][j].type == 0 &&
                    fieldMatrix[i][j].isFlagged == true){
                    return true
                }
                if (fieldMatrix[i][j].type == 1 &&
                    fieldMatrix[i][j].wasClicked == true){
                    return true
                }
            }
        }
        return false
    }

}