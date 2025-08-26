# Resource Forge a Asset Management System

![Resource Forge](https://img.shields.io/badge/Resource%20Forge-Your%20Asset%20Management-brightgreen?style=for-the-badge&logo=appveyor)  

[![Version](https://img.shields.io/badge/Version-1.0.0-blue.svg?style=for-the-badge&logo=appveyor)](https://github.com/yourusername/resource-forge/releases)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg?style=for-the-badge&logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![AngularJS](https://img.shields.io/badge/AngularJS-1.x-red.svg?style=for-the-badge&logo=angularjs)](https://angularjs.org/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-green.svg?style=for-the-badge&logo=thymeleaf)](https://www.thymeleaf.org/)
[![Python](https://img.shields.io/badge/Python-3.12-blue.svg?style=for-the-badge&logo=python)](https://www.python.org/)
[![Database](https://img.shields.io/badge/Database-MySQL%20%7C%20PostgreSQL-lightgrey.svg?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge&logo=open-source-initiative)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-green.svg?style=for-the-badge&logo=github-actions)](https://github.com/yourusername/resource-forge/actions)
[![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen.svg?style=for-the-badge&logo=github)](https://github.com/yourusername/resource-forge/issues)
[![Stars](https://img.shields.io/github/stars/yourusername/resource-forge?style=for-the-badge&logo=github)](https://github.com/yourusername/resource-forge/stargazers)
[![Forks](https://img.shields.io/github/forks/yourusername/resource-forge?style=for-the-badge&logo=github)](https://github.com/yourusername/resource-forge/network/members)

## 🌟 Overview

Resource Forge is a **comprehensive, enterprise-level solution** for tracking, managing, and optimizing assets in an organization. Built with a robust **Java Spring Boot backend** and a **hybrid frontend** combining **Thymeleaf** for server-side rendering and **AngularJS** for dynamic client-side interactions, it supports the full lifecycle of physical and digital assets—from acquisition to disposal. A companion **Python tool** is included for automated system information collection.

With features for **financial tracking**, **maintenance contracts**, **insurance**, **subscriptions**, and **reporting**, streamlines operations, reduces manual errors, and ensures compliance through automation, auditing, and role-based access. Ideal for managing IT hardware, office equipment, or software licenses, delivers efficiency and insights.

This repository includes the complete backend source code (Java/Spring Boot), the Python collector script, and frontend templates/views integrating Thymeleaf and AngularJS.

## 📋 Table of Contents

- [🌟 Overview](#-overview)
- [🔑 Key Features](#-key-features)
- [🛠️ Technologies Stack](#️-technologies-stack)
- [🏗️ Project Architecture](#️-project-architecture)
- [🎨 Frontend Details](#-frontend-details)
- [🖥️ System Information Collector](#️-system-information-collector)
- [📥 Installation Guide](#-installation-guide)
- [⚙️ Configuration](#️-configuration)
- [🚀 Usage Examples](#-usage-examples)
- [📚 API Documentation](#-api-documentation)
- [⏰ Scheduled Tasks](#-scheduled-tasks)
- [🔒 Security and Auditing](#-security-and-auditing)
- [🤝 Contributing Guidelines](#-contributing-guidelines)
- [🛠️ Troubleshooting](#️-troubleshooting)
- [📄 License](#-license)
- [📧 Contact](#-Contact)

## 🔑 Key Features

Resource Forge offers powerful tools for seamless asset management:

### 📦 Asset Lifecycle Management
- **Create & Update Assets**: Add details like serial numbers, purchase info, and depreciation. 📝
- **Status Tracking**: Manage states (in-service, rented, scrapped) with notes and locations. 🔄
- **Bulk Operations**: Update multiple assets for scrap, project returns, or dismissals. 🔢
- **Assignments**: Track handovers to employees, projects, or customers. 👥

### 🛡️ Maintenance & Compliance
- **AMC Tracking**: Handle vendors and auto-expire contracts. 🔧
- **Insurance Management**: Monitor policies and premiums with expiration alerts. 🛡️
- **Warranty Checks**: Automated status updates. ⏳

### 💰 Financial & Reporting
- **Cost Tracking**: Record purchases, sales, and depreciation methods. 💸
- **Excel Exports**: Generate asset, log, and financial reports. 📊
- **Role-Specific Dashboards**: Views for management, finance, logistics, and more. 📈

### 📅 Subscription Handling
- **Plan Management**: Track start/end dates with auto-expirations. 📆
- **Notifications**: Reminders for recharges. 🔔

### 👤 User & Security
- **Role-Based Access**: Admin, finance, logistics roles with Spring Security. 🔑
- **Auditing**: Timestamps and user tracking for changes. 📝

### 🤖 Automation & Utilities
- **Scheduled Jobs**: Daily expirations for warranties, AMCs, and subscriptions. ⏰
- **Generators**: OTP and unique codes. 🔢
- **File Uploads**: Attach documents and images. 📎

### 🔗 Integration Tools
- **System Info Collector**: Auto-gather Windows device details for easy registration. 🖥️

## 🛠️ Technologies Stack

| Category       | Technologies                                                                 |
|----------------|-----------------------------------------------------------------------------|
| **Backend**    | Java 17+ ☕, Spring Boot 🌱, Spring Data JPA 📊, Spring Security 🔒, ModelMapper 🔄, Lombok 📝 |
| **Frontend**   | Thymeleaf 🌿 (server-side templating), AngularJS 🅰️ (client-side dynamics) |
| **Database**   | MySQL 🐬 or PostgreSQL 🐘 (JPA-compatible)                                   |
| **Scheduling** | Spring @Scheduled ⏱️ with cron jobs                                         |
| **REST**       | Controllers for CRUD 📡                                                     |
| **Auditing**   | Spring Data JPA Auditing 📜                                                 |
| **Other**      | RestTemplate 🌐, SecureRandom 🔐, OTP Generation 📟                         |
| **Collector**  | Python 3.12 🐍 (psutil, wmi, requests) – Convertible to .exe               |
| **Build**      | Maven 🛠️                                                                   |
| **Testing**    | JUnit 🧪                                                                   |
| **Docs**       | Swagger/OpenAPI 📖                                                          |

## 🏗️ Project Architecture

Modular structure under `src/main/java/com.app.resourceforge`:

- **audit/**: Auditable entities 🔍
- **autoService/**: Schedulers (e.g., `AssetAutoService.java`) ⏰
- **configuration/**: Utilities (e.g., `OTPGenerator.java`) 🛠️
- **controller/**: REST endpoints (e.g., `AssetController.java`) 📡
- **DTO/**: Data objects (e.g., `AssetForProjectDTO.java`) 📦
- **model/**: Entities (e.g., `Asset.java`) 🗃️
- **repository/**: JPA repos (e.g., `AssetRepository.java`) 📊
- **security/**: Configs (e.g., `WebSecurityConfig.java`) 🔒
- **service/**: Logic (e.g., `AssetServiceImpl.java`) ⚙️
- **util/**: Helpers (depreciation calcs) 📐

Entry: `AssetManagementApplication.java` 🚀

Frontend views/templates integrate Thymeleaf for rendering and AngularJS for interactivity.

Python: `system_info_collector.py` 🖥️

## 🎨 Frontend Details

The frontend is a hybrid setup combining **Thymeleaf** and **AngularJS**:
- **Thymeleaf 🌿**: Handles server-side templating for dynamic HTML generation, integrating seamlessly with Spring Boot controllers. It processes templates with data from the backend, supporting features like conditional rendering, loops, and internationalization.
- **AngularJS 🅰️**: Provides client-side dynamism, including data binding (e.g., `ng-model`), event handling (e.g., `ng-click`), and modular components for interactive UI elements like forms, tables, and dashboards.
- **Integration**: Thymeleaf renders initial pages, while AngularJS enhances them with JavaScript for real-time updates, API calls (via `$http`). This combination allows for efficient server-rendered views with rich client-side behavior.
- **Location in Repo**: Frontend files are typically under `src/main/resources/templates/` (Thymeleaf .html files with AngularJS scripts embedded or linked).

This setup ensures a responsive, user-friendly interface for asset dashboards, reports, and management forms.

## 🖥️ System Information Collector

Automates registration by collecting Windows specs and posting to `/asset/api/system-info`.

- **Collects**: UUID, MAC/IP, serial, processor, RAM, disks, OS, software, BIOS, antivirus. 📊
- **Usage**: Run as .exe (rename for company code). 🚀
- **Conversion**: `pyinstaller --onefile system_info_collector.py` 📦

## 📥 Installation Guide

1. **Clone**: `git clone https://github.com/rajsekharacharya/Resource-Forge.git` 📥
2. **Backend**:
   - Edit `application.properties` for DB 🔧
   - `mvn clean install` 🛠️
   - `mvn spring-boot:run` 🚀
3. **Frontend**: Thymeleaf templates auto-serve; include AngularJS via CDN or local files in templates.
4. **Collector**: `pip install wmi requests` 🐍
5. **DB**: Auto-creates tables 🗃️

## ⚙️ Configuration

- DB URL, credentials in properties 📄
- JWT secrets 🔑
- API URL in Python 🌐
- AngularJS configs in frontend scripts 🅰️

## 🚀 Usage Examples

- **API**: POST `/asset/createAsset` with JSON 📡
- **Frontend**: Access views like `/dashboard` for Thymeleaf-rendered pages with AngularJS interactions.
- **Collector**: Run EXE on Windows 🖥️

## 📚 API Documentation

Swagger: `http://localhost:8080/swagger-ui.html` 📖

## ⏰ Scheduled Tasks

Daily cron for expirations ⏱️

## 🔒 Security and Auditing

JWT auth, entity auditing 🔍

## 🤝 Contributing Guidelines

Fork, branch, PR! 🌟

## 🛠️ Troubleshooting

- DB issues: Check creds 🛠️
- Python: Admin rights needed 🔑
- Frontend: Ensure AngularJS scripts load; check Thymeleaf syntax.

## 🤝 Contributing
1. Fork the repo.
2. Create a branch: `git checkout -b feature/xyz`.
3. Commit changes: `git commit -m "Add feature"`.
4. Push: `git push origin feature/xyz`.
5. Open a PR.

Follow Java conventions and add tests.

## 📄 License
Apache 2.0 - See [LICENSE](LICENSE) for details.

## 📧 Contact
- **Author**: Rajsekhar Acharya
- **Email**: rajsekhar.acharya@gmail.com (placeholder)
- **GitHub**: [https://github.com/rajsekharacharya/Resource-Forge](https://github.com/rajsekharacharya/Resource-Forge)

⭐ Star the repo if you find it useful!