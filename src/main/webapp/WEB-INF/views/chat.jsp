<%-- WEB-INF/views/chat.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FatDog AI</title>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            background-color: #faf9f6; /* Soft warm-white background */
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            color: #2c2a29;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .chat-container {
            width: 100%;
            max-width: 640px;
            height: 90vh;
            display: flex;
            flex-direction: column;
            background: #ffffff;
            border: 1px solid #eae8e3;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.02);
            overflow: hidden;
        }

        @media (max-width: 640px) {
            .chat-container {
                height: 100vh;
                border-radius: 0;
                border: none;
            }
        }

        .chat-header {
            padding: 20px 24px;
            border-bottom: 1px solid #eae8e3;
            background: #ffffff;
            text-align: center;
        }

        .chat-header h1 {
            margin: 0;
            font-size: 1.2rem;
            font-weight: 600;
            color: #1a1a1a;
            letter-spacing: -0.02em;
        }

        .chat-header p {
            margin: 4px 0 0 0;
            font-size: 0.8rem;
            color: #8c8a82;
        }

        .chat-history {
            flex: 1;
            overflow-y: auto;
            padding: 24px;
            display: flex;
            flex-direction: column;
            gap: 16px;
            background-color: #faf9f6;
        }

        .empty-state {
            text-align: center;
            color: #b0aeaa;
            margin-top: 60px;
            font-size: 0.9rem;
        }

        .message-card {
            background: #ffffff;
            border: 1px solid #eeebe5;
            border-radius: 12px;
            padding: 16px 20px;
            max-width: 80%;
            align-self: center;
            text-align: center;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.01);
            box-sizing: border-box;
        }

        .message-card.user-message {
            background: #f3f2ed;
            border-color: #e2dfd5;
        }

        .message-card.ai-message {
            background: #ffffff;
            border-color: #eeebe5;
        }

        .message-meta {
            margin-bottom: 8px;
            font-size: 0.75rem;
            color: #8c8a82;
            display: flex;
            justify-content: center;
            gap: 8px;
            align-items: center;
        }

        .message-owner {
            font-weight: 600;
            color: #4a4947;
        }

        .message-model {
            background: #eae8e3;
            padding: 2px 6px;
            border-radius: 4px;
            font-size: 0.7rem;
            color: #5a5957;
        }

        .message-time {
            color: #b0aeaa;
        }

        .message-body {
            font-size: 0.95rem;
            line-height: 1.5;
            color: #2c2a29;
            white-space: pre-wrap;
            word-break: break-all;
        }

        .chat-footer {
            padding: 20px 24px;
            border-top: 1px solid #eae8e3;
            background: #ffffff;
        }

        .chat-form {
            margin: 0;
        }

        .input-wrapper {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .chat-input {
            flex: 1;
            border: 1px solid #eae8e3;
            border-radius: 8px;
            padding: 10px 14px;
            font-size: 0.95rem;
            color: #2c2a29;
            outline: none;
            background: #faf9f6;
            transition: all 0.2s;
        }

        .chat-input:focus {
            border-color: #c5c2b9;
            background: #ffffff;
            box-shadow: 0 0 0 3px rgba(197, 194, 185, 0.15);
        }

        .chat-model-select {
            border: 1px solid #eae8e3;
            border-radius: 8px;
            padding: 10px 8px;
            font-size: 0.9rem;
            color: #5a5957;
            background: #faf9f6;
            outline: none;
            cursor: pointer;
        }

        .chat-model-select:focus {
            border-color: #c5c2b9;
        }

        .chat-send-btn {
            border: none;
            background: #2c2a29;
            color: #ffffff;
            padding: 10px 18px;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .chat-send-btn:hover {
            background: #4a4947;
        }
    </style>
</head>
<body>
    <main class="chat-container">
        <header class="chat-header">
            <h1>FatDog AI</h1>
            <p>차분하고 단정한 대화형 서비스</p>
        </header>

        <section class="chat-history">
            <c:if test="${empty chats}">
                <div class="empty-state">
                    <p>아직 나눈 대화가 없습니다. 아래에서 메시지를 입력해보세요.</p>
                </div>
            </c:if>
            <c:forEach var="chat" items="${chats}">
                <div class="message-card ${chat.owner == 'USER' ? 'user-message' : 'ai-message'}">
                    <div class="message-meta">
                        <span class="message-owner">${chat.owner}</span>
                        <span class="message-model">${chat.model}</span>
                        <span class="message-time">${chat.timestamp}</span>
                    </div>
                    <div class="message-body">${chat.message}</div>
                </div>
            </c:forEach>
        </section>

        <footer class="chat-footer">
            <form action="<c:url value="/chat"/>" method="post" class="chat-form">
                <div class="input-wrapper">
                    <input name="message" class="chat-input" placeholder="메시지를 입력하세요..." required autocomplete="off" />
                    <select name="model" class="chat-model-select">
                        <option value="gemma-4-26b-a4b-it">gemma-4-26b</option>
                        <option value="gemma-4-31b-it">gemma-4-31b</option>
                        <option value="gemini-3.1-flash-lite">gemini-3.1</option>
                        <option value="nemotron-3-ultra-550b-a55b">네모트론 3 (Nemotron)</option>
                        <option value="qwen/qwen3.6-27b">Qwen 3.6</option>
                    </select>
                    <button class="chat-send-btn">전송</button>
                </div>
            </form>
        </footer>
    </main>

    <script>
        // Auto scroll to bottom
        const historyDiv = document.querySelector('.chat-history');
        if (historyDiv) {
            historyDiv.scrollTop = historyDiv.scrollHeight;
        }

        // Format timestamps
        document.querySelectorAll('.message-time').forEach(el => {
            try {
                const rawTime = el.innerText.trim();
                if (rawTime) {
                    const date = new Date(rawTime);
                    if (!isNaN(date.getTime())) {
                        el.innerText = date.toLocaleTimeString('ko-KR', { 
                            hour: 'numeric', 
                            minute: '2-digit',
                            hour12: true 
                        });
                    }
                }
            } catch (e) {
                // fallback to raw string if parsing fails
            }
        });
    </script>
</body>
</html>

