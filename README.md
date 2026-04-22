# CreatorAlly - Central Platform

A Spring Boot application for managing video content creation, editing, and publishing workflows. This platform connects editors and influencers/creators for seamless video collaboration and YouTube publishing.

## Overview

CreatorAlly Central Platform is a backend service that facilitates:
- **Editor Management** - Register and manage video editors
- **Influencer/Creator Management** - Manage content creators
- **Video Upload & Processing** - Handle large video files (up to 10GB)
- **YouTube Integration** - Automated video publishing to YouTube using Google APIs
- **Job Scheduling** - Redis-based job queue for scheduled media publishing
- **Content Metadata Management** - Store and manage video details, categories, keywords, and privacy settings

## Tech Stack

- **Framework**: Spring Boot 3.3.0 (Java 17)
- **Database**: MySQL with JPA/Hibernate
- **Cache & Job Queue**: Redis
- **External APIs**: Google YouTube API v3, Google OAuth 2.0
- **Build Tool**: Maven
- **Additional Libraries**: Lombok, Apache HttpComponents

## Prerequisites

- Java 17+
- Maven 3.6+
- MySQL Server
- Redis Server (localhost:6379)
- Google API credentials (`client_secrets.json`)

## Project Structure

```
src/main/java/com/creatorally/centralplatform/
├── controllers/              # REST API endpoints
│   ├── EditorController.java
│   ├── InfluencerController.java
│   └── SchedulerController.java
├── services/                 # Business logic
│   ├── EditorService.java
│   ├── InfluencerService.java
│   ├── YoutubeUploadService.java
│   └── impl/
├── models/
│   ├── entities/            # JPA entities
│   ├── requests/            # Request DTOs
│   ├── responses/           # Response DTOs
│   └── enums/
├── repository/              # Data access layer
├── scheduler/               # Job scheduling with Redis
├── config/                  # Configuration classes
├── constants/               # Application constants
└── helper/                  # Utility helpers
```

## Setup & Installation

### 1. Clone Repository
```bash
git clone <repository-url>
cd creatorally
```

### 2. Configure Database
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/creatorally?useSSL=false
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### 3. Configure YouTube API
- Place `client_secrets.json` in `src/main/resources/`
- Obtain credentials from [Google Cloud Console](https://console.cloud.google.com/)

### 4. Build & Run
```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

The application will start on **http://localhost:8081**

## Database Schema

### Editor
```sql
- id (INT, PK)
- username (VARCHAR)
- password (VARCHAR)
- name (VARCHAR)
```

### Influencer
```sql
- id (INT, PK)
- username (VARCHAR)
- password (VARCHAR)
- accessUrl (VARCHAR)
```

### Media
```sql
- id (INT, PK)
- isVerified (BOOLEAN)
- editedUrl (VARCHAR)
- uneditedUrl (VARCHAR)
- editorId (INT, FK)
- creatorId (INT, FK)
- mediaType (ENUM)
- scheduledTime (BIGINT)
- title (VARCHAR)
- description (TEXT)
- category (VARCHAR)
- keywords (VARCHAR)
- privacyStatus (VARCHAR)
```

## API Endpoints

### Editor Management

**Create Editor**
```
POST /api/1.0/editor
Content-Type: application/json

{
  "username": "editor1",
  "password": "securepassword",
  "name": "John Editor"
}
```

**Get All Editors**
```
GET /api/1.0/editors
```

**Get Editor by ID**
```
GET /api/1.0/editors/{id}
```

**Upload Video**
```
POST /api/1.0/editor/upload/{id}
Content-Type: multipart/form-data

Form Data:
- file: <video_file>
- title: "Video Title"
- description: "Video Description"
- category: "Entertainment"
- keywords: "video,keywords"
- privacyStatus: "PUBLIC|PRIVATE|UNLISTED"
```

### Influencer Management
```
POST /api/1.0/influencer              # Create influencer
GET /api/1.0/influencer               # Get all influencers
GET /api/1.0/influencer/{id}          # Get influencer by ID
```

### Scheduler Management
```
POST /api/1.0/scheduler/schedule       # Schedule media publishing
GET /api/1.0/scheduler/jobs            # Get scheduled jobs
```

## Configuration

### File Upload Limits
```properties
spring.servlet.multipart.max-file-size=10GB
spring.servlet.multipart.max-request-size=10GB
spring.servlet.multipart.enabled=true
```

### Server Port
```properties
server.port=8081
```

### Redis Configuration
Redis is used for:
- Job scheduling and persistence
- Scheduled jobs are stored with prefix: `scheduled_jobs:`
- Default connection: `localhost:6379`

## Features

### 1. Video Upload
- Supports files up to 10GB
- Multipart form data upload
- Video metadata capture (title, description, keywords, category)

### 2. YouTube Integration
- OAuth 2.0 authentication
- Automated video publishing
- Privacy status control (Public, Private, Unlisted)
- Video categorization

### 3. Job Scheduling
- Schedule media publishing for future times
- Redis-backed job queue
- Spring @Scheduled tasks for job processing
- Automatic retry logic

### 4. Media Verification
- Track edited and unedited video URLs
- Verification status tracking
- Editor and creator assignment

## Development

### Build
```bash
mvn clean build
```

### Run Tests
```bash
mvn test
```

### Package
```bash
mvn package
```

### Code Style
The project uses Lombok to reduce boilerplate code:
- `@Data` - Generates getters, setters, toString, equals, hashCode
- `@Builder` - Builder pattern support
- `@Slf4j` - SLF4J logger injection
- `@NoArgsConstructor` / `@AllArgsConstructor` - Constructor generation

## Error Handling

The application includes validation and error handling for:
- Invalid file sizes
- Missing required fields
- Database constraint violations
- YouTube API errors
- Redis connection failures

## Logging

SLF4J is integrated throughout the application:
```java
@Slf4j
log.info("Message");
log.error("Error", exception);
```

## Security Considerations

⚠️ **Important**: 
- Never commit `client_secrets.json` to version control
- Store database credentials in environment variables
- Use HTTPS in production
- Implement authentication/authorization for API endpoints
- Hash passwords before storing

## Troubleshooting

### Redis Connection Error
- Ensure Redis is running: `redis-cli ping` should return `PONG`
- Check Redis is listening on localhost:6379

### Database Connection Error
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists

### YouTube API Errors
- Verify `client_secrets.json` is properly configured
- Check API quotas in Google Cloud Console
- Ensure OAuth tokens are valid

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

This project is proprietary to CreatorAlly.

## Support

For issues or questions, contact the development team.
