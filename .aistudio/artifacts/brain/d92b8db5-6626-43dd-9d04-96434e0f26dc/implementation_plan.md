# ApexTen - Class 10 Diagnostic Prep & Mastery

A high-impact diagnostic testing and mastery app tailored for Class 10 students, featuring 10-20 minute timed tests across core subjects, in-depth performance analytics, personalized AI study recommendations, unlockable educational prizes, and motivation-boosting leaderboards.

---

### User Review & Critical Decisions

> [!IMPORTANT]
> The following product design choices have been aligned based on the recommended specifications:

- **Curriculum & Subjects**: Core CBSE Class 10 syllabus covering **Mathematics, Science (Physics, Chemistry, Biology), Social Science (History, Civics, Geography, Economics), and English Language & Literature**.
- **Test Timers**: Flexible 10-minute (10 questions, quick sprint) and 20-minute (20 questions, deep assessment) modes simulating real board exam formats with countdown timer and instant answer validation.
- **Diagnostics & Personalized AI Recommendations**: Deep performance breakdown by chapter and cognitive skill (conceptual clarity, formula recall, calculation speed), paired with an AI-powered personalized study advisor (using Gemini 3.5 Flash) with fallback rule-based study schedules and focus topics.
- **Prizes & Unlocks for Better Understanding**: Students earn "Mastery Sparks" (XP & Gems) through diagnostic tests. These can be redeemed to unlock high-yield study aids:
  - High-yield **Formula Cheat Sheets** (e.g., Trigonometry, Quadratic Equations, Electricity & Light).
  - Quick-revision **Mind Maps** (e.g., Chemical Reactions, Life Processes, Rise of Nationalism in Europe).
  - High-frequency **Exam Tip Cards** & **Solved Board Exemplar Problems**.
- **Interactive Leaderboard**: Weekly and all-time student rankings, streak tracker, top performers podium, and percentile comparisons to foster continuous healthy motivation.

---

### 1. Overview & Core Concept

- **What It Does**: ApexTen empowers Class 10 students to test their syllabus preparation in short 10 to 20-minute timed sessions. Rather than merely giving a score, it breaks down weak topics, explains common misconceptions, provides tailored revision plans, awards study prizes that aid conceptual understanding, and ranks students on live class leaderboards.
- **Target Audience / Persona**: Class 10 students (CBSE/State boards, ages 14-16) preparing for board examinations and unit tests who need fast, focused practice and clear direction on where to spend their revision time.
- **Key Value**: Closes the loop from **testing** ➔ **weakness diagnosis** ➔ **targeted study resources** ➔ **motivation through rewards & rank**.

---

### 2. User Experience & Visual Design

#### Key User Flows
1. **Home / Dashboard**:
   - Welcome banner displaying current study streak, Total Sparks earned, and current Class Rank.
   - Quick Start 10-Min Sprint or 20-Min Board Mock test selector.
   - Subject picker chips (Maths, Science, Social Science, English) with mastery completion bars.
   - Recommended focus topic based on past diagnostic weakness.
2. **Active Test Session (10-20 Min Timer)**:
   - Clean distraction-free testing interface with live countdown timer ring.
   - Question navigator palette (answered, flagged for review, unvisited).
   - Rich questions with clear mathematical notation, chemical equations, and scenario-based multiple choices.
   - Auto-submit on time expiry with graceful review state.
3. **Diagnostic Analysis & Performance Metrics**:
   - Score summary (Accuracy %, Speed per question, Total score).
   - Subject & Chapter breakdown with color-coded mastery pills: *Strong*, *Needs Review*, *Critical Gap*.
   - Cognitive analysis: Conceptual Accuracy vs Calculation Accuracy vs Time Management.
   - **Personalized AI Study Plan**: Actionable 3-step revision protocol generated specifically for the student's missed concepts.
4. **Prizes & Knowledge Vault**:
   - Unlocked and unlockable learning rewards: Formula Cheat Sheets, Illustrated Science Mind Maps, Grammar Rule Cards, and History Chronology Timelines.
   - Interactive viewer for unlocked study aids so students can immediately study what they missed.
   - Achievement badges (e.g., "Algebra Ace", "Speed Demon", "Comeback Kid", "7-Day Streak").
5. **Interactive Leaderboard**:
   - Top 3 Podium showcase with animated ranks, avatar badges, and streak markers.
   - Filter by: This Week's Champions, All-Time Legends, and Subject Specialists.
   - "Your Rank" sticky bottom bar showing exact percentile and points needed to reach the next tier (Bronze, Silver, Gold, Diamond, Apex).

#### Visual Identity & Theme
- **Aesthetic Direction**: *Neo-Academic Scholar* — vibrant, inspiring, and focused. Combining the energetic motivation of modern fitness/learning apps with clean, trustworthy educational ergonomics.
- **Color Palette**:
  - `Primary`: Deep Indigo (`#2563EB` / `#3B82F6`) symbolizing focus and intellect.
  - `Secondary / Accent`: Electric Amber & Golden Sun (`#F59E0B` / `#FBBF24`) for sparks, rewards, and podium badges.
  - `Tertiary`: Mint Emerald (`#10B981`) for mastery, correct answers, and high scores.
  - `Error / Gap`: Coral Red (`#EF4444`) for critical gaps needing revision.
  - `Background`: Soft Off-White (`#F8FAFC`) in Light Mode; Slate Dark (`#0F172A`) with deep navy surfaces in Dark Mode.
- **Typography**:
  - Modern geometric sans-serif hierarchy with bold tabular figures for timer countdowns and clear heading distinction.
