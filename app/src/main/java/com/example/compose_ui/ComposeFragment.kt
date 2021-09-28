package com.example.compose_ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R

class ComposeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())

        view.apply {
            setContent {
                messageCard(message = "Hello Andorid!", "How are you")
            }
        }
        return view
    }

    @Composable
    fun ProgressBar() {
        CircularProgressIndicator()
    }

    @Composable
    fun messageCard(message: String, body: String) {
        Row(modifier = Modifier.padding(all = 8.dp)) {

            Image(
                painter = painterResource(R.drawable.ic_spash_logo),
                contentDescription = "this is compose",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(message)
                Spacer(modifier = Modifier.height(4.dp))
                Text(body)
            }
        }
    }

    @Preview
    @Composable
    fun previewMessageCard() {
        messageCard(message = "Hello Android!", "How are you?")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ComposeFragment()

    }
}