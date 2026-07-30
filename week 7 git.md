# Git Hands-On Lab (HOL) — Complete Solutions

This document contains full, working solutions to all 5 Git Hands-On Labs:

1. Git Setup, Configuration & First Commit
2. `.gitignore` — Ignoring Unwanted Files
3. Branching & Merging
4. Merge Conflict Resolution
5. Cleanup & Push to Remote

Every command below is meant to be run in **Git Bash**. Replace placeholder
values (name, email, remote URL) with your own.

---

## Lab 1 — Setup, Notepad++ Integration, First Commit

### Objectives
- Configure Git on your machine
- Integrate Notepad++ as the default Git editor
- Create a repo and add a file to it

### Step 1: Setup your machine with Git Configuration

**1. Verify Git is installed:**
```bash
git --version
```
Expected output (version will vary):
```
git version 2.44.0.windows.1
```

**2. Configure user name and email (global, i.e. machine-level):**
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

**3. Verify the configuration:**
```bash
git config --global --list
```
or, to check an individual value:
```bash
git config --global user.name
git config --global user.email
```

### Step 2: Integrate Notepad++ as the default editor

**1. Check if `notepad++` runs from Git Bash:**
```bash
notepad++
```
If Bash returns `command not found`, Notepad++'s install folder is not on
your `PATH`.

**2. Add Notepad++ to the PATH environment variable:**
- Control Panel → System → Advanced system settings → Environment Variables
- Under **User variables**, select `Path` → **Edit** → **New**
- Add the Notepad++ install path, typically:
  ```
  C:\Program Files\Notepad++
  ```
- Click OK on all dialogs, then **close and reopen** Git Bash.

**3. Confirm Notepad++ now launches from Bash:**
```bash
notepad++
```

**4. Create a convenient alias for Notepad++ (edit `~/.bashrc`):**
```bash
notepad++ ~/.bashrc
```
Add this line inside the file, save, and close:
```bash
alias notepad++="'/c/Program Files/Notepad++/notepad++.exe' -multiInst -notabbar -nosession -noPlugin"
```
Reload the profile:
```bash
source ~/.bashrc
```

**5. Set Notepad++ as Git's default editor:**
```bash
git config --global core.editor "notepad++ -multiInst -notabbar -nosession -noPlugin"
```

**6. Verify Notepad++ is the configured editor:**
```bash
git config --global -e
```
The `-e` flag opens the global config file in the configured editor
(Notepad++). It should show something like:
```ini
[user]
    name = Your Name
    email = your.email@example.com
[core]
    editor = notepad++ -multiInst -notabbar -nosession -noPlugin
```

### Step 3: Add a file to the source code repository

**1. Create and initialize a new repository "GitDemo":**
```bash
git init GitDemo
cd GitDemo
```

**2. Verify the repository was initialized (Git stores metadata in `.git`):**
```bash
ls -la
```
Output shows a hidden `.git` folder — this confirms initialization.

**3. Create `welcome.txt` with content:**
```bash
echo "Welcome to Git Demo" > welcome.txt
```

**4. Verify the file was created:**
```bash
ls -l
```

**5. Verify the file's content:**
```bash
cat welcome.txt
```

**6. Check status — the file is untracked (in the Working Directory only):**
```bash
git status
```
Output:
```
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)
        welcome.txt
```

**7. Stage the file so Git tracks it:**
```bash
git add welcome.txt
```

**8. Commit with a multi-line message using the default editor (Notepad++):**
```bash
git commit
```
Notepad++ opens — type a commit message, e.g.:
```
Add welcome.txt

Initial commit adding the welcome file to GitDemo repository.
```
Save and close Notepad++ to complete the commit.

**9. Confirm the local repo is now clean (file committed to local repository):**
```bash
git status
```
Output:
```
On branch master
nothing to commit, working tree clean
```
`welcome.txt` is now part of the **local repository**.

**10. Create a remote repository "GitDemo" on GitLab/GitHub** (via the web UI),
then link it:
```bash
git remote add origin https://github.com/<your-username>/GitDemo.git
git remote -v
```

**11. Pull from the remote (sync any existing history, e.g. a README/license):**
```bash
git pull origin master
```
> If your remote's default branch is `main` instead of `master`, use
> `git pull origin main` and `git push origin main` throughout.

**12. Push your local commits to the remote:**
```bash
git push origin master
```

