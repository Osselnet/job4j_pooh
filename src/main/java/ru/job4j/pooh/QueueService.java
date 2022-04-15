package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String nameQueue = req.getSourceName();
        String text;
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(nameQueue, new ConcurrentLinkedQueue<>());
            text = req.getParam();
            queue.get(nameQueue).add(text);
        } else {
            text = queue.getOrDefault(nameQueue, new ConcurrentLinkedQueue<>()).poll();
        }
        return new Resp(text, "200");
    }
}