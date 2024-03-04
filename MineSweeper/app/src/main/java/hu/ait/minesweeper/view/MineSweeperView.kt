package hu.ait.mineSweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.mineSweeper.MainActivity
import hu.ait.mineSweeper.R
import hu.ait.mineSweeper.model.MinesweeperModel

class mineSweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBackground: Paint
    lateinit var paintLine: Paint

    lateinit var paintText: Paint
    lateinit var paintBomb: Paint

    lateinit var paintImage: Paint

    var bitmapCell: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.cell)

    var bitmapLoser: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.loser)

    var bitmapWinner: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.winner)

    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.BLACK)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.setColor(Color.WHITE)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText = Paint()
        paintText.setColor(Color.GREEN)
        paintText.textSize = 100f

        paintBomb = Paint()
        paintBomb.setColor(Color.RED)
        paintBomb.textSize = 100f

        paintImage = Paint()
        paintImage.setAlpha(150)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = height / 5f

        bitmapCell = Bitmap.createScaledBitmap(
            bitmapCell, width/5, height/5, false)

        bitmapLoser = Bitmap.createScaledBitmap(
            bitmapLoser, width/5, height/5, false)

        bitmapWinner = Bitmap.createScaledBitmap(
            bitmapWinner, width/5, height/5, false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawGrid(canvas)

        drawGameArea(canvas)

        drawPlayers(canvas)

        drawLoser(canvas)

        drawWinner(canvas)
    }

    private fun drawGrid(canvas: Canvas){
        for (i in 0..4) {
            for (j in 0..4) {
                canvas.drawBitmap(bitmapCell, (i) * width / 5f, (j) * height / 5f, null)
            }
        }
    }

    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // five horizontal lines
        canvas.drawLine(0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(),
            paintLine)
        canvas.drawLine(0f, (2 * height / 5).toFloat(), width.toFloat(),
            (2 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (3 * height / 5).toFloat(), width.toFloat(),
            (3 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (4 * height / 5).toFloat(), width.toFloat(),
            (4 * height / 5).toFloat(), paintLine)

        // five vertical lines
        canvas.drawLine((width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(),
            paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (MinesweeperModel.getFieldContent(i, j).type == 1 &&
                    MinesweeperModel.getFieldContent(i, j).isFlagged == true) {
                    canvas.drawText(context.getString(R.string.flag), (i)* width/5f, (j+1)*height/5f, paintText)
                }
                if (MinesweeperModel.getFieldContent(i, j).type == 1 &&
                    MinesweeperModel.getFieldContent(i, j).wasClicked == true) {
                    canvas.drawText(context.getString(R.string.bomb), i* width/5f, (j+1)*height/5f, paintBomb)
                }
                if (MinesweeperModel.getFieldContent(i, j).type == 0 &&
                    MinesweeperModel.getFieldContent(i, j).wasClicked == true){
                    canvas.drawText(
                        context.getString(
                            R.string.minesAround, MinesweeperModel.getFieldContent(i, j).minesAround
                        ), i* width/5f, (j+1)*height/5f, paintText)

                }
            }
        }
    }

    private fun drawLoser(canvas: Canvas) {
        if (MinesweeperModel.getLoser() == true){
            canvas.drawBitmap(bitmapLoser, null, canvas.clipBounds, paintImage)
        }
    }

    private fun drawWinner(canvas: Canvas) {
        if(MinesweeperModel.getWinner() == true){
            canvas.drawBitmap(bitmapWinner, null, canvas.clipBounds, paintImage)
        }

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            //check flag mode in MineSweeperView
            if ((context as MainActivity).isFlagModeOn()) {
                MinesweeperModel.getFieldContent(tX, tY).isFlagged = true
                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(tX, tY).type == 0 &&
                    MinesweeperModel.getFieldContent(tX, tY).isFlagged == true) {
                    (context as MainActivity).showMessage(context.getString(R.string.no_mine_you_lose))
                    invalidate()
                }
                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(tX, tY).type == 1 &&
                    MinesweeperModel.getFieldContent(tX, tY).isFlagged == true) {
                    invalidate()

                    if (MinesweeperModel.getWinner() == true) {
                        (context as MainActivity).showMessage(context.getString(R.string.winner))
                        invalidate()
                    }
                }

            }
             else {
                MinesweeperModel.getFieldContent(tX, tY).wasClicked = true
                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(tX, tY).type == 1 &&
                    MinesweeperModel.getFieldContent(tX, tY).wasClicked == true) {
                    (context as MainActivity).showMessage(context.getString(R.string.mine_you_lose))
                    invalidate()
                }
                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(tX, tY).type == 0 &&
                    MinesweeperModel.getFieldContent(tX, tY).wasClicked == true) {
                    invalidate()

                }
            }
        }
        return true
    }

    fun resetGame() {
        MinesweeperModel.resetModel()
        invalidate()
    }

}