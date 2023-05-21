@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.calendar.account.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.calendar.R

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
) {
    if (viewModel.state.error != null) {
        AlertDialog(onDismissRequest = viewModel::dismissError, confirmButton = {
            Button(onClick = viewModel::dismissError) {
                Text(text = stringResource(id = R.string.ok))
            }
        }, text = { Text(text = viewModel.state.error ?: "") })
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 30.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (viewModel.state.currentUser == null) {
            Text(
                text = stringResource(id = R.string.account_welcome),
                style = MaterialTheme.typography.labelMedium
            )

            EmailField(
                modifier = Modifier.padding(vertical = 20.dp),
                value = viewModel.state.email,
                onValueChange = viewModel::onEmailChange
            )
            PasswordField(
                value = viewModel.state.password,
                onValueChange = viewModel::onPasswordChange
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = viewModel::signIn) {
                Text(text = stringResource(id = R.string.sign_in))
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = viewModel::signUp) {
                Text(text = stringResource(id = R.string.sign_up))
            }
        } else {
            Text(text = stringResource(id = R.string.congratulation_for_sign_in))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "${stringResource(id = R.string.your_email)}: ${viewModel.state.currentUser!!.email!!}")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = viewModel::signOut) {
                Text(text = stringResource(id = R.string.sign_out))
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
}

@Composable
fun PasswordField(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = R.string.password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}