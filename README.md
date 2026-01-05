# COMP2000 Restaurant Android Application

## Module
COMP2000 – Mobile Application Development  

## Assessment
Coursework 2 – Android Application Implementation  

## Student
Ryan Aston  

## Overview
This project is an Android application developed as part of COMP2000 Assessment 2.  
The application simulates a restaurant management system with **role-based functionality** for **guest** and **staff** users.

Guests can browse the menu, create and view reservations, and manage basic preferences.  
Staff users can manage menu items, view and control reservations, and monitor operational activity through a staff dashboard.

The application is built using **Java** and **Android Studio**, integrates a **cloud-based backend**, and follows **modern Android development practices**.

---

## Features

### Guest Features
- Browse restaurant menu by category
- Create table reservations
- View existing reservations
- Account-based login (Firebase Authentication)
- Optional guest quick-login for testing
- Visual feedback for reservation states

### Staff Features
- Staff-only dashboard
- View and manage all reservations
- Mark reservations as completed
- Delete reservations
- Manage menu items
- Operational alerts for new or pending reservations
- Staff quick-login for testing

---

## Architecture & Technologies

### Frontend
- Java
- Android SDK
- Material Design Components
- XML Layouts

### Backend & Data
- **Firebase Firestore** (central cloud database)
- **Firebase Authentication** (guest login accounts)
- **Room (SQLite)** for local caching and offline support

### Design & Structure
- Repository pattern
- Separation of concerns
- Role-based application control
- Asynchronous data handling
- SOLID principles applied where appropriate

---

## User Roles

| Role  | Access |
|------|--------|
| Guest | Menu browsing, reservation creation, account access |
| Staff | Reservation management, menu editing, operational dashboard |

Role enforcement is handled at both the UI and application logic level.

---

## Testing
- Task-based usability testing
- Two participants (guest and staff roles)
- Testing conducted using Android Studio emulator
- Iterative UI improvements applied based on feedback

---

## Legal, Social, Ethical, and Professional (LSEP)
- GDPR-aware data handling
- Minimal personal data collection
- User consent obtained during testing
- Professional development practices followed
- GitHub version control used throughout development

---

## How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/RevRaston/COMP2000-Restaurant-App.git
