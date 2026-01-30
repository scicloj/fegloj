# Fegloj

A batteries-included Clojure notebook for eval-on-save workflows.

## Rationale

Fegloj is a desktop environment for exploratory data analysis and literate programming in Clojure.
It combines [clooj](https://github.com/kloimhardt/clooj) (a simple Clojure IDE),
[Clay](https://scicloj.github.io/clay/) (literate programming for Clojure),
and [Noj](https://scicloj.github.io/noj/) (a comprehensive data science library).
It combines:

- **clooj**: Swing-based editor with syntax highlighting and project navigation
- **Clay**: Notebook-style evaluation and rendering
- **Noj**: Data science stack including Tablecloth, Fastmath, Scicloj.ml, and more
- **Default notebook**: Creates a starter notebook on first run

**FEGL not REPL**: Fegloj uses a **File-Eval-GUI-Loop** workflow instead of the traditional REPL.
Your file is the source of truth, and each save triggers evaluation.
Clay re-evaluates the file and displays results in your browser.
This is similar to [Figwheel](https://figwheel.org/) but for data science notebooks.

This supports data exploration, teaching, and prototyping.

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

## Learning Resources

- [Clay Documentation](https://scicloj.github.io/clay/) - Learn about literate programming and visualization
- [Noj Guide](https://scicloj.github.io/noj/) - Explore the data science toolkit
- [Clojure for Data Science](https://github.com/scicloj/clojure-data-scrapbook) - Tutorials and examples
- [Glance](https://scicloj.github.io/glance/) - Just plot

## License

See individual component licenses:
- clooj: Eclipse Public License 1.0
- Clay: EPL-2.0
- Noj: EPL-2.0

Enjoy exploring data with Clojure! 🎨
