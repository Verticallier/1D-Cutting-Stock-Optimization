# 🧩 1D Cutting Stock Optimization

This repository presents an implementation of solution approaches for the  
**1-Dimensional Cutting Stock Problem (1D-CSP)** using **greedy heuristics** and
**genetic algorithms**.

The project is developed on top of a reusable **metaheuristic optimization framework**
designed by **Assist. Prof. (PhD) Dindar Öz**.  
All **problem-specific modeling, objective functions, heuristics, and genetic algorithm
operators** were implemented **by the author** as part of structured coursework
assignments.

---

## 📌 What Is the 1D Cutting Stock Problem?

The **1-Dimensional Cutting Stock Problem (1D-CSP)** is a classical optimization problem
commonly encountered in manufacturing and production planning.

The problem can be summarized as follows:

- 📏 Stock materials of a fixed length are available (e.g., metal bars, wooden beams).
- ✂️ A set of smaller pieces with predefined lengths and quantities must be cut from these stocks.
- 🧾 Each cutting operation produces **waste (unused material)**.
- 🎯 The objective is to **minimize the total waste** while satisfying all piece demands.

Due to the rapid growth of possible cutting combinations, the 1D-CSP is classified as
an **NP-hard** problem. Therefore, exact methods are often impractical, and
**heuristic and metaheuristic approaches** are preferred in real-world scenarios.

---

## 🧠 Project Development Approach (Three-Step Solution Strategy)

This project addresses the 1D Cutting Stock Problem incrementally in **three main stages**.
Each stage builds upon the previous one to improve solution quality and robustness.

### 🔹 Step 1 – Greedy Baseline (HW1)
- Implementation of a deterministic greedy heuristic.
- Provides a fast and feasible baseline solution.
- Serves as a reference point for evaluating more advanced methods.

### 🔹 Step 2 – Standard Genetic Algorithm (HW2)
- Introduction of a classical Genetic Algorithm (GA).
- Uses population-based search with crossover and mutation operators.
- Achieves better solutions than the greedy baseline.

### 🔹 Step 3 – Adaptive / Improved Genetic Algorithm (HW3)
- Enhances the standard GA with adaptive mechanisms.
- Detects stagnation in solution improvement.
- Dynamically adjusts mutation rates to escape local optima and improve convergence.

---

## 🏗️ Framework and Contribution Breakdown

### Framework (Provided)
The framework, designed by **Assist. Prof. (PhD) Dindar Öz**, supplies
generic and problem-independent components, including:
- Metaheuristic abstractions
- Solution representations
- A reusable Genetic Algorithm skeleton
- Algorithm-independent interfaces

### Author Contributions
All **Cutting Stock Problem–specific logic** was developed by the author, including:
- Formal problem modeling
- Feasibility checking
- Objective function design
- Greedy heuristic implementation
- Genetic algorithm operators
- Adaptive and improved GA strategies

---

## ⚙️ Implemented Components

The following components specialize the framework for solving the Cutting Stock Problem.

---

## A. Problem Modeling (CSP-Specific Components)

### `CSPModel`
Defines the structure of the Cutting Stock Problem.

**Responsibilities:**
- Stores problem data:
  - Stock length
  - Piece sizes
  - Piece quantities
- Validates solution feasibility:
  - `isFeasible(...)` ensures stock length constraints are not violated.

---

### `CSPMinimumWasteOF` (Objective Function)
Defines the optimization objective.

**Responsibilities:**
- Computes the total waste produced by a solution.
- Applies a **First Fit placement strategy**:
  - Pieces are placed sequentially.
  - A new stock is opened when the current stock cannot fit the next piece.

---

## B. Algorithms and Operators

This section corresponds to the main workload of **HW1, HW2, and HW3**.

---

## 🔹 HW1 – Greedy Heuristic

### `CSPGreedyHeuristic`
A deterministic baseline algorithm.

**Key characteristics:**
- Sorts pieces from largest to smallest.
- Places pieces using a **Best Fit Decreasing–style strategy**.
- Very fast, but does not guarantee optimal solutions.

---

## 🔹 HW2 – Standard Genetic Algorithm

### Initial Solution Generator

#### `CSPRandomISG`
- Generates the initial population using random permutations of pieces.
- Ensures diversity at the beginning of the evolutionary process.

---