- **Component Styling & Layout**:
  - Rounded M3 cards (16dp-20dp radius) with subtle tonal elevations.
  - Pill badges for subject filters and question navigation dots.
  - Smooth animated progress bars and interactive question selection states.
  - Full edge-to-edge support with safe drawing insets.

---

### 3. Key Product Decisions & Trade-Offs

- **Decision 1: Curated Class 10 Question Bank with Rich Diagnostic Metadata**:
  - *Chosen Approach*: Comprehensive local Room database seeded with curated CBSE Class 10 questions across Mathematics, Science, Social Science, and English, tagged by subject, chapter, difficulty, and cognitive skill.
  - *Why*: Allows instantaneous test startup with zero loading latency or network dependency, while enabling granular diagnostic breakdown.
  - *Alternatives Considered*: Fetching questions dynamically via cloud; discarded to ensure instant test reliability and smooth offline test taking without timer interruptions.

- **Decision 2: Dual AI & Algorithmic Recommendation Engine**:
  - *Chosen Approach*: Hybrid architecture where detailed metrics (accuracy, time spent, chapter error rates) are analyzed algorithmically for immediate display, and enhanced with Gemini 3.5 Flash when an API key is available to provide human-like personalized encouragement, custom mnemonics, and study tips.
  - *Why*: Ensures the app is 100% functional, responsive, and insightful even offline or without an API key, while delivering high-tier AI personalization when connected.

- **Decision 3: Educational Prizes (Knowledge Vault) vs Pure Vanity Badges**:
  - *Chosen Approach*: Prizes directly address the user's prompt ("give prizes that they use for better understanding"). Rewards are actual high-yield revision tools (Formulas, Mind Maps, Quick Guides) unlocked via test points, alongside badges.
  - *Why*: Directly reinforces learning and creates a genuine incentive loop for students to take more diagnostic tests.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                              ApexTen App                               │
├────────────────────────────────────────────────────────────────────────┤
│                           Jetpack Compose UI                           │
│  ┌──────────────┐ ┌──────────────┐ ┌───────────────┐ ┌──────────────┐ │
│  │  Dashboard   │ │ Active Test  │ │  Diagnostics  │ │   Prizes &   │ │
│  │  & Subjects  │ │  (10-20 min) │ │ & AI Advisor  │ │ Leaderboard  │ │
│  └──────┬───────┘ └──────┬───────┘ └───────┬───────┘ └──────┬───────┘ │
├─────────┼────────────────┼─────────────────┼────────────────┼─────────┤
│         ▼                ▼                 ▼                ▼         │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                      ApexViewModel & State                       │  │
│  │   - ActiveTestState (timer, responses, flagged, progress)        │  │
│  │   - DiagnosticMetricsState (accuracy, chapter gaps, speed)       │  │
│  │   - StudentProfileState (Sparks, unlocked prizes, streak)        │  │
│  │   - LeaderboardState (rankings, tiers, comparisons)              │  │
│  └───────────────────────────────┬──────────────────────────────────┘  │
├──────────────────────────────────┼─────────────────────────────────────┤
│                                  ▼                                     │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                         Repository Layer                         │  │
│  │   - TestRepository (seed questions, generate 10/20m tests)       │  │
│  │   - AnalyticsRepository (compute cognitive & chapter metrics)    │  │
│  │   - PrizeRepository (catalog of formulas/maps, unlocks)          │  │
│  │   - GeminiAdvisorService (personalized AI study recommendations) │  │
│  └───────────────────────────────┬──────────────────────────────────┘  │
├──────────────────────────────────┼─────────────────────────────────────┤
│                                  ▼                                     │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                 Local Persistence (Room & DataStore)             │  │
│  │   - QuestionsTable, TestResultsTable, UnlockedPrizesTable        │  │
│  │   - StudentPreferences (streak, name, total sparks)              │  │
│  └──────────────────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────────────────┘
```

#### Data Entities & Models:
1. `Question`: ID, subject, chapter, questionText, options (A-D), correctOption, explanation, difficulty, cognitiveSkill (Conceptual, Formula, Application, Memory).
2. `TestSession`: Session ID, timestamp, durationMinutes (10 or 20), subject, totalQuestions, userResponses, score, timeSpentSeconds.
3. `DiagnosticReport`: Overall score, accuracyPercentage, speedPace, strongChapters, weakChapters, recommendedActions, aiStudyAdvice.
4. `StudyPrize`: ID, title, subject, category (FormulaSheet, MindMap, ExamTip), description, sparkCost, isUnlocked, contentDetails.
5. `LeaderboardEntry`: Rank, studentName, avatarSeed, scoreSparks, streakDays, titleTier.

---

### Implementation Stages (To Be Executed After Approval)
1. **Domain Models & Seed Question Bank**: Room entity setup and comprehensive Class 10 questions across Maths, Science, Social Science, and English with explanations.
2. **Repository & Diagnostic Engine**: Core logic to calculate test metrics, identify weak areas, and generate targeted study recommendations (including Gemini API integration).
3. **Prizes & Leaderboard Catalog**: Unlockable educational cheat sheets, mind maps, and interactive leaderboard with podium rankings.
4. **Jetpack Compose UI**:
   - Sleek Home Dashboard with streak and quick test launch.
   - Interactive Test Taking screen with real-time countdown timer, question palette, and instant review.
   - Comprehensive Performance Diagnostic & AI Recommendation Report.
   - Prize Knowledge Vault viewer and Leaderboard screens.
5. **Polishing & Verification**: App icons, test compilation, and Robolectric CUJ unit tests.
