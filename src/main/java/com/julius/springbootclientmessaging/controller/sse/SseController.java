package com.julius.springbootclientmessaging.controller.sse;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("sse")
public class SseController {

    private final List<SseEmitter> emitters = new ArrayList<>();

    @GetMapping(value = "/stream-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();

        // Add emitter to the list
        emitters.add(emitter);

        // Remove emitter when client disconnects
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody String message) {
        // Broadcast message to all active emitters
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(message);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);
            }
        }

        // Return a response to the API consumer
        return "Message sent to SSE clients: " + message;
    }
}
