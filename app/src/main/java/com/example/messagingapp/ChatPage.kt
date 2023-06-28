package com.example.messagingapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomContainer(
    value: String,
    isMicIcon :Boolean,
    onSendClick:() -> Unit,
    onChangeValue:(String)->Unit
){
    Row(Modifier.padding(10.dp),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_round_attach_file_24), contentDescription ="file attachment" )
        Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(20.dp), elevation = 0.dp) {
            TextField(
                value = value,
                onValueChange = onChangeValue,
                placeholder = { Text(text = "Message") },

                )
        }
        if (isMicIcon) Image(painter = painterResource(id = R.drawable.ic_baseline_mic_24), contentDescription = "Mic" )
        else Image(painter = painterResource(id = R.drawable.ic_baseline_send_24), contentDescription = "send button", modifier = Modifier.clickable {
            onSendClick()
        })
    }
}

@Composable
fun ContactHeader(
    state: State,
    painter: Painter,
    onMenuClick:()->Unit
){

    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(10.dp)) {

        Image(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription ="arrow Back" )

        Card(
            Modifier
                .height(50.dp)
                .width(50.dp), shape = RoundedCornerShape(25.dp)
        ) {
            Image(painter = painter, contentDescription = "")
        }
        Column(
            Modifier
                .weight(1f)
        ) {
            Text(text = state.chatId, fontSize = 18.sp)
//            Text(text = "Online", color = Color(0xFF82C492))
        }

        Image(painter = painterResource(id = R.drawable.ic_baseline_more_vert_24), contentDescription = "menu", modifier = Modifier.clickable {
            onMenuClick()
         println("click")
        } )


    }

}
@Composable
fun MessageContainer(
    text:String,
    date:String,
    alignment: Alignment.Horizontal,
    color: Color,
    onMessageClick:()->Unit,

    ){

    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, end = 10.dp, start = 10.dp), horizontalAlignment = alignment) {
        Card(
            modifier = Modifier.clickable {
                onMessageClick()
            },
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(Modifier.background(color)) {
                Column (Modifier.padding(10.dp), horizontalAlignment = Alignment.End){
                    Text(modifier = Modifier, text = text )
                    Text(text = date, textAlign = TextAlign.End)
                }
            }
        }

    }


}