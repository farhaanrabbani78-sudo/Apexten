package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.StudyPrize
import com.example.model.Subject
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimaryLight

@Composable
fun PrizesScreen(
  prizes: List<StudyPrize>,
  totalSparks: Int,
  selectedPrizeForStudy: StudyPrize?,
  onUnlockPrize: (StudyPrize) -> Unit,
  onViewPrize: (StudyPrize) -> Unit,
  onCloseViewer: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedSubjectFilter by remember { mutableStateOf<Subject?>(null) }
  var pendingUnlockPrize by remember { mutableStateOf<StudyPrize?>(null) }

  val filteredPrizes = if (selectedSubjectFilter != null) {
    prizes.filter { it.subject == selectedSubjectFilter }
  } else {
    prizes
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Header & Balance
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Knowledge Vault",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
          )
          Text(
            text = "Unlock high-yield formulas & concept maps using Sparks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = ApexGold.copy(alpha = 0.15f),
          border = androidx.compose.foundation.BorderStroke(1.dp, ApexGold)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ApexGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "$totalSparks Sparks",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onBackground
            )
          }
        }
      }
    }

    // Filter Chips
    item {
      LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
          FilterChip(
            selected = selectedSubjectFilter == null,
            onClick = { selectedSubjectFilter = null },
            label = { Text("All Prizes") },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
            )
          )
        }
        items(Subject.values()) { subj ->
          FilterChip(
            selected = selectedSubjectFilter == subj,
            onClick = { selectedSubjectFilter = subj },
            label = { Text(subj.displayName) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
            )
          )
        }
      }
    }

    // Prizes List
    items(filteredPrizes) { prize ->
      PrizeCard(
        prize = prize,
        totalSparks = totalSparks,
        onUnlockClick = { pendingUnlockPrize = prize },
        onViewClick = { onViewPrize(prize) }
      )
    }
  }

  // Unlock Confirmation Dialog
  if (pendingUnlockPrize != null) {
    val prize = pendingUnlockPrize!!
    val canAfford = totalSparks >= prize.sparkCost
    AlertDialog(
      onDismissRequest = { pendingUnlockPrize = null },
      icon = {
        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ApexGold, modifier = Modifier.size(32.dp))
      },
      title = { Text("Unlock Study Aid?") },
      text = {
        Column {
          Text(prize.title, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(4.dp))
          Text(prize.description, style = MaterialTheme.typography.bodySmall)
          Spacer(modifier = Modifier.height(12.dp))
          if (canAfford) {
            Text(
              "Cost: ${prize.sparkCost} Sparks (Remaining: ${totalSparks - prize.sparkCost} Sparks)",
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.SemiBold
            )
          } else {
            Text(
              "You need ${prize.sparkCost - totalSparks} more Sparks. Complete another 10-20 min test drill to earn Sparks!",
              color = MaterialTheme.colorScheme.error,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val toUnlock = pendingUnlockPrize!!
            pendingUnlockPrize = null
            onUnlockPrize(toUnlock)
          },
          enabled = canAfford,
          colors = ButtonDefaults.buttonColors(containerColor = ApexGold),
          modifier = Modifier.testTag("confirm_unlock_prize_button")
        ) {
          Text("Unlock Now")
        }
      },
      dismissButton = {
        TextButton(onClick = { pendingUnlockPrize = null }) {
          Text("Cancel")
        }
      }
    )
  }

  // Detailed Study Note Viewer Dialog
  if (selectedPrizeForStudy != null) {
    StudyNoteViewerDialog(
      prize = selectedPrizeForStudy,
      onDismiss = onCloseViewer
    )
  }
}

@Composable
fun PrizeCard(
  prize: StudyPrize,
  totalSparks: Int,
  onUnlockClick: () -> Unit,
  onViewClick: () -> Unit
) {
  val isUnlocked = prize.isUnlocked

  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isUnlocked) MaterialTheme.colorScheme.surface
      else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 2.dp else 0.dp),
    border = if (isUnlocked) androidx.compose.foundation.BorderStroke(1.5.dp, ApexEmerald.copy(alpha = 0.5f))
    else null,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Tag Pills
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.primaryContainer
          ) {
            Text(
              text = prize.subject.displayName,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
          ) {
            Text(
              text = prize.type.label,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSecondaryContainer,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        // Unlocked Badge or Cost
        if (isUnlocked) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = ApexEmerald.copy(alpha = 0.15f)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ApexEmerald, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("UNLOCKED", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = ApexEmerald)
            }
          }
        } else {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${prize.sparkCost} ⚡",
              fontWeight = FontWeight.Bold,
              style = MaterialTheme.typography.labelMedium,
              color = ApexGold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = prize.title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = prize.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Key concepts preview bullet points
      prize.keyConcepts.take(2).forEach { concept ->
        Row(
          modifier = Modifier.padding(vertical = 2.dp),
          verticalAlignment = Alignment.Top
        ) {
          Text("• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
          Text(
            text = concept,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            fontSize = 11.sp,
            lineHeight = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Button
      if (isUnlocked) {
        Button(
          onClick = onViewClick,
          colors = ButtonDefaults.buttonColors(containerColor = ApexEmerald),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("prize_action_button_${prize.id}")
        ) {
          Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Open & Study Formula Sheet")
        }
      } else {
        Button(
          onClick = onUnlockClick,
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimaryLight),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("prize_action_button_${prize.id}")
        ) {
          Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ApexGold, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Unlock with ${prize.sparkCost} Sparks")
        }
      }
    }
  }
}

@Composable
fun StudyNoteViewerDialog(
  prize: StudyPrize,
  onDismiss: () -> Unit
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .fillMaxSize(0.85f)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp)
      ) {
        // Dialog Top Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = prize.title,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "${prize.subject.displayName} • ${prize.type.label}",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Study Content Viewer
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = prize.detailedStudyNotes,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace,
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimaryLight),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Got It! Return to Hub")
        }
      }
    }
  }
}
