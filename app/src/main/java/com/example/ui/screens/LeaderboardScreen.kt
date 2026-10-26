package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeaderboardEntry
import com.example.ui.theme.ApexEmerald
import com.example.ui.theme.ApexGold
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryLight

@Composable
fun LeaderboardScreen(
  leaderboard: List<LeaderboardEntry>,
  totalSparks: Int,
  modifier: Modifier = Modifier
) {
  val top3 = leaderboard.take(3)
  val remainingList = leaderboard.drop(3)
  val currentUser = leaderboard.firstOrNull { it.isCurrentUser }

  Box(modifier = modifier.fillMaxSize()) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(top = 16.dp, bottom = 140.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Header
      item {
        Column {
          Text(
            text = "Class 10 Champions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
          )
          Text(
            text = "Earn Sparks & maintain test streaks to climb the ranks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // Podium Top 3 Showcase
      if (top3.isNotEmpty()) {
        item {
          PodiumCard(top3 = top3)
        }
      }

      // Tier Milestone Banner
      item {
        TierMilestoneCard(totalSparks = totalSparks)
      }

      // Section Header
      item {
        Text(
          text = "Full Class Rankings",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      // Rankings List
      items(remainingList) { entry ->
        LeaderboardRow(entry = entry)
      }
    }

    // Sticky Bottom Bar for Current User's Status
    if (currentUser != null) {
      Surface(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .fillMaxWidth()
          .padding(bottom = 76.dp, start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 8.dp,
        color = ApexPrimary,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF60A5FA))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = ApexGold,
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = "#${currentUser.rank}",
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF78350F),
                  fontSize = 13.sp
                )
              }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "${currentUser.name} (${currentUser.tier})",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
              )
              Text(
                text = "${currentUser.streakDays} Day Streak • ${currentUser.testsCount} Drills",
                color = Color(0xFFBFDBFE),
                style = MaterialTheme.typography.labelSmall
              )
            }
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ApexGold, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${currentUser.sparks}",
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              style = MaterialTheme.typography.titleMedium
            )
          }
        }
      }
    }
  }
}

@Composable
fun PodiumCard(top3: List<LeaderboardEntry>) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
      ) {
        // 2nd Place (Left)
        if (top3.size > 1) {
          PodiumColumn(
            entry = top3[1],
            rankColor = Color(0xFF94A3B8), // Silver
            crown = "🥈",
            height = 110.dp,
            rankNumber = 2
          )
        }

        // 1st Place (Center, Tallest)
        if (top3.isNotEmpty()) {
          PodiumColumn(
            entry = top3[0],
            rankColor = ApexGold, // Gold
            crown = "👑",
            height = 140.dp,
            rankNumber = 1
          )
        }

        // 3rd Place (Right)
        if (top3.size > 2) {
          PodiumColumn(
            entry = top3[2],
            rankColor = Color(0xFFD97706), // Bronze
            crown = "🥉",
            height = 90.dp,
            rankNumber = 3
          )
        }
      }
    }
  }
}

@Composable
fun PodiumColumn(
  entry: LeaderboardEntry,
  rankColor: Color,
  crown: String,
  height: androidx.compose.ui.unit.Dp,
  rankNumber: Int
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Bottom
  ) {
    Text(text = crown, fontSize = 22.sp)
    Spacer(modifier = Modifier.height(4.dp))

    // Avatar Circle
    Box(
      modifier = Modifier
        .size(46.dp)
        .clip(CircleShape)
        .background(Color(entry.avatarColorHex))
        .border(2.dp, rankColor, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = entry.avatarInitial,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = entry.name.take(9),
      style = MaterialTheme.typography.labelMedium,
      fontWeight = FontWeight.Bold,
      maxLines = 1
    )
    Text(
      text = "${entry.sparks} ⚡",
      style = MaterialTheme.typography.labelSmall,
      fontWeight = FontWeight.ExtraBold,
      color = ApexGold
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Podium Pillar
    Box(
      modifier = Modifier
        .width(76.dp)
        .height(height)
        .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(rankColor.copy(alpha = 0.35f), rankColor.copy(alpha = 0.15f))
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "#$rankNumber",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.ExtraBold,
        color = rankColor
      )
    }
  }
}

@Composable
fun TierMilestoneCard(totalSparks: Int) {
  val (nextTier, needed) = when {
    totalSparks < 180 -> "Gold" to (180 - totalSparks)
    totalSparks < 300 -> "Diamond" to (300 - totalSparks)
    totalSparks < 400 -> "Apex Scholar" to (400 - totalSparks)
    else -> "Max Level Reached" to 0
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        Icons.Default.WorkspacePremium,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(28.dp)
      )
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = if (needed > 0) "Next Milestone: $nextTier Tier" else "Apex Scholar Champion!",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = if (needed > 0) "Earn $needed more Sparks in diagnostic tests to level up."
          else "You are in the top tier of Class 10 scholars!",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
fun LeaderboardRow(entry: LeaderboardEntry) {
  val isMe = entry.isCurrentUser

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isMe) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
      else MaterialTheme.colorScheme.surface
    ),
    border = if (isMe) androidx.compose.foundation.BorderStroke(1.5.dp, ApexPrimaryLight) else null,
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "#${entry.rank}",
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.width(32.dp)
        )

        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(entry.avatarColorHex)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = entry.avatarInitial,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = entry.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = if (isMe) FontWeight.ExtraBold else FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = entry.tier,
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("•", color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = ApexGold, modifier = Modifier.size(12.dp))
            Text(
              text = "${entry.streakDays}d",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = ApexGold, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "${entry.sparks}",
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
    }
  }
}
