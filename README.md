# Mess Plate Planner

A cloud-based web app that predicts how many plates the hostel mess should cook, so no food is wasted.

**Live demo:** https://mess-waste-predictor.onrender.com
**Staff dashboard:** https://mess-waste-predictor.onrender.com/admin.html

## Problem

The mess cooks for everyone, but many students skip meals. Extra food is thrown away, and when the guess is too low, late students find nothing left. The mess has no data about who is coming.

## Solution

Before each meal, students tap "I'm eating" or "I'm skipping" on a simple web page. The Java backend counts the votes and predicts the plates to cook.

```
Plates to cook = students eating + 5% buffer (rounded up)
```

## How it works

1. Student opens the web page and enters roll number, meal, and choice.
2. The Spring Boot backend receives the vote through a REST API.
3. The vote is saved in the database. A repeat vote updates the old one, so there is no double counting.
4. The backend counts eating votes and adds a 5% buffer.
5. The staff dashboard shows plates to cook and plates saved. It refreshes every 10 seconds.

## Tech stack

| Part | Technology |
|---|---|
| Frontend | HTML, CSS, JavaScript |
| Backend | Java, Spring Boot |
| Database | H2 (can move to MySQL or PostgreSQL) |
| Packaging | Docker |
| Code hosting | GitHub |
| Cloud hosting | Render |

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/vote` | Save a student's vote |
| GET | `/api/summary` | Get meal-wise count and plates to cook |

## Run locally

Requirements: Java and Maven.

```
mvn spring-boot:run
```

Then open `http://localhost:8080` in the browser.

## Future scope

- Move to PostgreSQL or MySQL so data stays after a restart
- Student login with password or OTP
- Smart prediction using past data and machine learning
- WhatsApp or app reminders before voting closes

## Team

B.Tech CSE (DS&AI), II Year, Dr. M.G.R. Educational and Research Institute

- S.VAISHNAVI

Course: Cloud Applications Developer
