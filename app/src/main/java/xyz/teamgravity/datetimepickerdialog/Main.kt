package xyz.teamgravity.datetimepickerdialog

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import xyz.teamgravity.datetimepickerdialog.ui.theme.DateTimePickerDialogTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateTimePickerDialogTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }
                    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm") }

                    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
                    var pickedTime by remember { mutableStateOf(LocalTime.now()) }

                    val formattedDate by remember { derivedStateOf { dateFormatter.format(pickedDate) } }
                    val formattedTime by remember { derivedStateOf { timeFormatter.format(pickedTime) } }

                    val dateDialogState = rememberMaterialDialogState()
                    val timeDialogState = rememberMaterialDialogState()

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(onClick = dateDialogState::show) {
                            Text(text = stringResource(id = R.string.pick_date))
                        }
                        Text(text = formattedDate)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = timeDialogState::show) {
                            Text(text = stringResource(id = R.string.pick_time))
                        }
                        Text(text = formattedTime)
                    }
                    MaterialDialog(
                        dialogState = dateDialogState,
                        buttons = {
                            positiveButton(
                                text = stringResource(id = R.string.ok),
                                onClick = {
                                    Toast.makeText(applicationContext, getString(R.string.clicked_ok), Toast.LENGTH_SHORT).show()
                                }
                            )
                            negativeButton(text = stringResource(id = R.string.cancel))
                        },
                        content = {
                            datepicker(
                                initialDate = pickedDate,
                                title = stringResource(id = R.string.pick_date),
                                allowedDateValidator = { it.dayOfMonth % 2 == 0 },
                                onDateChange = { pickedDate = it }
                            )
                        }
                    )
                    MaterialDialog(
                        dialogState = timeDialogState,
                        buttons = {
                            positiveButton(
                                text = stringResource(id = R.string.ok),
                                onClick = {
                                    Toast.makeText(applicationContext, getString(R.string.clicked_ok), Toast.LENGTH_SHORT).show()
                                }
                            )
                            negativeButton(text = stringResource(id = R.string.cancel))
                        },
                        content = {
                            timepicker(
                                initialTime = pickedTime,
                                title = stringResource(id = R.string.pick_time),
                                timeRange = LocalTime.MIDNIGHT..LocalTime.NOON,
                                onTimeChange = { pickedTime = it }
                            )
                        }
                    )
                }
            }
        }
    }
}