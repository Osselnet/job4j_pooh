package ru.job4j.pooh;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String result = "";
        String status = "200";
        if ("POST".equals(req.httpRequestType())) {
            queue.get(req.getSourceName()).forEach((key, value) -> value.add(req.getParam()));
            result = req.getParam();
        } else if ("GET".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            queue.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            result = queue.get(req.getSourceName()).get(req.getParam()).poll();
        } else {
            status = "501";
        }
        return new Resp(Objects.requireNonNullElse(result, ""), status);
    }
}