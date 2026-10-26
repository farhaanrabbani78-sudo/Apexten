package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.Class10QuestionBank
import com.example.data.PrizeCatalog
import com.example.data.StudentProfileManager
import com.example.model.Subject
import com.example.model.TestDuration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ApexTen", appName)
  }

  @Test
  fun `question bank provides core Class 10 questions`() {
    val mathQuestions = Class10QuestionBank.getQuestionsForTest(Subject.MATHEMATICS, 5)
    assertTrue("Should return 5 math questions", mathQuestions.size == 5)
    mathQuestions.forEach { q ->
      assertEquals(Subject.MATHEMATICS, q.subject)
      assertEquals("Question must have 4 options", 4, q.options.size)
      assertTrue("Correct index must be 0..3", q.correctIndex in 0..3)
      assertTrue("Explanation must not be empty", q.explanation.isNotBlank())
    }
  }

  @Test
  fun `prize catalog and unlocking mechanics`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val profile = StudentProfileManager(context)
    profile.totalSparks = 200

    val initialUnlocked = profile.getUnlockedPrizeIds()
    assertTrue("Trig sheet should be unlocked by default", initialUnlocked.contains("prize_trig_sheet"))

    val success = profile.unlockPrize("prize_chem_reactions", 80)
    assertTrue("Should succeed unlocking prize with sufficient sparks", success)
    assertTrue(profile.getUnlockedPrizeIds().contains("prize_chem_reactions"))
    assertEquals(120, profile.totalSparks)
  }

  @Test
  fun `leaderboard includes current user and sorts by sparks`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val profile = StudentProfileManager(context)
    profile.totalSparks = 500

    val leaderboard = profile.getLeaderboard()
    val topUser = leaderboard.first()
    assertEquals("Aryan Sharma (You)", topUser.name)
    assertEquals(1, topUser.rank)
  }

  @Test
  fun `login and profile name modification flow`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val profile = StudentProfileManager(context)

    // Initial first-time open
    profile.performLogout()
    assertTrue("Should be logged out after logout", !profile.isLoggedIn)

    // Log in via Google
    profile.performLogin("Farhaan Rabbani", "farhaanrabbani78@gmail.com", "+91 98765 43210", "Google")
    assertTrue("User should be logged in", profile.isLoggedIn)
    assertEquals("Farhaan Rabbani", profile.studentName)
    assertEquals("Google", profile.authProvider)
    assertEquals("farhaanrabbani78@gmail.com", profile.studentEmail)

    // Update Name from Account/Settings tab
    profile.updateProfileDetails(
      name = "Farhaan Rabbani (Board Topper)",
      email = "farhaanrabbani78@gmail.com",
      phone = "+91 98765 43210",
      school = "Delhi Public School",
      board = "CBSE Class 10",
      goal = "Score 98%+"
    )
    assertEquals("Farhaan Rabbani (Board Topper)", profile.studentName)

    // Verify name updates on Leaderboard
    val updatedLeaderboard = profile.getLeaderboard()
    val currentUser = updatedLeaderboard.first { it.isCurrentUser }
    assertEquals("Farhaan Rabbani (Board Topper) (You)", currentUser.name)

    // Test sign out
    profile.performLogout()
    assertTrue("User should be logged out", !profile.isLoggedIn)
  }
}
