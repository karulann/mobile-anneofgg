# Next Tasks

## Current Task

Review the Android Studio project setup before building features.

## Immediate Tasks

1. Confirm the project opens correctly in Android Studio.
2. Confirm the app runs on emulator or real Android phone.
3. Confirm current package name.
4. Rename package to `com.karulann.anneofgreengablesfullseriesebook`.
5. Initialize Git.
6. Create first commit.
7. Create GitHub repository.
8. Push project to GitHub.

## Recommended Next Action

Rename the package before the first serious commit.

Current temporary package:
`com.example.anneofgreengablesfullseriesebook`

Confirmed production package:
`com.karulann.anneofgreengablesfullseriesebook`

Reason:
It is cleaner to fix this before app architecture, Firebase, AdMob, and Play Store setup are added.

## After Package Rename

Run:

```bash
git init
git add .
git commit -m "Initial Android project setup"
git branch -M main
```

Then create GitHub repository:

Repository name:
`anne-ebook-android`

Then connect and push:

```bash
git remote add origin https://github.com/YOUR_USERNAME/anne-ebook-android.git
git push -u origin main
```

## Next Development Step After GitHub

Create the app folder structure:

```text
core/
data/
domain/
feature/
monetization/
di/
```

Then build the first feature:

Home screen with book list.
