package com.example.chat.ui.presentation

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.chat.datamodel.UserProfile
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.material.icons.filled.ArrowDropDown


@Composable
fun ProfileForm(
    profile: UserProfile,
    onProfileChange: (UserProfile) -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var genderExpanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other")

    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                onProfileChange(profile.copy(dob = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SnackbarHost(hostState = snackbarHostState)

        OutlinedTextField(
            value = profile.name,
            onValueChange = { onProfileChange(profile.copy(name = it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profile.email,
            onValueChange = {
                emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                onProfileChange(profile.copy(email = it))
            },
            label = { Text("Email") },
            isError = emailError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = profile.phone,
            onValueChange = {
                phoneError = it.length != 10
                onProfileChange(profile.copy(phone = it))
            },
            label = { Text("Phone") },
            isError = phoneError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gender Dropdown (stable)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = profile.gender,
                onValueChange = {},
                label = { Text("Gender") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { genderExpanded = true },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { genderExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select gender"
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = genderExpanded,
                onDismissRequest = { genderExpanded = false }
            ) {
                genderOptions.forEach { gender ->
                    DropdownMenuItem(
                        text = { Text(gender) },
                        onClick = {
                            onProfileChange(profile.copy(gender = gender))
                            genderExpanded = false
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date of Birth picker
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show()
                    focusManager.clearFocus()
                }
        ) {
            OutlinedTextField(
                value = profile.dob,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (profile.name.isBlank() || emailError || phoneError ||
                    profile.email.isBlank() || profile.phone.isBlank() ||
                    profile.gender.isBlank() || profile.dob.isBlank()
                ) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Please fill all fields correctly")
                    }
                } else {
                    onSaveClick()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Profile saved successfully")
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Profile")
        }
    }
}
