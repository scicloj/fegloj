# Fegloj

A batteries-included Clojure notebook for eval-on-save workflows.

## Rationale

Fegloj is a batteries-included notebook environment for exploratory data analysis and literate programming in Clojure.
It combines [Clay](https://scicloj.github.io/clay/) (literate programming) and [Noj](https://scicloj.github.io/noj/) (data science stack) with live-reload functionality:

- **Clay**: Notebook-style evaluation and browser-based rendering
- **Noj**: Comprehensive data science stack (Tablecloth, Fastmath, Scicloj.ml, and more)
- **Live-reload**: Auto-evaluation on file save
- **REPL**: Interactive development with full Clojure REPL
- **Default notebook**: Creates a starter notebook on first run

**FEGL not REPL**: Fegloj uses a **File-Eval-GUI-Loop** workflow instead of just a traditional REPL.
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
3. Edit `notebooks/my_notebook.clj` in your favorite editor (VS Code, Emacs, Vim, etc.)
4. Save the file and watch your browser update with rendered output

## Usage

On startup, fegloj:
- Creates a `notebooks/` directory with a default `my_notebook.clj`
- Starts Clay with live-reload mode
- Opens rendered output in your browser
- Provides a REPL for interactive development

**The FEGL Workflow**:
1. Edit your notebook (`notebooks/my_notebook.clj`) in your favorite editor
2. Save the file (Cmd+S / Ctrl+S)
3. Clay automatically re-evaluates and updates your browser
4. Iterate!

## Learning Resources

- [Clay Documentation](https://scicloj.github.io/clay/) - Learn about literate programming and visualization
- [Noj Guide](https://scicloj.github.io/noj/) - Explore the data science toolkit
- [Clojure for Data Science](https://github.com/scicloj/clojure-data-scrapbook) - Tutorials and examples
- [Glance](https://scicloj.github.io/glance/) - Just plot

## License

See individual component licenses:
- Clay: EPL-2.0
- Noj: EPL-2.0

Enjoy exploring data with Clojure! 🎨
