# Spring Trade MCP Server

A Spring Boot application that exposes trade operations through both REST API and MCP (Model Context Protocol) server, enhanced with Spring AI capabilities for intelligent trade analysis and insights.

## Features

### Core Trade Operations
- **Get All Trades**: Retrieve all trades from the database
- **Get Trade by ID**: Fetch specific trade details
- **Create Trade**: Create new trade entries
- **Trade Statistics**: Get aggregated trade statistics

### AI-Powered Analysis
- **Trade Analysis**: AI-powered insights on trading patterns and trends
- **Recommendations**: Get AI recommendations for specific trade types
- **Trend Prediction**: Predict future trading trends based on historical data
- **Combined Insights**: Comprehensive analysis combining multiple AI perspectives

### Dual Interface
- **REST API**: Traditional HTTP endpoints for trade operations and AI analysis
- **MCP Server**: WebSocket-based Model Context Protocol server for real-time communication

## Technology Stack

- **Spring Boot 3.4.5**: Core framework
- **Spring AI**: AI capabilities with OpenAI and Ollama support
- **Spring WebSocket**: Real-time communication
- **PostgreSQL**: Database
- **Flyway**: Database migration
- **Gradle**: Build tool

## Prerequisites

1. **Java 21**: Required for Spring Boot 3.x
2. **PostgreSQL**: Database server
3. **Ollama** (optional): For local AI processing
4. **OpenAI API Key** (optional): For cloud-based AI processing

## Setup Instructions

### 1. Database Setup

Create a PostgreSQL database:
```sql
CREATE DATABASE ibandb;
```

### 2. AI Configuration

#### Option A: Ollama (Local AI)
Install Ollama and run a model:
```bash
# Install Ollama
curl -fsSL https://ollama.ai/install.sh | sh

# Pull and run a model
ollama pull llama2
ollama run llama2
```

#### Option B: OpenAI (Cloud AI)
Uncomment and configure OpenAI settings in `application.properties`:
```properties
spring.ai.openai.api-key=your-openai-api-key
spring.ai.openai.base-url=https://api.openai.com
spring.ai.openai.chat.options.model=gpt-3.5-turbo
```

### 3. Application Configuration

Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ibandb
spring.datasource.username=your-username
spring.datasource.password=your-password
```

### 4. Build and Run

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The application will start on `http://localhost:8081`

## API Endpoints

### REST API

#### Trade Operations
- `GET /trades` - Get all trades
- `GET /trade/{tradeId}` - Get trade by ID
- `POST /trade` - Create new trade

#### AI Analysis
- `GET /api/ai/analyze` - Analyze all trades
- `GET /api/ai/recommendations?tradeType={type}` - Get recommendations for trade type
- `GET /api/ai/predict` - Predict trading trends
- `GET /api/ai/insights` - Get combined AI insights

### MCP Server

The MCP server is available at WebSocket endpoint: `/mcp`

#### Trade Operations (MCP)
- `/app/trades/getAll` - Get all trades
- `/app/trades/getById` - Get trade by ID
- `/app/trades/create` - Create new trade
- `/app/trades/statistics` - Get trade statistics

#### AI Operations (MCP)
- `/app/ai/analyze` - Analyze trades
- `/app/ai/recommendations` - Get recommendations
- `/app/ai/predict` - Predict trends
- `/app/ai/insights` - Get combined insights

## MCP Protocol

The MCP server uses JSON-RPC 2.0 protocol over WebSocket:

### Request Format
```json
{
  "jsonrpc": "2.0",
  "id": "1",
  "method": "/trades/getAll",
  "params": {}
}
```

### Response Format
```json
{
  "jsonrpc": "2.0",
  "id": "1",
  "result": {
    "trades": [...],
    "count": 5
  }
}
```

## Testing the MCP Server

### Web Client
Access the built-in test client at: `http://localhost:8081/mcp-client.html`

### JavaScript Client Example
```javascript
// Connect to MCP server
const socket = new SockJS('/mcp');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    // Subscribe to responses
    stompClient.subscribe('/topic/trades', function (response) {
        const message = JSON.parse(response.body);
        console.log('Trade response:', message);
    });
    
    // Send request
    const request = {
        jsonrpc: "2.0",
        id: "1",
        method: "/trades/getAll",
        params: {}
    };
    stompClient.send("/app/trades/getAll", {}, JSON.stringify(request));
});
```

### Python Client Example
```python
import websocket
import json

# Connect to MCP server
ws = websocket.create_connection("ws://localhost:8081/mcp/websocket")

# Send request
request = {
    "jsonrpc": "2.0",
    "id": "1",
    "method": "/trades/getAll",
    "params": {}
}
ws.send(json.dumps(request))

# Receive response
response = ws.recv()
print(json.loads(response))
```

## AI Capabilities

### Trade Analysis
The AI service provides:
- Pattern recognition in trading data
- Risk assessment
- Optimization recommendations
- Market analysis based on trade types

### Recommendations
- Optimal trade strategies
- Risk management approaches
- Market opportunities
- Best practices for specific trade types

### Trend Prediction
- Future trading volume trends
- Expected trade type distribution
- Potential market movements
- Risk factors to watch

## Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# AI Configuration
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama2

# MCP Server Configuration
mcp.server.enabled=true
mcp.server.websocket.path=/mcp
mcp.server.topics.trades=/topic/trades
mcp.server.topics.ai=/topic/ai
```

## Development

### Project Structure
```
src/main/java/com/sample/trade/
├── controller/          # REST controllers
├── mcp/                # MCP server components
├── ai/                 # AI services
├── service/            # Business logic
├── repository/         # Data access
└── command/            # DTOs and commands
```

### Adding New MCP Methods
1. Add method to `TradeMcpService` or `AiMcpService`
2. Add corresponding endpoint in `McpController` or `AiMcpController`
3. Update client documentation

## Troubleshooting

### Common Issues

1. **Database Connection**: Ensure PostgreSQL is running and credentials are correct
2. **AI Service**: Check if Ollama is running or OpenAI API key is configured
3. **WebSocket Connection**: Verify the MCP endpoint is accessible at `/mcp`

### Logs
Enable debug logging by adding to `application.properties`:
```properties
logging.level.com.sample.trade=DEBUG
logging.level.org.springframework.web=DEBUG
```

## License

This project is licensed under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request