---

## Lab 2 — `.gitignore` (Ignoring Unwanted Files)

### Objective
Ignore all `.log` files and an entire `log` folder from being tracked/committed.

### Solution

**1. Make sure you're inside your Git repo (reuse `GitDemo` or any repo):**
```bash
cd GitDemo
```

**2. Create a `.log` file and a `log` folder with a file inside it:**
```bash
echo "sample log entry" > debug.log
mkdir log
echo "another log entry" > log/app.log
```

**3. Check status before ignoring — both currently show as untracked:**
```bash
git status
```
Output:
```
Untracked files:
        debug.log
        log/
```

**4. Create/update `.gitignore` to exclude `.log` files and the `log/` folder:**
```bash
echo "*.log" >> .gitignore
echo "log/" >> .gitignore
```
(Or open it in the editor: `notepad++ .gitignore`)

`.gitignore` contents:
```gitignore
# Ignore all log files
*.log

# Ignore the log directory entirely
log/
```

**5. Verify Git now ignores them:**
```bash
git status
```
Output:
```
On branch master
Untracked files:
        .gitignore
```
Notice `debug.log` and `log/` **no longer appear** — only the new
`.gitignore` file itself shows as untracked (working directory state).

**6. Stage and commit the `.gitignore` file:**
```bash
git add .gitignore
git commit -m "Add .gitignore to exclude log files and log folder"
```

**7. Final verification — status is clean, and the ignored files are confirmed:**
```bash
git status
git status --ignored
```
`git status --ignored` explicitly lists `debug.log` and `log/` under
"Ignored files", proving the working directory, local repository, and
`.gitignore` rule are all in sync.

---

## Lab 3 — Branching and Merging

### Branching

**1. Create a new branch "GitNewBranch":**
```bash
git branch GitNewBranch
```

**2. List all local and remote branches (the `*` marks the current branch):**
```bash
git branch -a
```
Output:
```
* master
  GitNewBranch
  remotes/origin/master
```

**3. Switch to the new branch and add a file with content:**
```bash
git checkout GitNewBranch
echo "This is a change made on GitNewBranch" > branchfile.txt
```

**4. Commit the changes to the branch:**
```bash
git add branchfile.txt
git commit -m "Add branchfile.txt on GitNewBranch"
```

**5. Check status:**
```bash
git status
```
Output:
```
On branch GitNewBranch
nothing to commit, working tree clean
```

### Merging

**1. Switch back to master:**
```bash
git checkout master
```

**2. List command-line differences between master and the branch:**
```bash
git diff master GitNewBranch
```

**3. List visual differences using the P4Merge tool:**
```bash
git difftool -t p4merge master GitNewBranch
```
> Requires P4Merge configured as Git's diff tool, e.g.:
> ```bash
> git config --global diff.tool p4merge
> git config --global difftool.p4merge.path "/c/Program Files/Perforce/p4merge.exe"
> ```

**4. Merge the branch into master:**
```bash
git merge GitNewBranch
```
Since there's no conflicting change on master, this is a **fast-forward**
merge (or produces a merge commit if master has diverged).

**5. Observe the merge history graph:**
```bash
git log --oneline --graph --decorate
```

**6. Delete the branch now that it's merged, then check status:**
```bash
git branch -d GitNewBranch
git status
git branch -a
```
`-d` (safe delete) only deletes branches that are fully merged; Git refuses
otherwise, prompting you to use `-D` to force delete.

---

## Lab 4 — Conflict Resolution

### Objective
Resolve a merge conflict that occurs when master and a branch modify the
same file differently.

### Solution

**1. Verify master is in a clean state:**
```bash
git checkout master
git status
```

**2. Create a branch "GitWork" and add `hello.xml`:**
```bash
git checkout -b GitWork
echo "<message>Hello from GitWork branch</message>" > hello.xml
```

**3. Update the content of `hello.xml` and observe status:**
```bash
echo "<message>Updated hello.xml on GitWork</message>" > hello.xml
git status
```
Output shows `hello.xml` as untracked/modified (new file not yet staged).

**4. Commit the changes on the branch:**
```bash
git add hello.xml
git commit -m "Add and update hello.xml on GitWork"
```

**5. Switch to master:**
```bash
git checkout master
```

**6. Add `hello.xml` to master with different content:**
```bash
echo "<message>Hello from master branch</message>" > hello.xml
```

