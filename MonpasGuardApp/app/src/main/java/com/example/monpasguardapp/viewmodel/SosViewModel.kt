package com.example.monpasguardapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.monpasguardapp.data.SosLocation
import com.example.monpasguardapp.data.SosReport
import java.time.Instant

class SosViewModel : ViewModel() {

    var selectedLocation by mutableStateOf(SosLocation.MONPAS)
        private set

    var selectedIncidentType by mutableStateOf<String?>(null)
        private set

    var note by mutableStateOf("")
        private set

    var showConfirmationDialog by mutableStateOf(false)
        private set

    var reportSent by mutableStateOf(false)
        private set

    fun selectLocation(location: SosLocation) {
        selectedLocation = location
    }

    fun selectIncidentType(incidentType: String) {
        selectedIncidentType = incidentType
    }

    fun updateNote(newNote: String) {
        note = newNote.take(200)
    }

    fun openConfirmationDialog() {
        if (selectedIncidentType != null) {
            showConfirmationDialog = true
        }
    }

    fun closeConfirmationDialog() {
        showConfirmationDialog = false
    }

    fun sendReport(): SosReport {
        val report = SosReport(
            location = selectedLocation.label,
            incidentType = selectedIncidentType ?: "Other",
            note = note,
            timestamp = Instant.now().toString()
        )

        showConfirmationDialog = false
        reportSent = true

        return report
    }

    fun resetReport() {
        selectedLocation = SosLocation.MONPAS
        selectedIncidentType = null
        note = ""
        reportSent = false
    }
}