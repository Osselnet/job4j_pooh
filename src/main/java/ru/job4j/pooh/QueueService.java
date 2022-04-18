package ru.job4j.pooh;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String nameQueue = req.getSourceName();
        String result = "";
        String status = "200";
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(nameQueue, new ConcurrentLinkedQueue<>());
            result = req.getParam();
            queue.get(nameQueue).add(result);
        } else if ("GET".equals(req.httpRequestType())) {
            result = queue.getOrDefault(nameQueue, new ConcurrentLinkedQueue<>()).poll();
        } else {
            status = "501";
        }
        return new Resp(Objects.requireNonNullElse(result, ""), status);
    }
}