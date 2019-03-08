package com.example.frankito.hive.ui.activity

import android.content.Intent
import android.widget.LinearLayout
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.ui.base.BaseActivity
import com.example.frankito.hive.ui.fragment.GameMenuFragment
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup
import com.example.frankito.hive.ui.view.Insects.*
import com.example.frankito.hive.util.DisableDragListener
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_game.*

fun startGameActivity(activity: BaseActivity) {
    val intent = Intent(activity, GameActivity::class.java)
    activity.startActivity(intent)
}

class GameActivity : BaseActivity() {

    private lateinit var playerOneLayoutsRes : Array<LinearLayout>
    private lateinit var playerTwoLayoutsRes : Array<LinearLayout>

    private lateinit var gameManager: GameManager

    private lateinit var realm : Realm

    override fun getContentView() = R.layout.activity_game

    override fun initUi() {

        realm = Realm.getDefaultInstance()

        btnMenu.setOnClickListener {
            val fm = supportFragmentManager
            val gameMenuFragment = GameMenuFragment.newInstance()
            gameMenuFragment.show(fm ,"fragment_game_menu")
        }

        initLayouts()

        val hexaGrid = HexaViewGroup(this)
        hexa_grid_layout.addView(hexaGrid)

        gameManager = GameManager(this, hexaGrid)
        gameManager.initGame()

    }

    fun restoreStartDragViews(){
        gameManager.restoreStartDragViews()
        gameManager.restoreLogicDisabledViews()
        gameManager.restoreEnabledViews()
    }

    fun droppedAt(row: Int, col: Int) {
        gameManager.droppedAt(row, col)
    }

    fun dragStartedAt(element: HexaElement){
        gameManager.dragStartedAt(element)
    }

    override fun onResume() {
        setPlayerOneTurn()
        GameManager.firstMove = true
        super.onResume()
    }

    private fun initLayouts() {

        playerOneLayoutsRes = arrayOf(
                first_player_layout_queen,
                first_player_layout_ant,
                first_player_layout_stagbeetle,
                first_player_layout_spider,
                first_player_layout_grasshopper
        )

        playerTwoLayoutsRes = arrayOf(
                second_player_layout_queen,
                second_player_layout_ant,
                second_player_layout_stagbeetle,
                second_player_layout_spider,
                second_player_layout_grasshopper
        )

        val radius = resources.getDimension(R.dimen.radius)
        val height = 2 * radius
        val layoutParams = LinearLayout.LayoutParams(height.toInt(), height.toInt())
        layoutParams.setMargins(0, 0, 0, 8)

        for (it in playerOneLayoutsRes) {
            it.layoutParams = layoutParams
            it.setOnDragListener(DisableDragListener(this))
        }

        for (it in playerTwoLayoutsRes) {
            it.layoutParams = layoutParams
            it.setOnDragListener(DisableDragListener(this))
        }

    }

    fun setPlayerOneTurn() {
        first_player_scroll.alpha = 1F
        second_player_scroll.alpha = 0.5F

        gameManager.setPlayerOneTurn()
    }

    fun setPlayerTwoTurn() {
        first_player_scroll.alpha = 0.5F
        second_player_scroll.alpha = 1F

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