### Genetic Operators

#### `CSPRandomCrossOver`
- Produces offspring by combining two parent solutions.
- Preserves feasibility while increasing diversity.

#### `CSPRandomSwapMutation`
- Randomly swaps two pieces within a solution.
- Helps prevent premature convergence.

> Although `GA.java` is provided as a generic skeleton by the framework,
> the orchestration of selection, crossover, and mutation was configured
> and adapted within this project.

---

## 🚀 HW3 – Adaptive / Improved Genetic Algorithm (Innovation Component)

### `AdaptiveGA`
An enhanced version of the standard genetic algorithm.

**Enhancements:**
- Implements stagnation detection to monitor optimization progress.
- Applies **online parameter control**:
  - Mutation rate = `0.2` during normal improvement
  - Mutation rate = `0.6` during stagnation phases

---

### `CSPSmartISG`
A smart initial solution generator.

**Techniques used:**
- Inspired by **GRASP (Greedy Randomized Adaptive Search Procedure)**
- Utilizes a **Restricted Candidate List (RCL)**

**Benefits:**
- Produces higher-quality initial solutions than pure random generation.
- Maintains population diversity.
- Improves convergence speed and final solution quality.

---

## 📂 Project Structure and Experimental Results

All problem instances, algorithm outputs, and experimental results generated during
the execution of the algorithms are located in the **`data/`** directory.

The `data/` folder contains:
- Input problem definitions
- Output solutions produced by different algorithms
- Experimental results used for performance evaluation and comparison

---
## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK):** Version 17 or higher.
- **IDE:** IntelliJ IDEA (Recommended) or Eclipse.

### How to Run
The project contains multiple experiment runners located in `src/experiments/CSP/`.

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Verticallier/1D-Cutting-Stock-Optimization.git](https://github.com/Verticallier/1D-Cutting-Stock-Optimization.git)
   ```
2. **Open in IntelliJ IDEA.**

3. **Navigate to the experiment file:**
   - For the final comparison (HW3): src/experiments/CSP/CSPComparisonMainHW3.java
   - For parameter tuning: src/experiments/CSP/CSPParameterTuningMain.java

4. **Run the main method.**
   - The results will be generated in the data/output/ directory (e.g., CSP_HW3_Adaptive_Final.txt).

---

### 2. 🏆 Key Results (Önemli Sonuçlar - Opsiyonel ama Etkileyici)
"Results are in the folder" demek yerine, elde ettiğin o muazzam farkı (Greedy vs Adaptive) küçük bir tablo ile burada gösterirsen projenin etkisi artar. (Verileri analiz ettiğimiz dosyalardan aldım).

**Bunu "Experimental Results" başlığı altına ekleyebilirsin:**

```markdown
### 🏆 Performance Highlights
The Adaptive GA significantly outperforms the Greedy heuristic, especially in complex instances. Below is a sample comparison from **Instance 6**:

| Algorithm | Best Solution (Total Waste) | Improvement vs. Greedy |
|-----------|-----------------------------|------------------------|
| **Greedy Heuristic** | 629.00 | - |
| **Standard GA** | 77.50 | 87.6% |
| **Adaptive GA (Proposed)** | **66.55** | **89.4%** |

*Note: Lower waste indicates better performance.*
```


## 📊 Component Summary

| Component | Source | Description |
|--------|--------|------------|
| Metaheuristic, Solution, GA Interfaces | Framework | Generic optimization infrastructure |
| CSPModel, CSPMinimumWasteOF | Author | CSP modeling and objective function |
| CSPGreedyHeuristic | Author (HW1) | Fast greedy baseline |
| Standard GA (CSPRandom\*) | Author (HW2) | Classical genetic algorithm |
| AdaptiveGA, CSPSmartISG | Author (HW3) | Adaptive and improved GA |

---

## 📝 Design Notes

- This project was developed for academic purposes.
- The primary focus is on:
  - Algorithmic design
  - Metaheuristic optimization
  - Clean separation of concerns
- UI and visualization are intentionally excluded.
- Problem-specific logic is strictly separated from the framework
  to ensure modularity, extensibility, and reusability.

---

## 🛠️ Technologies
- **Language:** Java 17
- **Concept:** Metaheuristic Optimization, Genetic Algorithms, GRASP
- **IDE:** IntelliJ IDEA

---
