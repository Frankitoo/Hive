package com.example.frankito.hive.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ScrollView
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup
import com.example.frankito.hive.ui.view.Insects.*
import com.example.frankito.hive.util.DisableDragListener
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    private lateinit var viewHolder: Viewholder

    inner class Viewholder {

        internal val hexaGrid = findViewById<HexaViewGroup>(R.id.hexa_grid)

        internal val firstPlayerScrollView = findViewById<ScrollView>(R.id.first_player_scroll)
        internal val secondPlayerScrollView = findViewById<ScrollView>(R.id.second_player_scroll)

        internal val firstPlayerSetLayout = findViewById<LinearLayout>(R.id.first_player_set_layout)
        internal val secondPlayerSetLayout = findViewById<LinearLayout>(R.id.second_player_set_layout)
        internal val hexaLayout = findViewById<LinearLayout>(R.id.hexa_grid_layout)

        internal val firstPlayerLayoutQueen = findViewById<LinearLayout>(R.id.first_player_layout_queen)
        internal val firstPlayerLayoutAnt = findViewById<LinearLayout>(R.id.first_player_layout_ant)
        internal val firstPlayerLayoutStagbeetle = findViewById<LinearLayout>(R.id.first_player_layout_stagbeetle)
        internal val firstPlayerLayoutSpider = findViewById<LinearLayout>(R.id.first_player_layout_spider)
        internal val firstPlayerLayoutGrasshopper = findViewById<LinearLayout>(R.id.first_player_layout_grasshopper)

        internal val secondPlayerLayoutQueen = findViewById<LinearLayout>(R.id.second_player_layout_queen)
        internal val secondPlayerLayoutAnt = findViewById<LinearLayout>(R.id.second_player_layout_ant)
        internal val secondPlayerLayoutStagbeetle = findViewById<LinearLayout>(R.id.second_player_layout_stagbeetle)
        internal val secondPlayerLayoutSpider = findViewById<LinearLayout>(R.id.second_player_layout_spider)
        internal val secondPlayerLayoutGrasshopper = findViewById<LinearLayout>(R.id.second_player_layout_grasshopper)
    }

    private lateinit var playerOneLayouts: ArrayList<LinearLayout>
    private lateinit var playerTwoLayouts: ArrayList<LinearLayout>

    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewHolder = Viewholder()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initLayouts()

        gameManager = GameManager(this@GameActivity, viewHolder.hexaGrid)
        gameManager.initGame()

    }

    fun restoreViews(){
        gameManager.restoreViews()
    }

    fun droppedAt(row: Int, col: Int) {
        gameManager.droppedAt(row, col)
    }

    fun dragStartedAt(row: Int,col: Int){
        gameManager.dragStartedAt(row, col)
    }

    override fun onResume() {
        setPlayerOneTurn()
        GameManager.firstMove = true
        super.onResume()
    }

    private fun initLayouts() {

        playerOneLayouts = ArrayList()
        playerTwoLayouts = ArrayList()

        viewHolder.firstPlayerSetLayout.setOnDragListener(DisableDragListener(this@GameActivity))
        viewHolder.secondPlayerSetLayout.setOnDragListener(DisableDragListener(this@GameActivity))
        viewHolder.firstPlayerScrollView.setOnDragListener(DisableDragListener(this@GameActivity))
        viewHolder.secondPlayerScrollView.setOnDragListener(DisableDragListener(this@GameActivity))
        viewHolder.hexaLayout.setOnDragListener(DisableDragListener(this@GameActivity))

        playerOneLayouts.add(viewHolder.firstPlayerLayoutAnt)
        playerOneLayouts.add(viewHolder.firstPlayerLayoutGrasshopper)
        playerOneLayouts.add(viewHolder.firstPlayerLayoutSpider)
        playerOneLayouts.add(viewHolder.firstPlayerLayoutStagbeetle)
        playerOneLayouts.add(viewHolder.firstPlayerLayoutQueen)

        playerTwoLayouts.add(viewHolder.secondPlayerLayoutAnt)
        playerTwoLayouts.add(viewHolder.secondPlayerLayoutGrasshopper)
        playerTwoLayouts.add(viewHolder.secondPlayerLayoutSpider)
        playerTwoLayouts.add(viewHolder.secondPlayerLayoutStagbeetle)
        playerTwoLayouts.add(viewHolder.secondPlayerLayoutQueen)

        val radius = resources.getDimension(R.dimen.radius)
        val height = 2 * radius
        val layoutParams = LinearLayout.LayoutParams(height.toInt(), height.toInt())
        layoutParams.setMargins(0, 0, 0, 8)

        for (it in playerOneLayouts) {
            it.layoutParams = layoutParams
            it.setOnDragListener(DisableDragListener(this@GameActivity))
        }

        for (it in playerTwoLayouts) {
            it.layoutParams = layoutParams
            it.setOnDragListener(DisableDragListener(this@GameActivity))
        }

    }

    fun setPlayerOneTurn() {
        viewHolder.firstPlayerScrollView.alpha = 1F
        viewHolder.secondPlayerScrollView.alpha = 0.5F

        gameManager.setPlayerOneTurn()
    }

    fun setPlayerTwoTurn() {
        viewHolder.firstPlayerScrollView.alpha = 0.5F
        viewHolder.secondPlayerScrollView.alpha = 1F

        gameManager.setPlayerTwoTurn()
    }

    fun insertInsectToLayout(hexaElement: HexaElement) {
        when (hexaElement) {
            is Ant -> {
                when (hexaElement.whichPlayer) {
                    HexaElement.WhichPlayer.PLAYERONE -> {
                        first_player_layout_ant.addView(hexaElement)
                    }
                    HexaElement.WhichPlayer.PLAYERTWO -> {
                        second_player_layout_ant.addView(hexaElement)
                    }
                }
            }
            is Grasshopper -> {
                when (hexaElement.whichPlayer) {
                    HexaElement.WhichPlayer.PLAYERONE -> {
                        first_player_layout_grasshopper.addView(hexaElement)
                    }
                    HexaElement.WhichPlayer.PLAYERTWO -> {
                        second_player_layout_grasshopper.addView(hexaElement)
                    }
                }
            }
            is Spider -> {
                when (hexaElement.whichPlayer) {
                    HexaElement.WhichPlayer.PLAYERONE -> {
                        first_player_layout_spider.addView(hexaElement)
                    }
                    HexaElement.WhichPlayer.PLAYERTWO -> {
                        second_player_layout_spider.addView(hexaElement)
                    }
                }
            }
            is Stagbeetle -> {
                when (hexaElement.whichPlayer) {
                    HexaElement.WhichPlayer.PLAYERONE -> {
                        first_player_layout_stagbeetle.addView(hexaElement)
                    }
                    HexaElement.WhichPlayer.PLAYERTWO -> {
                        second_player_layout_stagbeetle.addView(hexaElement)
                    }
                }
            }
            is Queen -> {
                when (hexaElement.whichPlayer) {
                    HexaElement.WhichPlayer.PLAYERONE -> {
                        first_player_layout_queen.addView(hexaElement)
                    }
                    HexaElement.WhichPlayer.PLAYERTWO -> {
                        second_player_layout_queen.addView(hexaElement)
                    }
                }
            }
            else -> {
                //Nothing here
            }
        }

    }

}
