package com.example.monpasguardapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monpasguardapp.ui.theme.MonpasGuardAppTheme
import com.example.monpasguardapp.viewmodel.SosViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.monpasguardapp.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import com.example.monpasguardapp.data.SosLocation

@Composable
fun SosScreen(
    viewModel: SosViewModel
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        val focusManager = LocalFocusManager.current

        Image(
            painter = painterResource(id = R.drawable.logo_monpas_guard),
            contentDescription = "Logo de Monpás Guard",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Do you need help?",
            modifier = Modifier.fillMaxWidth(),
            color = colors.onSurface,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Send an alert to the safety team",
            modifier = Modifier.fillMaxWidth(),
            color = colors.onSurface.copy(alpha = 0.70f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        LocationSelector(
            selectedLocation = viewModel.selectedLocation,
            onLocationSelected = viewModel::selectLocation
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "What happened?",
            color = colors.onSurface,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        IncidentOptions(
            selectedIncident = viewModel.selectedIncidentType,
            onIncidentSelected = viewModel::selectIncidentType
        )

        OutlinedTextField(
            value = viewModel.note,
            onValueChange = viewModel::updateNote,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Note (optional)") },
            placeholder = { Text("Add more details if needed...") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            minLines = 3,
            maxLines = 3,
            supportingText = {
                Text("${viewModel.note.length}/200")
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                focusedIndicatorColor = colors.primary,
                unfocusedIndicatorColor = colors.outline,
                focusedLabelColor = colors.primary
            ),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.openConfirmationDialog()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            enabled = viewModel.selectedIncidentType != null,
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.tertiary,
                disabledContainerColor = colors.tertiary.copy(alpha = 0.35f),
                contentColor = colors.onTertiary,
                disabledContentColor = colors.onTertiary.copy(alpha = 0.7f)
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_sos),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = colors.onTertiary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "SEND SOS ALERT",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "If there is immediate danger, call 112",
            modifier = Modifier.fillMaxWidth(),
            color = colors.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
    if (viewModel.showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = viewModel::closeConfirmationDialog,
            title = {
                Text("Send SOS alert?")
            },
            text = {
                Text(
                    "You are about to report a ${viewModel.selectedIncidentType} " +
                            "incident at \${viewModel.selectedLocation.label}."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.sendReport()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.tertiary
                    )
                ) {
                    Text("Send alert")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = viewModel::closeConfirmationDialog
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (viewModel.reportSent) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("SOS alert created")
            },
            text = {
                Text(
                    "Your SOS alert has been registered. " +
                            "A connection to the safety system will be added next."
                )
            },
            confirmButton = {
                Button(
                    onClick = viewModel::resetReport,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary
                    )
                ) {
                    Text("Send another alert")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationSelector(
    selectedLocation: SosLocation,
    onLocationSelected: (SosLocation) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val locations = SosLocation.entries
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedLocation.label,
            onValueChange = {},
            readOnly = true,
            label = { Text("Location") },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .menuAnchor(),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                focusedIndicatorColor = colors.primary,
                unfocusedIndicatorColor = colors.outline,
                focusedLabelColor = colors.primary
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = null,
                    tint = colors.primary
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            locations.forEach { location ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(location.label) },
                    onClick = {
                        onLocationSelected(location)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun IncidentOptions(
    selectedIncident: String?,
    onIncidentSelected: (String) -> Unit
) {
    val incidents = listOf(
        "Person at risk" to R.drawable.ic_warning,
        "Strong waves" to R.drawable.ic_waves,
        "Possible fall" to R.drawable.ic_fall,
        "Other" to R.drawable.ic_more
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        incidents.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { (incident, iconRes) ->
                    IncidentCard(
                        text = incident,
                        iconRes = iconRes,
                        isSelected = incident == selectedIncident,
                        onClick = { onIncidentSelected(incident) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun IncidentCard(
    text: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = modifier
            .height(118.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) colors.primary else colors.outline
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) colors.secondaryContainer else colors.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .background(colors.surfaceVariant, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(38.dp),
                    tint = colors.onSurface
                )
            }

            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color = colors.onSurface,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview
@Composable
fun SosScreenPreview() {
    MonpasGuardAppTheme {
        SosScreen(
            viewModel = SosViewModel()
        )
    }
}
