# Effortless Baritone

Effortless Baritone is a client-side bridge mod for Minecraft that seamlessly integrates the powerful, intuitive building tools of [Effortless Building](https://modrinth.com/mod/effortless-building) with the autonomous pathfinding and construction capabilities of [Baritone](https://github.com/cabaletta/baritone).

## Features

*   **Seamless Integration**: Use any Effortless Building tool (Line, Wall, Cylinder, Replace, etc.) to define what needs to be built or broken. The mod automatically captures these actions and queues them for Baritone.
*   **Task Queue & Batching**: Every build operation is assigned a unique Task ID. You can manage multiple builds independently or merge them into a single massive batch operation using the `/eb merge` commands.
*   **Fuzzy Matching**: Survival building is messy. Grass turns into dirt, stone drops as cobblestone. This mod uses a smart tag-based substitution system so Baritone won't get stuck trying to "fix" blocks that have naturally changed states (like snowy grass) or naturally downgraded based on your tools.
*   **Silk Touch Priority**: Automatically prioritizes using Silk Touch tools in Baritone when breaking lossy blocks like Bookshelves or Ores to preserve their state.
*   **SafeStop (safejop)**: A configurable safety toggle that immediately halts all Baritone operations if the player dies, preventing unpredictable pathfinding upon respawn.
*   **Interactive Chat UI**: Clickable chat messages allow you to easily copy Task IDs or start builds with a single click.

## Commands

*   `/eb start` - Starts the first task in the queue.
*   `/eb start <id>` - Starts a specific task by its ID.
*   `/eb start all` - Merges all queued tasks and starts building them immediately.
*   `/eb merge all` - Merges all queued tasks into a single task (without starting).
*   `/eb merge last <count>` - Merges the most recent `X` tasks into a single task.
*   `/eb stop` - Safely stops Baritone's current build process.
*   `/eb list` - Lists all tasks currently in the queue.
*   `/eb remove <id|last>` - Removes a specific task from the queue.
*   `/eb clear` - Clears the entire task queue.
*   `/eb safejop <true|false|1|0>` - Toggles the death-safety feature (Defaults to true).

## Setup & Requirements

*   **NeoForge**: Designed for NeoForge 1.21.1.
*   **Dependencies**: Requires both **Effortless Building** and **Baritone** to be installed in your `mods` folder.
*   **Client-Side**: This mod is entirely client-side. You do not need to install it on the server you are playing on (as long as the server allows Effortless Building and Baritone).

## For Developers (Community Contributions)

This project is open-source and welcomes contributions! The codebase is structured to be easy to read and extend. Check out `FuzzyMatcher.java` if you want to add support for more block equivalencies, or `StartCommand.java` if you want to expose more of Baritone's settings to the batch process.
