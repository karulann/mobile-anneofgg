# Technical and Product Decisions

## Product Decision

App Name:
Anne of Green Gables Full Series Ebook

Platform:
Android

Publishing:
New Google Play app

Existing Old App:
The old app will not be updated. This project will be developed and published as a new app.

## Package Name

Temporary Android Studio package name:
`com.example.anneofgreengablesfullseriesebook`

Confirmed production package name:
`com.karulann.anneofgreengablesfullseriesebook`

Decision:
Use `com.karulann.anneofgreengablesfullseriesebook` before serious development and before publishing.

Reason:
`com.example` is a placeholder namespace and should not be used for a production Google Play app.

## Target Users

The app will target:

- Children
- Teens
- Adults
- Elderly readers

Because children are included, the app must be designed as family friendly and must avoid risky monetization patterns.

## Content Source

Book content source:
Project Gutenberg

Important:
The app may use public domain text from Project Gutenberg, but the app should not use Project Gutenberg branding in a misleading way.

## Technology Stack

Language:
Kotlin

UI:
Jetpack Compose

Architecture:
MVVM with light Clean Architecture

Local Database:
Room

Settings Storage:
DataStore

Dependency Injection:
Hilt

Navigation:
Navigation Compose

Crash Reporting:
Firebase Crashlytics

Analytics:
Firebase Analytics

Monetization:
AdMob and Google Play Billing

## Architecture Decision

Use MVVM with a light Clean Architecture structure.

Layers:

1. UI Layer
   - Screens
   - Composables
   - ViewModels
   - UI state

2. Domain Layer
   - Models
   - Repository interfaces
   - Use cases

3. Data Layer
   - Local assets
   - Room database
   - DataStore
   - Repository implementations

Reason:
This gives the app a professional structure without overengineering.

## Monetization Decision

Use:

- Free app
- Child safe AdMob banner ads
- One time Remove Ads purchase

Do not use in MVP:

- Interstitial ads
- Rewarded ads
- Native ads
- Subscription
- Ads inside the reader screen

Reason:
The reading experience should remain clean, and children are part of the target audience.

## Reader Experience Decision

The reader screen must prioritize:

- Font size control
- Line spacing
- Light mode
- Dark mode
- Sepia mode
- Simple navigation
- Saved progress
- Large and clear touch targets

## Privacy Decision

For MVP, avoid:

- Login
- User generated content
- Cloud sync
- Chat
- Collection of unnecessary personal data
- Unnecessary permissions

Reason:
This reduces privacy risk, especially because children are included in the target audience.
