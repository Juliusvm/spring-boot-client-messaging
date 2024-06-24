document.addEventListener("DOMContentLoaded", function() {
    const eventSource = new EventSource('/sse/stream-sse');

    eventSource.onmessage = function(event) {
        const messageList = document.getElementById("messages");
        const newMessage = document.createElement("li");
        newMessage.textContent = event.data;
        messageList.appendChild(newMessage);
    };

    eventSource.onerror = function(err) {
        console.error("EventSource failed:", err);
        eventSource.close();
    };
});
