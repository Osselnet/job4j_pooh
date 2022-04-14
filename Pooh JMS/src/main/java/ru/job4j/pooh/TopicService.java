package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private static String queueName = "";
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "ok";
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            queue.get(req.getSourceName()).putIfAbsent(queueName, new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).get(queueName).add(req.getParam());
        } else {
            queueName = req.getParam();
            text = queue
                    .getOrDefault(req.getSourceName(), new ConcurrentHashMap<>())
                    .getOrDefault(req.getParam(), new ConcurrentLinkedQueue<>())
                    .poll();
        }
        return new Resp(text == null ? "" : text, "200");
    }
}