**7. Commit the changes to master:**
```bash
git add hello.xml
git commit -m "Add hello.xml on master with different content"
```

**8. Observe the diverging history of both branches:**
```bash
git log --oneline --graph --decorate --all
```
You'll see master and GitWork have diverged, each with its own commit for
`hello.xml`.

**9. Check differences using Git's diff tool:**
```bash
git diff master GitWork -- hello.xml
```

**10. Visualize the differences using P4Merge:**
```bash
git difftool -t p4merge master GitWork -- hello.xml
```

**11. Merge the branch into master:**
```bash
git merge GitWork
```
Because both branches changed the same lines of `hello.xml`, Git reports:
```
Auto-merging hello.xml
CONFLICT (add/add): Merge conflict in hello.xml
Automatic merge failed; fix conflicts and then commit the result.
```

**12. Observe Git's conflict markup in the file:**
```bash
cat hello.xml
```
```xml
<<<<<<< HEAD
<message>Hello from master branch</message>
=======
<message>Updated hello.xml on GitWork</message>
>>>>>>> GitWork
```

**13. Use the 3-way merge tool (P4Merge) to resolve the conflict:**
```bash
git mergetool -t p4merge
```
Manually pick/combine the correct content in P4Merge's 3-way view (Base /
Local / Remote → Merged Result), save, and close the tool. This rewrites
`hello.xml` with the resolved content and creates a `hello.xml.orig` backup.

**14. Commit the merge once the conflict is resolved:**
```bash
git add hello.xml
git commit -m "Resolve merge conflict in hello.xml"
```

**15. Check status — a leftover backup file appears; add it to `.gitignore`:**
```bash
git status
```
Output shows `hello.xml.orig` as untracked.
```bash
echo "*.orig" >> .gitignore
```

**16. Commit the `.gitignore` update:**
```bash
git add .gitignore
git commit -m "Ignore merge tool backup files (*.orig)"
```

**17. List all available branches:**
```bash
git branch -a
```

**18. Delete the branch now that it's merged into master:**
```bash
git branch -d GitWork
```

**19. Observe the final consolidated history:**
```bash
git log --oneline --graph --decorate
```

---

## Lab 5 — Cleanup and Push Back to Remote

### Objective
Sync the local clean-up work with the remote repository.

### Solution

**1. Verify master is in a clean state:**
```bash
git checkout master
git status
```
Output:
```
On branch master
nothing to commit, working tree clean
```

**2. List all available branches (confirm feature branches are cleaned up):**
```bash
git branch -a
```

**3. Pull the latest remote changes into master:**
```bash
git pull origin master
```
> Use `git pull origin main` if your remote's default branch is `main`.

**4. Push all pending local commits (from the conflict-resolution lab) to the remote:**
```bash
git push origin master
```

**5. Verify the changes are reflected on the remote:**
```bash
git log origin/master --oneline --graph --decorate
```
Alternatively, open the repository on GitHub/GitLab in a browser and
confirm the latest commits (including the merge and `.gitignore` update)
are visible in the commit history.

---

## Quick Command Reference

| Purpose | Command |
|---|---|
| Check Git version | `git --version` |
| Set global identity | `git config --global user.name "..."` / `user.email "..."` |
| Set default editor | `git config --global core.editor "..."` |
| Initialize repo | `git init <name>` |
| Check status | `git status` |
| Stage a file | `git add <file>` |
| Commit | `git commit -m "message"` |
| Create branch | `git branch <name>` |
| Switch branch | `git checkout <name>` |
| Create + switch | `git checkout -b <name>` |
| List branches | `git branch -a` |
| Diff branches | `git diff <b1> <b2>` |
| Visual diff | `git difftool -t p4merge <b1> <b2>` |
| Merge branch | `git merge <name>` |
| Resolve conflicts | `git mergetool -t p4merge` |
| Delete merged branch | `git branch -d <name>` |
| Force delete branch | `git branch -D <name>` |
| View graph log | `git log --oneline --graph --decorate --all` |
| Add remote | `git remote add origin <url>` |
| Pull | `git pull origin master` |
| Push | `git push origin master` |

---

*Compiled solutions for Git Hands-On Labs 1–5 (Setup & Config, .gitignore, Branching & Merging, Conflict Resolution, Cleanup & Push).*
