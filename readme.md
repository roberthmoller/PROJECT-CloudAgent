# Spring Boot Cloud Agent

The Spring Boot Cloud Agent library is a Spring Boot-based solution for creating Large Language Model (LLM) agents in the cloud. It provides an abstract class `AiAgent` for developing cloud-based AI agents, utilizing Kotlin and Java, and is designed to integrate LLMs with cloud services seamlessly.

## Features
- **Abstract AiAgent Class**: A base class to build LLM agents with customizable fields.
- **String Template Files**: Utilize `name.st` templates for `systemDefinitionTemplate` and `functionCallTemplate`.
- **Modular Design**: Example implementations demonstrate the library's versatility.
- **Integration with External APIs**: Interfaces for various external services like jokes, weather, translation, and Wikipedia.

## Getting Started

### Prerequisites
- Java 17
- Kotlin 1.8.22
- Maven (compatible with Java 17)

### Installation
1. Clone the repository:
   ```bash
   git clone [repository-url]
   ```
2. Install dependencies using Maven:
   ```bash
   mvn install
   ```

### Implementing AiAgent
Create a new class extending `AiAgent` and provide implementations for the following fields:
- `name`: The name of your AI agent.
- `purpose`: The intended purpose of your agent.
- `llm`: Instance of `AiClient`.
- `systemDefinitionTemplate`: A `name.st` file with parameters `role`, `purpose`, `skills`, and `goal`.
- `functionCallTemplate`: A `name.st` file with parameters `function` and `result`.

### Example Implementations
The `example` folder includes:
1. **Comedian**: An AI agent that fetches jokes.
2. **Discovery**: A Eureka service registry server.
3. **Orchestrator**: An AI agent orchestrating others for optimal responses.
4. **Skills**: Interfaces for API clients using OpenFeign for services and external APIs.

## Contributing
Contributions to the Spring Boot Cloud Agent are welcome. Feel free to fork the repository, make changes, and submit pull requests.

## License
This project is distributed under [Your License]. See `LICENSE` for more information.
