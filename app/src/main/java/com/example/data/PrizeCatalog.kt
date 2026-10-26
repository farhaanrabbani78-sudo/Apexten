package com.example.data

import com.example.model.PrizeType
import com.example.model.StudyPrize
import com.example.model.Subject

object PrizeCatalog {
  val defaultPrizes: List<StudyPrize> = listOf(
    StudyPrize(
      id = "prize_trig_sheet",
      title = "Trigonometry Master Identity Sheet",
      subject = Subject.MATHEMATICS,
      type = PrizeType.FORMULA_SHEET,
      sparkCost = 50,
      isUnlocked = true, // Free starter unlock so the student immediately explores a prize!
      description = "Comprehensive Class 10 trigonometric formulas, standard value table (0°-90°), and proof shortcuts.",
      keyConcepts = listOf(
        "Pythagorean Identities: sin²θ + cos²θ = 1, 1 + tan²θ = sec²θ, 1 + cot²θ = cosec²θ",
        "Standard Table: sin 0°=0, 30°=1/2, 45°=1/√2, 60°=√3/2, 90°=1",
        "Reciprocal Relations: tan θ = sin θ / cos θ, cot θ = cos θ / sin θ",
        "Heights & Distances: tan θ = Opposite / Adjacent (Observer elevation & depression angles)"
      ),
      detailedStudyNotes = """
        === CLASS 10 TRIGONOMETRY MASTER NOTES ===
        
        1. THE FUNDAMENTAL IDENTITIES:
           • sin²θ + cos²θ = 1  ==>  sin²θ = 1 - cos²θ  ==>  cos²θ = 1 - sin²θ
           • 1 + tan²θ = sec²θ  ==>  sec²θ - tan²θ = 1  ==>  (sec θ - tan θ)(sec θ + tan θ) = 1
           • 1 + cot²θ = cosec²θ ==> cosec²θ - cot²θ = 1 ==> (cosec θ - cot θ)(cosec θ + cot θ) = 1
        
        2. SPECIAL VALUES RECALL TRICK:
           Angles:    0°       30°       45°       60°       90°
           Pattern:  √(0/4)   √(1/4)    √(2/4)    √(3/4)    √(4/4)
           sin:        0        1/2      1/√2      √3/2        1
           cos:        1       √3/2      1/√2       1/2        0
           tan:        0       1/√3        1        √3     Not Defined
        
        3. TOP BOARD EXAM PROVING STRATEGY:
           • Convert all sec θ, cosec θ, tan θ, cot θ into basic sin θ and cos θ first.
           • Rationalize denominators containing (1 ± sin θ) or (1 ± cos θ).
           • Look out for a² - b² = (a - b)(a + b) factoring opportunities.
      """.trimIndent()
    ),
    StudyPrize(
      id = "prize_chem_reactions",
      title = "Chemical Reactions & Reactivity Mind Map",
      subject = Subject.SCIENCE,
      type = PrizeType.MIND_MAP,
      sparkCost = 80,
      isUnlocked = false,
      description = "Visual chemical reaction classifications, color changes of precipitates, and reactivity mnemonic.",
      keyConcepts = listOf(
        "Mnemonic for Reactivity: Please Stop Calling Me A Careless Zebra Instead Try Learning How Copper Saves Gold (K > Na > Ca > Mg > Al > Zn > Fe > Pb > H > Cu > Ag > Au)",
        "Precipitate Colors: PbI₂ (Bright Yellow), Fe(OH)₂ (Dirty Green), Cu(OH)₂ (Pale Blue)",
        "Redox Definitions: Oxidation is Gain of Oxygen / Loss of H⁺; Reduction is Loss of Oxygen / Gain of H⁺",
        "Corrosion Prevention: Galvanisation (Zinc coating), Electroplating, Alloying (Stainless Steel)"
      ),
      detailedStudyNotes = """
        === CLASS 10 CHEMISTRY REVISION MAP ===
        
        1. TYPES OF CHEMICAL REACTIONS:
           • Combination: A + B -> AB (e.g. CaO + H₂O -> Ca(OH)₂ + Heat [Slaking of lime])
           • Decomposition: AB -> A + B (Thermal: CaCO₃ -> CaO + CO₂; Photolytic: 2AgCl -> 2Ag + Cl₂)
           • Displacement: More reactive metal replaces less reactive metal (Fe + CuSO₄ -> FeSO₄ + Cu)
           • Double Displacement: BaCl₂ + Na₂SO₄ -> BaSO₄ (white ppt) + 2NaCl
        
        2. IMPORTANT COLORS & GAS TESTS:
           • NO₂ gas: Dense brown fumes upon heating Lead Nitrate Pb(NO₃)₂
           • SO₂ gas: Burning sulfur smell upon heating Ferrous Sulfate FeSO₄ crystals
           • CO₂ gas: Turns lime water milky due to CaCO₃ precipitate
           • H₂ gas: Burns with a characteristic 'pop' sound near flame
      """.trimIndent()
    ),
    StudyPrize(
      id = "prize_life_processes",
      title = "Life Processes Ultra Summary Card",
      subject = Subject.SCIENCE,
      type = PrizeType.CONCEPT_CARDS,
      sparkCost = 100,
      isUnlocked = false,
      description = "Step-by-step mechanisms of Human Heart Double Circulation, Nephron Filtration, and Cellular Respiration.",
      keyConcepts = listOf(
        "Respiration Pathways: Glucose (6C) -> Pyruvate (3C) in Cytoplasm. Mitochondria = Aerobic (36 ATP); Yeast = Ethanol+CO₂; Muscles = Lactic Acid (Cramps)",
        "Nephron Journey: Bowman's Capsule (Glomerular filtration) -> Tubular Reabsorption (Glucose, amino acids, salts, water) -> Collecting Duct",
        "Heart Valves: Tricuspid & Bicuspid prevent backflow between atria and ventricles",
        "Plant Transport: Xylem (unidirectional, water & minerals via transpiration pull), Phloem (bidirectional, sucrose via ATP)"
      ),
      detailedStudyNotes = """
        === BIOLOGY HIGH-YIELD CHEAT SHEET ===
        
        1. DOUBLE CIRCULATION CYCLE:
           Lungs (Oxygenated blood)
               └──> Pulmonary Vein ──> Left Atrium ──> Left Ventricle
               └──> Aorta ──> Body Tissues (Cells consume O₂)
               └──> Vena Cava ──> Right Atrium ──> Right Ventricle
               └──> Pulmonary Artery ──> Back to Lungs for deoxygenation
        
        2. ENZYME ACTION MATRIX:
           • Salivary Amylase (Mouth): Starch ──> Maltose (mildly alkaline pH 6.8)
           • Pepsin (Stomach): Proteins ──> Peptones (requires acidic HCl medium)
           • Trypsin (Pancreas): Proteins ──> Peptides (requires alkaline bile medium)
           • Lipase (Pancreas): Emulsified fats ──> Fatty acids + Glycerol
      """.trimIndent()
    ),
    StudyPrize(
      id = "prize_history_timeline",
      title = "Indian Freedom Struggle Chronology",
      subject = Subject.SOCIAL_SCIENCE,
      type = PrizeType.MIND_MAP,
      sparkCost = 120,
      isUnlocked = false,
      description = "Chronological timeline from 1915 to 1942 covering key Gandhi movements, congress sessions, and acts.",
      keyConcepts = listOf(
        "1915: Gandhi returns from South Africa; Champaran (1917), Kheda (1917), Ahmedabad Mill (1918)",
        "1919: Rowlatt Act & Jallianwala Bagh Massacre (13th April, General Dyer)",
        "1920-1922: Non-Cooperation Movement launched (withdrawn after Chauri Chaura incident)",
        "1930: Dandi Salt March (12 March - 6 April); Civil Disobedience Movement initiated"
      ),
      detailedStudyNotes = """
        === NATIONALISM IN INDIA: CRITICAL TIMELINE ===
        
        • Dec 1920 (Nagpur Congress): Non-Cooperation program adopted.
        • 1928: Simon Commission arrives in India ('Simon Go Back' protests led by Lala Lajpat Rai).
        • Dec 1929 (Lahore Congress): Jawaharlal Nehru declares 'Purna Swaraj' (Complete Independence).
        • 1930: Dandi March: 240 miles from Sabarmati Ashram to coastal town of Dandi.
        • 1931: Gandhi-Irwin Pact signed; Gandhi attends Second Round Table Conference in London.
        • 1932: Poona Pact between Mahatma Gandhi & Dr. B.R. Ambedkar (reserved seats for Depressed Classes).
        • 1942: Quit India Movement ('Do or Die' call).
      """.trimIndent()
    ),
    StudyPrize(
      id = "prize_grammar_rules",
      title = "English Grammar Precision Matrix",
      subject = Subject.ENGLISH,
      type = PrizeType.EXAM_CHEATSHEET,
      sparkCost = 90,
      isUnlocked = false,
      description = "Zero-error rules for Subject-Verb Concord, Tense Shifting in Indirect Speech, and Modals nuances.",
      keyConcepts = listOf(
        "Proximity Rule: In 'Either... or' and 'Neither... nor', verb matches nearest subject",
        "Collective Nouns: Singular when acting as a single unit ('The committee has reached a decision')",
        "Reported Speech Exceptions: Universal truths & historical facts DO NOT shift tense ('The Earth moves around the sun')",
        "Modals: Must = legal/urgent necessity; Should = recommendation; Ought to = moral duty"
      ),
      detailedStudyNotes = """
        === CLASS 10 GRAMMAR MASTERY REFERENCE ===
        
        1. HIGH-FREQUENCY SUBJECT-VERB AGREEMENT TRAPS:
           • 'Each of', 'Neither of', 'Every one of' ALWAYS takes a SINGULAR verb.
             (Example: 'Each of the participants was given a certificate' - NOT were!)
           • Words joined by 'as well as', 'with', 'together with' agree with the FIRST subject.
             (Example: 'The captain, as well as the players, is celebrating.')
        
        2. REPORTED SPEECH TRANSFORMATION CHEAT:
           Direct Speech            -->  Reported / Indirect Speech
           Simple Present (writes)  -->  Simple Past (wrote)
           Present Perfect (has)    -->  Past Perfect (had)
           Simple Past (wrote)      -->  Past Perfect (had written)
           will / shall             -->  would
           now / today              -->  then / that day
           yesterday                -->  the previous day
      """.trimIndent()
    ),
    StudyPrize(
      id = "prize_optics_ray_guide",
      title = "Light Optics & Ray Diagram Compass",
      subject = Subject.SCIENCE,
      type = PrizeType.FORMULA_SHEET,
      sparkCost = 140,
      isUnlocked = false,
      description = "New Cartesian Sign Convention compass, Lens & Mirror formulas, and power calculation rules.",
      keyConcepts = listOf(
        "Sign Convention: Distances measured in direction of incident light are (+), opposite are (-)",
        "Focal Length: Concave Mirror/Lens focal length is always (-); Convex is always (+)",
        "Mirror Formula: 1/f = 1/v + 1/u ; Magnification m = -v/u = h'/h",
        "Lens Formula: 1/f = 1/v - 1/u ; Magnification m = +v/u = h'/h",
        "Power of Lens: P = 1 / f (in meters), unit is Dioptre (D)"
      ),
      detailedStudyNotes = """
        === LIGHT: RAY DIAGRAMS & SIGN CONVENTIONS ===
        
        1. CONCAVE MIRROR OBJECT PLACEMENT CHEAT SHEET:
           Position of Object    Position of Image    Size of Image       Nature
           • At Infinity         At Focus F           Point sized         Real & Inverted
           • Beyond C            Between F and C      Diminished          Real & Inverted
           • At C                At C                 Same size           Real & Inverted
           • Between C and F     Beyond C             Enlarged            Real & Inverted
           • Between P and F     Behind mirror        Enlarged            VIRTUAL & ERECT! (Dentist mirror)
        
        2. POWER OF LENSES CALCULATION TRICK:
           • If focal length f = +50 cm = +0.5 m ==> Power P = +1 / 0.5 = +2.0 D (Convex lens for Hypermetropia)
           • If focal length f = -25 cm = -0.25 m ==> Power P = -1 / 0.25 = -4.0 D (Concave lens for Myopia)
      """.trimIndent()
    )
  )
}
