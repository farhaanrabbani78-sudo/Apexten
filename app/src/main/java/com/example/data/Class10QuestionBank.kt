package com.example.data

import com.example.model.CognitiveSkill
import com.example.model.Question
import com.example.model.Subject

object Class10QuestionBank {
  val questions: List<Question> = listOf(
    // MATHEMATICS
    Question(
      id = "m1",
      subject = Subject.MATHEMATICS,
      chapter = "Quadratic Equations",
      questionText = "For the quadratic equation 2x² - 4x + 3 = 0, what is the nature of its roots?",
      options = listOf("Real and equal", "Real and distinct", "No real roots (imaginary)", "Zero roots"),
      correctIndex = 2,
      explanation = "Discriminant D = b² - 4ac = (-4)² - 4(2)(3) = 16 - 24 = -8. Since D < 0, the equation has no real roots.",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Medium"
    ),
    Question(
      id = "m2",
      subject = Subject.MATHEMATICS,
      chapter = "Trigonometry",
      questionText = "If sin θ + cos θ = √2 cos θ, then what is the value of (cos θ - sin θ)?",
      options = listOf("√2 sin θ", "√2 cos θ", "1 / √2", "sin θ"),
      correctIndex = 0,
      explanation = "Squaring both sides and applying identity (sin θ + cos θ)² + (cos θ - sin θ)² = 2(sin²θ + cos²θ) = 2. So 2 cos²θ + (cos θ - sin θ)² = 2 -> (cos θ - sin θ)² = 2(1 - cos²θ) = 2 sin²θ. Hence cos θ - sin θ = √2 sin θ.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Hard"
    ),
    Question(
      id = "m3",
      subject = Subject.MATHEMATICS,
      chapter = "Arithmetic Progressions",
      questionText = "In an AP, if a = 3.5, d = 0, and n = 101, what is the value of the 101st term (a₁₀₁)?",
      options = listOf("0", "3.5", "104.5", "350"),
      correctIndex = 1,
      explanation = "Since common difference d = 0, every term of the AP is identical to the first term 'a'. So a₁₀₁ = 3.5 + (101 - 1)(0) = 3.5.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "m4",
      subject = Subject.MATHEMATICS,
      chapter = "Triangles",
      questionText = "If ∆ABC ~ ∆PQR, with BC/QR = 1/3, what is the ratio of Area(∆PQR) to Area(∆ABC)?",
      options = listOf("1 : 3", "3 : 1", "9 : 1", "1 : 9"),
      correctIndex = 2,
      explanation = "The ratio of areas of two similar triangles is equal to the square of the ratio of their corresponding sides. Area(∆PQR) / Area(∆ABC) = (QR/BC)² = (3/1)² = 9/1.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Medium"
    ),
    Question(
      id = "m5",
      subject = Subject.MATHEMATICS,
      chapter = "Statistics",
      questionText = "Which empirical relationship correctly links Mode, Median, and Mean for a moderately skewed distribution?",
      options = listOf(
        "Mode = 3 Median - 2 Mean",
        "Mode = 2 Median - 3 Mean",
        "3 Mode = Median + 2 Mean",
        "Mode = 3 Mean - 2 Median"
      ),
      correctIndex = 0,
      explanation = "The standard empirical formula taught in Class 10 is: Mode = 3 Median - 2 Mean.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "m6",
      subject = Subject.MATHEMATICS,
      chapter = "Coordinate Geometry",
      questionText = "The point P which divides the line segment joining A(-1, 7) and B(4, -3) in the ratio 2 : 3 internally has coordinates:",
      options = listOf("(1, 3)", "(2, 1)", "(1, -3)", "(3, 1)"),
      correctIndex = 0,
      explanation = "By section formula: x = (2(4) + 3(-1)) / (2+3) = (8 - 3)/5 = 1. y = (2(-3) + 3(7)) / (2+3) = (-6 + 21)/5 = 15/5 = 3. Point is (1, 3).",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Medium"
    ),
    Question(
      id = "m7",
      subject = Subject.MATHEMATICS,
      chapter = "Real Numbers",
      questionText = "If HCF(a, b) = 12 and a × b = 1800, what is the LCM(a, b)?",
      options = listOf("150", "900", "120", "180"),
      correctIndex = 0,
      explanation = "HCF × LCM = Product of two numbers. Therefore, LCM = (a × b) / HCF = 1800 / 12 = 150.",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Easy"
    ),
    Question(
      id = "m8",
      subject = Subject.MATHEMATICS,
      chapter = "Probability",
      questionText = "A card is drawn from a well-shuffled pack of 52 cards. What is the probability of getting a black face card?",
      options = listOf("3 / 13", "3 / 26", "1 / 26", "6 / 13"),
      correctIndex = 1,
      explanation = "Total face cards = 12 (Jack, Queen, King across 4 suits). Half are black (Spades and Clubs) = 6 cards. Probability = 6/52 = 3/26.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Medium"
    ),
    Question(
      id = "m9",
      subject = Subject.MATHEMATICS,
      chapter = "Circles",
      questionText = "If tangents PA and PB from a point P to a circle with centre O are inclined to each other at an angle of 80°, then ∠POA is equal to:",
      options = listOf("50°", "60°", "70°", "80°"),
      correctIndex = 0,
      explanation = "In quadrilateral PAOB, ∠OAP = ∠OBP = 90°. Since ∠APB = 80°, ∠AOB = 180° - 80° = 100°. The line OP bisects ∠AOB, so ∠POA = 100° / 2 = 50°.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Hard"
    ),
    Question(
      id = "m10",
      subject = Subject.MATHEMATICS,
      chapter = "Polynomials",
      questionText = "If one zero of the quadratic polynomial x² + 3x + k is 2, then what is the value of k?",
      options = listOf("10", "-10", "-7", "-2"),
      correctIndex = 1,
      explanation = "Since 2 is a zero, substituting x = 2 gives 2² + 3(2) + k = 0 => 4 + 6 + k = 0 => k = -10.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),

    // SCIENCE
    Question(
      id = "s1",
      subject = Subject.SCIENCE,
      chapter = "Chemical Reactions & Equations",
      questionText = "When Lead Nitrate powder is heated in a dry boiling tube, brown fumes of which gas are evolved?",
      options = listOf("Nitric Oxide (NO)", "Nitrogen Dioxide (NO₂)", "Nitrous Oxide (N₂O)", "Nitrogen Pentoxide (N₂O₅)"),
      correctIndex = 1,
      explanation = "2Pb(NO₃)₂ --heat--> 2PbO + 4NO₂↑ (brown fumes) + O₂↑. The characteristic brown gas is Nitrogen Dioxide.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Medium"
    ),
    Question(
      id = "s2",
      subject = Subject.SCIENCE,
      chapter = "Acids, Bases & Salts",
      questionText = "What is the chemical formula for Bleaching Powder used in water purification and textile bleaching?",
      options = listOf("CaSO₄·½H₂O", "CaOCl₂", "Ca(OH)₂", "CaCl₂"),
      correctIndex = 1,
      explanation = "Bleaching powder is Calcium Oxychloride (CaOCl₂), prepared by passing chlorine gas over dry slaked lime.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "s3",
      subject = Subject.SCIENCE,
      chapter = "Life Processes",
      questionText = "During anaerobic respiration in human muscle cells during vigorous exercise, pyruvate is converted into:",
      options = listOf("Ethanol + CO₂ + Energy", "Lactic acid + Energy", "Carbon dioxide + Water + Energy", "Methanol + Energy"),
      correctIndex = 1,
      explanation = "Due to lack of oxygen in human muscle tissue, 3-carbon pyruvate is reduced into 3-carbon Lactic Acid, which causes muscle cramps.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "s4",
      subject = Subject.SCIENCE,
      chapter = "Light - Reflection & Refraction",
      questionText = "A concave mirror produces three times magnified real image of an object placed at 10 cm in front of it. What is the radius of curvature?",
      options = listOf("15 cm", "-30 cm", "-15 cm", "30 cm"),
      correctIndex = 1,
      explanation = "m = -v/u = -3 (real image has negative magnification). u = -10 cm, so v = -30 cm. 1/f = 1/v + 1/u = -1/30 - 1/10 = -4/30 => f = -7.5 cm. Radius of curvature R = 2f = -15 cm (or distance magnitude 15 cm with R = -15 cm, wait: 2 * -7.5 = -15 cm). Radius magnitude is 15 cm.",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Hard"
    ),
    Question(
      id = "s5",
      subject = Subject.SCIENCE,
      chapter = "Electricity",
      questionText = "Two resistors of 6 Ω and 3 Ω are connected in parallel. This combination is connected in series with a 4 Ω resistor. What is the total equivalent resistance?",
      options = listOf("2 Ω", "6 Ω", "9 Ω", "13 Ω"),
      correctIndex = 1,
      explanation = "Parallel part: (6 × 3) / (6 + 3) = 18 / 9 = 2 Ω. Adding series resistor: R_eq = 2 Ω + 4 Ω = 6 Ω.",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Medium"
    ),
    Question(
      id = "s6",
      subject = Subject.SCIENCE,
      chapter = "Control & Coordination",
      questionText = "Which plant hormone promotes cell division and is present in greater concentration in areas of rapid cell growth like fruits and seeds?",
      options = listOf("Auxin", "Gibberellin", "Cytokinin", "Abscisic Acid"),
      correctIndex = 2,
      explanation = "Cytokinins promote active cell division and are concentrated in developing seeds and growing fruits.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "s7",
      subject = Subject.SCIENCE,
      chapter = "Magnetic Effects of Electric Current",
      questionText = "According to Fleming's Left-Hand Rule, what does the middle finger represent?",
      options = listOf("Direction of Magnetic Field", "Direction of Induced Current", "Direction of Electric Current", "Direction of Force/Motion"),
      correctIndex = 2,
      explanation = "In Fleming's Left Hand Rule: Thumb represents Force/Motion, Forefinger represents Magnetic Field, and Middle finger represents Direction of Electric Current.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "s8",
      subject = Subject.SCIENCE,
      chapter = "Metals and Non-Metals",
      questionText = "An element reacts with oxygen to give a compound with a high melting point, which is also soluble in water. The element is likely to be:",
      options = listOf("Calcium", "Carbon", "Silicon", "Iron"),
      correctIndex = 0,
      explanation = "Calcium reacts with oxygen to form Calcium Oxide (CaO), an ionic compound with high melting point that dissolves in water to form slaked lime.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Medium"
    ),
    Question(
      id = "s9",
      subject = Subject.SCIENCE,
      chapter = "Heredity",
      questionText = "In a cross between pure tall (TT) and pure dwarf (tt) pea plants, the phenotypic ratio in the F₂ generation is:",
      options = listOf("1 : 2 : 1", "3 : 1", "9 : 3 : 3 : 1", "2 : 1"),
      correctIndex = 1,
      explanation = "The phenotypic ratio of tall to dwarf in F₂ generation of a monohybrid cross is 3 : 1 (while the genotypic ratio is 1 TT : 2 Tt : 1 tt).",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "s10",
      subject = Subject.SCIENCE,
      chapter = "Our Environment",
      questionText = "According to Lindeman's 10% law, what percentage of energy is transferred from one trophic level to the next?",
      options = listOf("1%", "10%", "50%", "90%"),
      correctIndex = 1,
      explanation = "Only about 10% of the energy consumed is turned into biomass and available for the next trophic level; 90% is lost as heat and metabolic functions.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),

    // SOCIAL SCIENCE
    Question(
      id = "sst1",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Rise of Nationalism in Europe",
      questionText = "The Civil Code of 1804 in France is universally known by which name?",
      options = listOf("Treaty of Vienna", "Napoleonic Code", "Declaration of Rights", "Monroe Doctrine"),
      correctIndex = 1,
      explanation = "The Civil Code of 1804 introduced by Napoleon abolished privileges based on birth, established equality before law, and secured property rights.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst2",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Nationalism in India",
      questionText = "Mahatma Gandhi launched the historic Salt March (Dandi March) from Sabarmati Ashram in 1930 to protest against which act?",
      options = listOf("Rowlatt Act", "Government monopoly on salt manufacturing and salt tax", "Simon Commission", "Inland Emigration Act"),
      correctIndex = 1,
      explanation = "Gandhi targeted the salt tax because salt was consumed by rich and poor alike, making it the most unifying symbol against British colonial rule.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst3",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Resources & Development",
      questionText = "Black soil, formed from basalt rock and rich in calcium carbonate, is ideal for the cultivation of which crop?",
      options = listOf("Tea", "Cotton", "Wheat", "Jute"),
      correctIndex = 1,
      explanation = "Black soil (also called Regur soil) has high moisture retention capacity and is universally known as Black Cotton Soil.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst4",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Power Sharing",
      questionText = "Which linguistic arrangement resolved community tensions in the Belgian model of power sharing?",
      options = listOf(
        "Dutch speakers were given 80% majority in all ministries",
        "Equal number of Dutch and French ministers in Central Government",
        "French language was declared the sole official language",
        "A monarchical rule replaced constitutional democracy"
      ),
      correctIndex = 1,
      explanation = "The Belgian constitution prescribed that the number of Dutch and French-speaking ministers shall be equal in the central government.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "sst5",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Money & Credit",
      questionText = "Which apex institution in India supervises the functioning of formal sources of loans and monitors bank reserves?",
      options = listOf("State Bank of India (SBI)", "Reserve Bank of India (RBI)", "NITI Aayog", "Ministry of Commerce"),
      correctIndex = 1,
      explanation = "The Reserve Bank of India (RBI) supervises the functioning of commercial banks, ensures minimum cash balance, and monitors lending practices.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst6",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Federalism",
      questionText = "Matters of national importance such as Defense, Foreign Affairs, Banking, and Currency fall under which legislative list in the Indian Constitution?",
      options = listOf("State List", "Union List", "Concurrent List", "Residuary Powers"),
      correctIndex = 1,
      explanation = "The Union List contains 100 items of national significance where only the Central Parliament has exclusive authority to legislate.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "sst7",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Sectors of Indian Economy",
      questionText = "Underemployment in agriculture where more people work on a plot than is actually needed is termed as:",
      options = listOf("Seasonal Unemployment", "Disguised Unemployment", "Frictional Unemployment", "Structural Unemployment"),
      correctIndex = 1,
      explanation = "Disguised unemployment happens when workers appear employed, but their marginal productivity is zero—removing a few causes no reduction in total output.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "sst8",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Gender, Religion and Caste",
      questionText = "A system where all work inside the home is done by family women or domestic helpers is known as:",
      options = listOf("Feminist division", "Sexual division of labour", "Patriarchal supremacy", "Occupational immobility"),
      correctIndex = 1,
      explanation = "Sexual division of labour is a societal division based on gender roles rather than biological abilities.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst9",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Globalisation & The Indian Economy",
      questionText = "Removing barriers or restrictions set by the government on foreign trade and investment is known as:",
      options = listOf("Privatisation", "Liberalisation", "Nationalisation", "Decentralisation"),
      correctIndex = 1,
      explanation = "Liberalisation refers to removing governmental regulations and tariffs to facilitate easier business and global trade.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "sst10",
      subject = Subject.SOCIAL_SCIENCE,
      chapter = "Water Resources",
      questionText = "The 'Bhakra Nangal' multipurpose river valley project is built on which major Indian river?",
      options = listOf("Mahanadi", "Satluj", "Ganga", "Narmada"),
      correctIndex = 1,
      explanation = "The Bhakra-Nangal dam is constructed on the Satluj river in northern India, providing both irrigation and hydroelectric power.",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Medium"
    ),

    // ENGLISH
    Question(
      id = "eng1",
      subject = Subject.ENGLISH,
      chapter = "A Letter to God",
      questionText = "What did Lencho call the post office employees after counting the money in the envelope?",
      options = listOf("A group of angels", "A bunch of crooks", "Selfless benefactors", "Corrupt officials"),
      correctIndex = 1,
      explanation = "In an ironic twist, Lencho firmly believed God sent the exact 100 pesos, and assumed the post office workers had stolen the missing 30 pesos.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "eng2",
      subject = Subject.ENGLISH,
      chapter = "Nelson Mandela: Long Walk to Freedom",
      questionText = "According to Nelson Mandela, what is the greatest wealth of his country?",
      options = listOf("Its rich gold and diamond deposits", "Its fertile agrarian lands", "Its people, finer and truer than pure diamonds", "Its naval ports and infrastructure"),
      correctIndex = 2,
      explanation = "Mandela asserted: 'My country is rich in the minerals and gems that lie beneath its soil, but I have always known that its greatest wealth is its people...'",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "eng3",
      subject = Subject.ENGLISH,
      chapter = "Grammar - Subject-Verb Agreement",
      questionText = "Choose the grammatically correct sentence:",
      options = listOf(
        "Neither the teacher nor the students was present in the auditorium.",
        "Neither the teacher nor the students were present in the auditorium.",
        "Neither the teacher nor the students is present in the auditorium.",
        "Neither the teacher nor the students has been present in the auditorium."
      ),
      correctIndex = 1,
      explanation = "In 'Neither... nor' constructions, the verb agrees in number with the closer subject ('the students', which is plural, hence 'were').",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Medium"
    ),
    Question(
      id = "eng4",
      subject = Subject.ENGLISH,
      chapter = "Grammar - Modals",
      questionText = "Identify the modal that conveys strong moral obligation or advice:",
      options = listOf("Can", "Might", "Ought to", "May"),
      correctIndex = 2,
      explanation = "'Ought to' expresses moral obligation, duty, or advisable ethical behavior ('We ought to respect our national heritage').",
      cognitiveSkill = CognitiveSkill.RECALL_TERMINOLOGY,
      difficulty = "Easy"
    ),
    Question(
      id = "eng5",
      subject = Subject.ENGLISH,
      chapter = "Grammar - Reported Speech",
      questionText = "Convert to indirect speech: The teacher said to Rohan, 'Why are you late today?'",
      options = listOf(
        "The teacher asked Rohan that why he is late today.",
        "The teacher asked Rohan why he was late that day.",
        "The teacher questioned Rohan why was he late that day.",
        "The teacher said to Rohan why he had been late today."
      ),
      correctIndex = 1,
      explanation = "In indirect interrogative sentences: 'said to' becomes 'asked', no 'that' conjunction is used before 'why', tense shifts from present to past ('was'), and 'today' becomes 'that day'.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Medium"
    ),
    Question(
      id = "eng6",
      subject = Subject.ENGLISH,
      chapter = "From the Diary of Anne Frank",
      questionText = "Why did Anne Frank decide to write a diary and name it 'Kitty'?",
      options = listOf(
        "Because she wanted to publish a bestselling wartime novel",
        "Because paper has more patience than people and she lacked a true intimate friend",
        "Because her school teacher Mr. Keesing ordered her to write one",
        "Because her sister Margot advised her to keep daily records"
      ),
      correctIndex = 1,
      explanation = "Anne wrote: 'Paper has more patience than people.' Even with loving parents and acquaintances, she felt she had no true friend to confide in.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Easy"
    ),
    Question(
      id = "eng7",
      subject = Subject.ENGLISH,
      chapter = "Two Stories about Flying",
      questionText = "What finally prompted the young seagull to take his first flight from the cliff?",
      options = listOf(
        "He saw an eagle attacking his nest",
        "He was maddened by hunger when his mother brought fish close to him",
        "A sudden gust of wind pushed him off the edge",
        "His father carried him on his back"
      ),
      correctIndex = 1,
      explanation = "Starving for 24 hours, the sight of food maddened him. When his mother halted just out of reach with a piece of fish, he dived at it and his wings opened naturally.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "eng8",
      subject = Subject.ENGLISH,
      chapter = "Grammar - Tenses",
      questionText = "Complete the sentence: By the end of this month, our class _____ the complete Class 10 syllabus.",
      options = listOf("will finish", "will have finished", "is finishing", "has finished"),
      correctIndex = 1,
      explanation = "Action that will be completed before a specified future milestone ('By the end of this month') takes the Future Perfect tense ('will have finished').",
      cognitiveSkill = CognitiveSkill.FORMULA_APPLICATION,
      difficulty = "Hard"
    ),
    Question(
      id = "eng9",
      subject = Subject.ENGLISH,
      chapter = "The Thief's Story",
      questionText = "Why did Hari Singh return to Anil after stealing the 600 rupees at the railway station?",
      options = listOf(
        "He missed the train and the police were chasing him",
        "He realized that without education and respect, money was temporary and meaningless",
        "He forgot his watch at Anil's room",
        "Anil caught him on the railway platform"
      ),
      correctIndex = 1,
      explanation = "Hari Singh realized Anil was teaching him to write whole sentences and become a respected, educated person, which was worth far more than stolen cash.",
      cognitiveSkill = CognitiveSkill.CONCEPTUAL_CLARITY,
      difficulty = "Medium"
    ),
    Question(
      id = "eng10",
      subject = Subject.ENGLISH,
      chapter = "Grammar - Determiners",
      questionText = "Fill in the blank: There is _____ hope of his complete recovery after the critical surgery, but doctors are doing their best.",
      options = listOf("a few", "little", "a little", "many"),
      correctIndex = 1,
      explanation = "'Little' has a negative connotation meaning 'hardly any' or 'negligible', which fits the critical situation described.",
      cognitiveSkill = CognitiveSkill.LOGICAL_REASONING,
      difficulty = "Hard"
    )
  )

  fun getQuestionsForTest(
    subjectFilter: Subject?,
    count: Int
  ): List<Question> {
    val pool = if (subjectFilter != null) {
      questions.filter { it.subject == subjectFilter }
    } else {
      questions
    }
    // Return shuffled subset or repeat if pool is smaller than count
    return pool.shuffled().take(count.coerceAtMost(pool.size))
  }
}
