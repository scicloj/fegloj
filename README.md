# Fegloj

A lightweight, batteries-included Clojure notebook environment combining [clooj](https://github.com/kloimhardt/clooj) (a simple Clojure IDE) with [Clay](https://scicloj.github.io/clay/) (literate programming for Clojure) and [Noj](https://scicloj.github.io/noj/) (a comprehensive data science library).

## Rationale

Fegloj provides an approachable, desktop-based environment for exploratory data analysis and literate programming in Clojure.
It combines:

- **clooj's simplicity**: A lightweight Swing-based editor with syntax highlighting and project navigation
- **Clay's power**: Rich notebook capabilities for mixing code, visualizations, and narrative
- **Noj's batteries**: Pre-loaded data science stack including Tablecloth, Fastmath, Scicloj.ml, and more
- **Zero-config setup**: Launches with a ready-to-use default notebook

**FEGL not REPL**: Fegloj embraces a **File-Eval-GUI-Loop** workflow instead of the traditional REPL.
Save your file (Cmd+S / Ctrl+S), and Clay automatically re-evaluates it, displaying rich visualizations in your browser.
Think of it like [Figwheel](https://figwheel.org/) for data science notebooks.
This encourages literate programming and makes notebooks the primary development artifact.

Perfect for data exploration, teaching, or quick prototyping without the complexity of larger IDE setups.

## Requirements

- Java 11 or later ([install Java](https://clojure.org/guides/install_clojure#java) if needed)
- A web browser (for viewing Clay outputs)

## Quickstart

1. Download the latest `fegloj-*-standalone.jar` from [Releases](https://github.com/YOUR_USERNAME/fegloj/releases)
2. Run it:
   ```bash
   java -jar fegloj-*-standalone.jar
   ```
3. Edit the pre-loaded `notebooks/my_notebook.clj` in the clooj editor
4. Save the file (Cmd+S / Ctrl+S) and watch your browser update with rendered output

## Usage

On startup, fegloj:
- Opens a GUI editor with project navigation
- Creates a `notebooks/` directory with a default `my_notebook.clj`
- Loads the notebook in the editor
- Activates Clay's live-reload mode

**The FEGL Workflow**:
1. Edit your notebook in the clooj editor
2. Save the file (Cmd+S / Ctrl+S)
3. Clay automatically re-evaluates and displays results in your browser
4. Iterate!

This gives you instant feedback with rich visualizations, plots, tables, and formatted output.

## Key Features

- **File-Eval-GUI-Loop**: Save-driven workflow instead of manual REPL evaluation
- **Live Reload**: Clay watches for changes and updates visualizations automatically
- **Data Science Ready**: Noj library pre-loaded with tools for data manipulation, ML, and visualization
- **Integrated Editor**: Syntax highlighting, bracket matching, navigation
- **Project Management**: Browse and edit files in the tree view

## Building from Source

If you want to build fegloj yourself:

```bash
git clone https://github.com/YOUR_USERNAME/fegloj.git
cd fegloj
clj -T:build uber
java -jar target/fegloj-*-standalone.jar
```

Requires Clojure CLI tools.

## Learning Resources

- [Clay Documentation](https://scicloj.github.io/clay/) - Learn about literate programming and visualization
- [Noj Guide](https://scicloj.github.io/noj/) - Explore the data science toolkit
- [Clojure for Data Science](https://github.com/scicloj/clojure-data-scrapbook) - Tutorials and examples

## License

See individual component licenses:
- clooj: Eclipse Public License 1.0
- Clay: EPL-2.0
- Noj: EPL-2.0

Enjoy exploring data with Clojure! 🎨
