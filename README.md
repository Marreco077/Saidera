# Projeto Saidera

## Overview

**Saidera** is a fullstack system developed for managing and splitting group bills. The goal is to simplify the process of dividing expenses between friends, family, or colleagues, ensuring that everyone contributes their fair share. Saidera offers an efficient solution to calculate how much each person should pay, track payments, and manage the quantity of items consumed.

## Features

- **Comanda Sharing**: Generate and share links so others can access and participate in the same comanda, making group management easier.
- **Participant Management**: Add or remove people from the comanda with ease, providing flexibility in controlling who is part of the split.
- **Payment System**: Mark participants as "paid" to easily track who contributed, avoiding duplicate payments.
- **Quantity Management**: Add varying quantities of items for each participant, considering each person's share of the total cost.
- **Dashboard**: Access an intuitive dashboard showing the total bill amount, each participant's payment, and the remaining balance, with real-time updates.

## Technologies Used

### Backend

- **Java 17** with **Spring Boot**: The backend application is built with Java, using the Spring Boot framework to facilitate rapid development and integration with other technologies.

### Frontend

- **JavaScript**, **HTML**, **CSS**, and **Bootstrap**: The user interface is developed using traditional web technologies, with Bootstrap to ensure a responsive design and a good mobile experience.

### Database

- **MySQL**: The system uses MySQL to store all data related to participants, items, and values.

### Containerization

- **Docker**: Saidera uses Docker to isolate the database environment, making it easier to configure and run the system across different environments, ensuring consistency across development machines.

## Architecture

Saidera follows a **fullstack** architecture, divided into three main components:

1. **Frontend**: Responsible for the user interface and interaction with the end user, enabling the management of comandas, participants, and payments.
2. **Backend**: Handles business logic processing, such as the division of bills, payment tracking, and interaction with the database.
3. **Database**: Stores data about comandas, participants, items, and payments.
