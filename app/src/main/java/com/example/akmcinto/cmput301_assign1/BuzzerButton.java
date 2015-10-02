/*
   Copyright 2015 Andrea McIntosh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.example.akmcinto.cmput301_assign1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

/*
    Class about a button for the game show buzzer.  Each player will have their own instance of buzzer button.
 */
public class BuzzerButton {

    private Integer playerNum;
    private Button button;
    private Context buzzerContext;
    private Integer numPlayers;
    private BuzzerWinners buzzerWinners = BuzzerWinners.getInstance();

    public BuzzerButton(Button button, final Integer playerNum, final Integer numPlayers, final Context buzzerContext) {
        this.button = button;
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWinner();
                buzzerWinners.addBuzzerWinner(numPlayers, playerNum, buzzerContext);
            }

        });
        this.playerNum = playerNum;
        this.numPlayers = numPlayers;
        this.buzzerContext = buzzerContext;
    }

    private void showWinner() {
        // http://stackoverflow.com/questions/26097513/android-simple-alert-dialog, MysticMagicϡ, 2015-09-26
        AlertDialog alertDialog = new AlertDialog.Builder(buzzerContext).create();
        alertDialog.setMessage("Player " + this.playerNum.toString() + " wins!");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

}
